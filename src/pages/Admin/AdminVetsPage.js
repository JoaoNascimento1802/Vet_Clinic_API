import React, { useState, useEffect, useCallback } from 'react';
import MainLayout from '../../layouts/MainLayout';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';
import VeterinaryFormModal from '../Admin/VeterinaryFormModal';
import { getAllVeterinarians, addVeterinary, updateVeterinary, deleteVeterinary } from '../../api/vetService';

// Estilos (podem ser movidos para um .module.css)
const buttonStyle = { padding: '10px 15px', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold' };
const tableCellStyle = { padding: '12px', borderBottom: '1px solid #ddd', textAlign: 'left' };
const actionButtonStyle = { marginRight: '8px', padding: '6px 12px', border: '1px solid transparent', borderRadius: '4px', cursor: 'pointer' };

const AdminVetsPage = () => {
    const [vets, setVets] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingVet, setEditingVet] = useState(null);

    const fetchVets = useCallback(async () => {
        try {
            setLoading(true);
            const data = await getAllVeterinarians();
            setVets(data);
        } catch (err) {
            setError('Falha ao carregar os dados dos veterinários.');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => { fetchVets(); }, [fetchVets]);

    const handleSave = async (vetData) => {
        try {
            const payload = { ...vetData };
            // Não envia a senha se estiver vazia durante a edição
            if (editingVet && !payload.password) {
                delete payload.password;
            }

            if (editingVet) {
                await updateVeterinary(editingVet.id, payload);
            } else {
                await addVeterinary(payload);
            }
            fetchVets();
            setIsModalOpen(false);
        } catch (err) {
            alert('Erro ao salvar veterinário.');
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza?')) {
            await deleteVeterinary(id);
            fetchVets();
        }
    };

    const handleAddNew = () => { setEditingVet(null); setIsModalOpen(true); };
    const handleEdit = (vet) => { setEditingVet(vet); setIsModalOpen(true); };

    return (
        <MainLayout>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                <h1>Gerenciar Veterinários</h1>
                <button onClick={handleAddNew} style={buttonStyle}>+ Adicionar Novo Veterinário</button>
            </div>
            {loading ? <LoadingSpinner /> : error ? <p style={{color: 'red'}}>{error}</p> : (
                <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                    <thead>
                        <tr style={{ backgroundColor: '#f8f9fa' }}>
                            <th style={tableCellStyle}>Nome</th>
                            <th style={tableCellStyle}>Email</th>
                            <th style={tableCellStyle}>Especialidade</th>
                            <th style={tableCellStyle}>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {vets.map(vet => (
                            <tr key={vet.id}>
                                <td style={tableCellStyle}>{vet.name}</td>
                                <td style={tableCellStyle}>{vet.email}</td>
                                <td style={tableCellStyle}>{vet.specialityenum}</td>
                                <td style={tableCellStyle}>
                                    <button onClick={() => handleEdit(vet)} style={{ ...actionButtonStyle, backgroundColor: '#ffc107' }}>Editar</button>
                                    <button onClick={() => handleDelete(vet.id)} style={{ ...actionButtonStyle, backgroundColor: '#dc3545', color: 'white' }}>Excluir</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            <VeterinaryFormModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onSave={handleSave}
                vet={editingVet}
            />
        </MainLayout>
    );
};

export default AdminVetsPage;