import React, { useState, useEffect, useCallback } from 'react';
import MainLayout from '../../layouts/MainLayout';
import BackButton from '../../components/common/BackButton/BackButton';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';
import { getAllVeterinarians } from '../../api/vetService';
import { generateConsultationsReport } from '../../api/reportService';
import styles from '../AppointmentsPage.module.css'; // Reutilizaremos os estilos

const specialities = [
    'CLINICO_GERAL', 'ANESTESIOLOGIA', 'CARDIOLOGIA', 'DERMATOLOGIA', 'ENDOCRINOLOGIA', 
    'GASTROENTEROLOGIA', 'NEUROLOGIA', 'NUTRICAO', 'OFTALMOLOGIA', 'ONCOLOGIA', 'ORTOPEDIA', 
    'REPRODUCAO_ANIMAL', 'PATOLOGIA', 'CIRURGIA_GERAL', 'CIRURGIA_ORTOPEDICA', 
    'ODONTOLOGIA', 'ZOOTECNIA', 'EXOTICOS', 'ACUPUNTURA', 'FISIOTERAPIA', 'IMAGINOLOGIA'
];

const ReportsPage = () => {
    const [vets, setVets] = useState([]);
    const [filters, setFilters] = useState({ veterinarioId: '', speciality: '' });
    const [loading, setLoading] = useState(true);
    const [isGenerating, setIsGenerating] = useState(false); // Estado para o botão

    // Busca os veterinários para preencher o filtro
    const fetchVets = useCallback(async () => {
        try {
            const vetsData = await getAllVeterinarians();
            setVets(vetsData);
        } catch (error) {
            console.error("Falha ao carregar veterinários para o filtro.", error);
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchVets();
    }, [fetchVets]);

    const handleFilterChange = (e) => {
        setFilters({ ...filters, [e.target.name]: e.target.value });
    };

    const handleGenerateReport = async () => {
        setIsGenerating(true);
        try {
            await generateConsultationsReport(filters);
        } catch (error) {
            // O erro já é tratado no service com um alert
        } finally {
            setIsGenerating(false);
        }
    };

    if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;

    return (
        <MainLayout>
            <BackButton />
            <div className={styles.pageHeader}>
                <h1 className={styles.pageTitle}>Gerar Relatórios</h1>
            </div>

            <div className={styles.section}>
                <h2 className={styles.sectionTitle}>Relatório de Consultas</h2>
                <p style={{marginBottom: '1.5rem'}}>Selecione os filtros desejados para gerar o relatório em PDF. Deixe em branco para incluir todos.</p>

                <div className={styles.formGrid}>
                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="veterinarioId">Filtrar por Veterinário</label>
                        <select name="veterinarioId" value={filters.veterinarioId} onChange={handleFilterChange} className={styles.select}>
                            <option value="">Todos os Veterinários</option>
                            {vets.map(vet => <option key={vet.id} value={vet.id}>{vet.name}</option>)}
                        </select>
                    </div>

                    <div className={styles.formGroup}>
                        <label className={styles.label} htmlFor="speciality">Filtrar por Especialidade</label>
                        <select name="speciality" value={filters.speciality} onChange={handleFilterChange} className={styles.select}>
                            <option value="">Todas as Especialidades</option>
                            {specialities.map(spec => <option key={spec} value={spec}>{spec.replace(/_/g, ' ')}</option>)}
                        </select>
                    </div>
                </div>

                <div style={{marginTop: '2rem', textAlign: 'right'}}>
                    <button onClick={handleGenerateReport} disabled={isGenerating} style={{padding: '12px 25px', backgroundColor: '#0d6efd', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold'}}>
                        {isGenerating ? 'Gerando...' : 'Gerar Relatório PDF'}
                    </button>
                </div>
            </div>
        </MainLayout>
    );
};

export default ReportsPage;