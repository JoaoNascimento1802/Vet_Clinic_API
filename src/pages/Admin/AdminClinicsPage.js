import React, { useState, useEffect, useCallback } from 'react';
import { getAllClinics, addClinic, updateClinic, deleteClinic } from '../../api/clinicService';
import MainLayout from '../../layouts/MainLayout';
import ClinicFormModal from '../Admin/FormModal.module.css'

const AdminClinicsPage = () => {
    const [clinics, setClinics] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingClinic, setEditingClinic] = useState(null);

    const fetchClinics = useCallback(async () => setClinics(await getAllClinics()), []);

    useEffect(() => { fetchClinics(); }, [fetchClinics]);
    
    const handleSave = async (clinicData) => {
        if (editingClinic) {
            await updateClinic(editingClinic.id, clinicData);
        } else {
            await addClinic(clinicData);
        }
        fetchClinics();
        handleCloseModal();
    };

    const handleEdit = (clinic) => {
        setEditingClinic(clinic);
        setIsModalOpen(true);
    };
    
    const handleAddNew = () => {
        setEditingClinic(null);
        setIsModalOpen(true);
    };

    const handleDelete = async (id) => {
        if(window.confirm('Tem certeza?')) {
            await deleteClinic(id);
            fetchClinics();
        }
    };
    
    const handleCloseModal = () => {
        setIsModalOpen(false);
        setEditingClinic(null);
    };
    
    return (
        <MainLayout>
            <h1>Gerenciar Clínicas</h1>
            <button onClick={handleAddNew}>Adicionar Nova Clínica</button>
            <table style={{width: '100%', marginTop: '20px'}}>
                {/* ... Thead da tabela ... */}
                <tbody>
                    {clinics.map(clinic => (
                        <tr key={clinic.id}>
                            <td>{clinic.name}</td>
                            <td>{clinic.email}</td>
                            <td>
                                <button onClick={() => handleEdit(clinic)}>Editar</button>
                                <button onClick={() => handleDelete(clinic.id)}>Excluir</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <ClinicFormModal 
                isOpen={isModalOpen}
                onClose={handleCloseModal}
                onSave={handleSave}
                clinic={editingClinic}
            />
        </MainLayout>
    );
};

export default AdminClinicsPage;