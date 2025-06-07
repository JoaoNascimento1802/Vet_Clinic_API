import React, { useState, useEffect, useCallback } from 'react';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import styles from './AppointmentsPage.module.css';

// Importe todos os serviços necessários
import { createConsultation, getAllConsultations } from '../api/consultationService';
import { getAllVeterinarians } from '../api/vetService';
import { getAllClinics } from '../api/clinicService';
import { getAllPets } from '../api/petService';

// Valores do Enum de Especialidades do seu backend
const specialities = [
    'CLINICA_GERAL', 'CIRURGIA', 'ODONTOLOGIA', 'OFTALMOLOGIA',
    'DERMATOLOGIA', 'ORTOPEDIA', 'CARDIOLOGIA', 'ONCOLOGIA',
    'ANESTESIOLOGIA', 'EXAMES_IMAGEM', 'VACINACAO', 'INTERNACAO',
    'EMERGENCIA', 'FISIOTERAPIA'
];

const AppointmentsPage = () => {
    const { user, isAdmin } = useAuth();
    
    // Estados para os dados e UI
    const [consultations, setConsultations] = useState([]);
    const [myPets, setMyPets] = useState([]);
    const [clinics, setClinics] = useState([]);
    const [vets, setVets] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    // Estado para o formulário
    const [formData, setFormData] = useState({
        petId: '',
        clinicaId: '',
        veterinarioId: '',
        consultationdate: '',
        consultationtime: '',
        reason: '',
        specialityEnum: ''
    });

    const fetchAllData = useCallback(async () => {
        if (!user) return;
        try {
            setLoading(true);
            // Busca todos os dados em paralelo para melhor performance
            const [consultationsData, petsData, clinicsData, vetsData] = await Promise.all([
                getAllConsultations(),
                getAllPets(),
                getAllClinics(),
                isAdmin ? getAllVeterinarians() : [] // Admins veem todos os vets
            ]);

            setConsultations(consultationsData);
            setClinics(clinicsData);
            setVets(vetsData);
            // Filtra para mostrar apenas os pets do usuário logado no dropdown
            setMyPets(petsData.filter(p => p.usuario?.id === user.id));

        } catch (err) {
            console.error("Failed to load page data:", err);
            setError("Falha ao carregar dados da página. Verifique sua conexão e permissões.");
        } finally {
            setLoading(false);
        }
    }, [user, isAdmin]);

    useEffect(() => {
        fetchAllData();
    }, [fetchAllData]);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            // Monta o payload conforme o DTO do backend
            const payload = {
                ...formData,
                petId: parseInt(formData.petId),
                clinicaId: parseInt(formData.clinicaId),
                veterinarioId: parseInt(formData.veterinarioId),
                usuarioId: user.id,
                status: 'AGENDADA', // Status padrão ao criar
                observations: 'Nenhuma observação inicial.' // Observação padrão
            };
            await createConsultation(payload);
            alert('Consulta agendada com sucesso!');
            setFormData({ petId: '', clinicaId: '', veterinarioId: '', consultationdate: '', consultationtime: '', reason: '', specialityEnum: '' }); // Limpa o form
            fetchAllData(); // Recarrega a lista de consultas
        } catch (err) {
            alert('Erro ao agendar consulta. Verifique os dados.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };
    
    const getStatusStyle = (status) => {
        switch (status) {
            case 'AGENDADA': return { backgroundColor: '#17a2b8' };
            case 'REALIZADA': return { backgroundColor: 'var(--cor-sucesso)' };
            case 'CANCELADA': return { backgroundColor: 'var(--cor-perigo)' };
            default: return { backgroundColor: '#6c757d' };
        }
    };
    
    // Filtra as consultas para mostrar (todas para admin, só as do user para user)
    const filteredConsultations = isAdmin ? consultations : consultations.filter(c => c.usuario?.id === user.id);

    if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;

    return (
        <MainLayout>
            <div className={styles.pageHeader}>
                <h1 className={styles.pageTitle}>Minhas Consultas</h1>
            </div>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            {/* Seção do Formulário de Agendamento */}
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Agendar Nova Consulta</h2>
                <form onSubmit={handleSubmit} className={styles.formGrid}>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="petId">Seu Pet</label>
                        <select name="petId" id="petId" value={formData.petId} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione um pet</option>
                            {myPets.map(pet => <option key={pet.id} value={pet.id}>{pet.name}</option>)}
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="clinicaId">Clínica</label>
                        <select name="clinicaId" id="clinicaId" value={formData.clinicaId} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione uma clínica</option>
                            {clinics.map(clinic => <option key={clinic.id} value={clinic.id}>{clinic.name}</option>)}
                        </select>
                    </div>
                    {isAdmin && (
                        <div className={styles.formGroup}>
                           <label className={styles.label} htmlFor="veterinarioId">Veterinário</label>
                           <select name="veterinarioId" id="veterinarioId" value={formData.veterinarioId} onChange={handleChange} required className={styles.select}>
                               <option value="">Selecione um veterinário</option>
                               {vets.map(vet => <option key={vet.id} value={vet.id}>{vet.name} - {vet.specialityenum}</option>)}
                           </select>
                       </div>
                    )}
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="specialityEnum">Especialidade Desejada</label>
                        <select name="specialityEnum" id="specialityEnum" value={formData.specialityEnum} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione uma especialidade</option>
                            {specialities.map(spec => <option key={spec} value={spec}>{spec.replace('_', ' ')}</option>)}
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="consultationdate">Data</label>
                        <input type="date" name="consultationdate" id="consultationdate" value={formData.consultationdate} onChange={handleChange} required className={styles.input} />
                    </div>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="consultationtime">Hora</label>
                        <input type="time" name="consultationtime" id="consultationtime" value={formData.consultationtime} onChange={handleChange} required className={styles.input} />
                    </div>
                    <div className={styles.formGroup} style={{gridColumn: '1 / -1'}}>
                        <label className={styles.label} htmlFor="reason">Motivo da Consulta</label>
                        <textarea name="reason" id="reason" value={formData.reason} onChange={handleChange} required className={styles.textarea}></textarea>
                    </div>
                    <button type="submit" className={styles.submitButton} disabled={loading}>{loading ? 'Agendando...' : 'Confirmar Agendamento'}</button>
                </form>
            </div>

            {/* Seção da Lista de Consultas */}
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Próximas Consultas</h2>
                {filteredConsultations.length > 0 ? (
                    <ul className={styles.appointmentList}>
                        {filteredConsultations.map(c => (
                            <li key={c.id} className={styles.appointmentCard}>
                                <div className={styles.appointmentInfo}>
                                    <strong>{c.consultationdate} às {c.consultationtime}</strong><br/>
                                    <span>Pet: {c.pet?.name || 'N/A'} | Veterinário(a): {c.veterinario?.name || 'N/A'}</span><br/>
                                    <span>Clínica: {c.clinica?.name || 'N/A'}</span>
                                </div>
                                <div className={`${styles.statusBadge}`} style={getStatusStyle(c.status)}>
                                    {c.status}
                                </div>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Nenhuma consulta agendada.</p>
                )}
            </div>
        </MainLayout>
    );
};

export default AppointmentsPage;