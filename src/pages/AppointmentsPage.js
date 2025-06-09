import React, { useState, useEffect, useCallback } from 'react';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import styles from './AppointmentsPage.module.css';

// Importa todos os serviços de API necessários
import { createConsultation, getAllConsultations } from '../api/consultationService';
import { getAllVeterinarians } from '../api/vetService';
import { getAllClinics } from '../api/clinicService';
import { getAllPets } from '../api/petService';

// Valores do Enum de Especialidades do seu backend para o dropdown
const specialities = [
    'CLINICO_GERAL', 'CIRURGIA', 'ODONTOLOGIA', 'OFTALMOLOGIA',
    'DERMATOLOGIA', 'ORTOPEDIA', 'CARDIOLOGIA', 'ONCOLOGIA',
    'ANESTESIOLOGIA', 'EXAMES_IMAGEM', 'VACINACAO', 'INTERNACAO',
    'EMERGENCIA', 'FISIOTERAPIA'
];

const AppointmentsPage = () => {
    const { user, isAdmin } = useAuth();
    
    // --- ESTADOS DO COMPONENTE ---
    // Listas de dados
    const [consultations, setConsultations] = useState([]);
    const [petsForSelect, setPetsForSelect] = useState([]); // Pets para o dropdown
    const [clinics, setClinics] = useState([]);
    const [vets, setVets] = useState([]);
    
    // Controle de UI
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    // Dados do formulário
    const [formData, setFormData] = useState({
        petId: '',
        clinicaId: '',
        veterinarioId: '',
        consultationdate: '',
        consultationtime: '',
        reason: '',
        specialityEnum: ''
    });

    // --- LÓGICA DE BUSCA DE DADOS ---
    const fetchAllData = useCallback(async () => {
        if (!user) return;
        try {
            setLoading(true);
            setError('');
            
            // Busca todos os dados necessários em paralelo para melhor performance
            const [consultationsData, petsData, clinicsData, vetsData] = await Promise.all([
                getAllConsultations(),
                getAllPets(),
                getAllClinics(),
                getAllVeterinarians() // Busca todos os vets, pois o agendamento precisa deles
            ]);

            setConsultations(consultationsData);
            setClinics(clinicsData);
            setVets(vetsData);

            // Lógica corrigida para o dropdown de pets
            if (isAdmin) {
                // Se for admin, carrega TODOS os pets para o dropdown
                setPetsForSelect(petsData);
            } else {
                // Se for usuário comum, filtra e carrega apenas os seus pets
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

    // --- HANDLERS (FUNÇÕES DE EVENTO) ---
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
                usuarioId: user.id, // Sempre o usuário logado que está agendando
                status: 'AGENDADA',
                observations: 'Agendado via sistema.'
            };
            await createConsultation(payload);
            alert('Consulta agendada com sucesso!');
            setFormData({ petId: '', clinicaId: '', veterinarioId: '', consultationdate: '', consultationtime: '', reason: '', specialityEnum: '' }); // Limpa o form
            fetchAllData(); // Recarrega a lista de consultas
        } catch (err) {
            alert('Erro ao agendar consulta. Verifique se todos os campos estão preenchidos corretamente.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };
    
    // Função auxiliar para estilizar o status da consulta
    const getStatusStyle = (status) => {
        switch (status) {
            case 'AGENDADA': return { backgroundColor: '#17a2b8' };
            case 'REALIZADA': return { backgroundColor: 'var(--cor-sucesso)' };
            case 'CANCELADA': return { backgroundColor: 'var(--cor-perigo)' };
            default: return { backgroundColor: '#6c757d' };
        }
    };
    
    // Filtra as consultas que serão exibidas na lista
    const filteredConsultations = isAdmin ? consultations : consultations.filter(c => c.usuario?.id === user.id);

    // --- RENDERIZAÇÃO DO COMPONENTE ---
    if (loading) {
        return <MainLayout><LoadingSpinner /></MainLayout>;
    }
    
    return (
        <MainLayout>
            <div className={styles.pageHeader}>
                <h1 className={styles.pageTitle}>Agendamentos</h1>
            </div>

            {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}

            {/* Seção do Formulário */}
            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Agendar Nova Consulta</h2>
                <form onSubmit={handleSubmit} className={styles.formGrid}>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="petId">Pet</label>
                        <select name="petId" id="petId" value={formData.petId} onChange={handleChange} required className={styles.select}>
                            <option value="">Selecione um pet</option>
                            {petsForSelect.map(pet => <option key={pet.id} value={pet.id}>{pet.name} {isAdmin ? `(Dono: ID ${pet.usuarioId})` : ''}</option>)}
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
                            {vets.filter(v => formData.specialityEnum ? v.specialityenum === formData.specialityEnum : true)
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
                    <p>Nenhuma consulta encontrada.</p>
                )}
            </div>
        </MainLayout>
    );
};

export default AppointmentsPage;