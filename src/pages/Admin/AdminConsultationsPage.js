import React, { useState, useEffect, useCallback } from 'react';
import MainLayout from '../../layouts/MainLayout';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';
import { getAllConsultations } from '../../api/consultationService'; // Supondo que você tenha um delete/updateService

const AdminConsultationsPage = () => {
    const [consultations, setConsultations] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchConsultations = useCallback(async () => {
        setLoading(true);
        const data = await getAllConsultations();
        setConsultations(data);
        setLoading(false);
    }, []);

    useEffect(() => { fetchConsultations(); }, [fetchConsultations]);

    return (
        <MainLayout>
            <h1>Gerenciar Todas as Consultas</h1>
            {loading ? <LoadingSpinner /> : (
                <table>
                    <thead>
                        <tr>
                            <th>Data</th>
                            <th>Pet</th>
                            <th>Veterinário</th>
                            <th>Status</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {consultations.map(c => (
                            <tr key={c.id}>
                                <td>{c.consultationdate} às {c.consultationtime}</td>
                                <td>{c.petName}</td>
                                <td>{c.veterinaryName}</td>
                                <td>{c.status}</td>
                                <td>
                                    <button>Alterar Status</button>
                                    <button>Cancelar</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </MainLayout>
    );
};

export default AdminConsultationsPage;