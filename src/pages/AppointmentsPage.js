import React, { useState, useEffect, useCallback } from 'react';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import styles from './AppointmentsPage.module.css';

// 1. IMPORTA AS NOVAS FUNÇÕES DO SERVIÇO
import { createConsultation, getMyConsultations, getAllConsultationsForAdmin } from '../api/consultationService';
import { getAllVeterinarians } from '../api/vetService';
import { getAllClinics } from '../api/clinicService';
import { getAllPets } from '../api/petService';

// Lista de especialidades
const specialities = [
    'CLINICO_GERAL', 'ANESTESIOLOGIA', 'CARDIOLOGIA', 'DERMATOLOGIA',
    'ENDOCRINOLOGIA', 'GASTROENTEROLOGIA', 'NEUROLOGIA', 'NUTRICAO',
    'OFTALMOLOGIA', 'ONCOLOGIA', 'ORTOPEDIA', 'REPRODUCAO_ANIMAL',
    'PATOLOGIA', 'CIRURGIA_GERAL', 'CIRURGIA_ORTOPEDICA', 'ODONTOLOGIA',
    'ZOOTECNIA', 'EXOTICOS', 'ACUPUNTURA', 'FISIOTERAPIA', 'IMAGINOLOGIA'
];

const AppointmentsPage = () => {
    const { user, isAdmin } = useAuth();
    
    // Estados do componente
    const [consultations, setConsultations] = useState([]);
    const [petsForSelect, setPetsForSelect] = useState([]);
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
        specialityEnum: 'CLINICO_GERAL',
        status: 'AGENDADA'
    });

    const fetchAllData = useCallback(async () => {
        if (!user) return;
        try {
            setLoading(true);
            setError('');
            
            // 2. LÓGICA ATUALIZADA: Escolhe qual função de busca de consulta usar
            const fetchConsultationsFunction = isAdmin ? getAllConsultationsForAdmin() : getMyConsultations();

            const [consultationsData, petsData, clinicsData, vetsData] = await Promise.all([
                fetchConsultationsFunction, // <-- USA A FUNÇÃO CORRETA AQUI
                getAllPets(),
                getAllClinics(),
                getAllVeterinarians()
            ]);

            setConsultations(consultationsData);
            setClinics(clinicsData);
            setVets(vetsData);

            if (isAdmin) {
                setPetsForSelect(petsData);
            } else {
                setPetsForSelect(petsData.filter(p => p.usuarioId === user.id));
            }

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
            const payload = {
                ...formData,
                petId: parseInt(formData.petId),
                clinicaId: parseInt(formData.clinicaId),
                veterinarioId: parseInt(formData.veterinarioId),
                usuarioId: user.id,
                observations: 'Agendado via sistema.'
            };
            await createConsultation(payload);
            alert('Consulta agendada com sucesso!');
            setFormData({ petId: '', clinicaId: '', veterinarioId: '', consultationdate: '', consultationtime: '', reason: '', specialityEnum: 'CLINICO_GERAL', status: 'AGENDADA' });
            fetchAllData();
        } catch (err) {
            const errorMessage = err.response?.data?.message || 'Erro ao agendar consulta. Verifique os dados.';
            alert(errorMessage);
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
    
    // Este filtro continua correto, pois agora 'consultations' já vem filtrado do backend para usuários normais
    const filteredConsultations = consultations;

    if (loading) {
        return <MainLayout><LoadingSpinner /></MainLayout>;
    }
    
    return (
        <MainLayout>
            <div className={styles.pageHeader}>
                <h1 className={styles.pageTitle}>Agendamentos</h1>
            </div>

            {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}

            {/* --- SEÇÃO DO FORMULÁRIO --- */}
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Agendar Nova Consulta</h2>
                <form onSubmit={handleSubmit} className={styles.formGrid}>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="petId">Pet</label>
                        <select name="petId" id="petId" value={formData.petId} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione um pet</option>
                            {petsForSelect.map(pet => <option key={pet.id} value={pet.id}>{pet.name} {isAdmin ? `(Dono: ${pet.usuarioUsername})` : ''}</option>)}
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="clinicaId">Clínica</label>
                        <select name="clinicaId" id="clinicaId" value={formData.clinicaId} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione uma clínica</option>
                            {clinics.map(clinic => <option key={clinic.id} value={clinic.id}>{clinic.name}</option>)}
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="specialityEnum">Especialidade</label>
                        <select name="specialityEnum" id="specialityEnum" value={formData.specialityEnum} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione uma especialidade</option>
                            {specialities.map(spec => <option key={spec} value={spec}>{spec.replace(/_/g, ' ')}</option>)}
                        </select>
                    </div>
                     <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="veterinarioId">Veterinário</label>
                        <select name="veterinarioId" id="veterinarioId" value={formData.veterinarioId} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione um veterinário</option>
                            {vets
                                .filter(v => formData.specialityEnum ? v.specialityenum === formData.specialityEnum : true)
                                 .map(vet => <option key={vet.id} value={vet.id}>{vet.name}</option>)}
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
                        <textarea name="reason" id="reason" value={formData.reason} onChange={handleChange} required className={styles.textarea} placeholder="Descreva brevemente o motivo da visita..."></textarea>
                    </div>
                    <button type="submit" className={styles.submitButton} disabled={loading}>{loading ? 'Enviando...' : 'Confirmar Agendamento'}</button>
                </form>
            </div>

            {/* Seção da Lista de Consultas */}
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Histórico de Consultas</h2>
                {filteredConsultations.length > 0 ? (
                    <ul className={styles.appointmentList}>
                        {filteredConsultations.map(c => (
                            <li key={c.id} className={styles.appointmentCard}>
                                <div className={styles.appointmentInfo}>
                                    <strong>{new Date(c.consultationdate).toLocaleDateString('pt-BR', {timeZone: 'UTC'})} às {c.consultationtime}</strong><br/>
                                    <span>Pet: {c.petName} | Veterinário(a): {c.veterinaryName}</span><br/>
                                    <span>Clínica: {c.clinicName}</span>
                                </div>
                                <div className={`${styles.statusBadge}`} style={getStatusStyle(c.status)}>
                                    {c.status}
                                </div>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Nenhuma consulta encontrada.</p>
                )}
            </div>
        </MainLayout>
    );
};

export default AppointmentsPage;