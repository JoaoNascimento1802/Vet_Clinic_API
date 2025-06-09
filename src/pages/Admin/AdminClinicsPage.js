import React, { useState, useEffect, useCallback } from 'react';
import MainLayout from '../../layouts/MainLayout';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';
import ClinicFormModal from '../Admin/ClinicFormModal'; // O modal que criamos

// Importa todas as funções de serviço necessárias para o CRUD
import {
  getAllClinics,
  addClinic,
  updateClinic,
  deleteClinic
} from '../../api/clinicService';

// Estilos para a página
const buttonStyle = {
  padding: '10px 15px',
  backgroundColor: '#28a745',
  color: 'white',
  border: 'none',
  borderRadius: '5px',
  cursor: 'pointer',
  fontWeight: 'bold'
};

const tableCellStyle = { padding: '12px', borderBottom: '1px solid #ddd', textAlign: 'left' };
const actionButtonStyle = { 
  marginRight: '8px', 
  padding: '6px 12px', 
  border: '1px solid transparent', 
  borderRadius: '4px', 
  cursor: 'pointer' 
};

const AdminClinicsPage = () => {
    // Estados para os dados da página
    const [clinics, setClinics] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    // Estados para controlar o modal de formulário
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingClinic, setEditingClinic] = useState(null); // Guarda a clínica que está sendo editada

    // Função para buscar os dados da API
    const fetchClinics = useCallback(async () => {
        try {
            setLoading(true);
            const data = await getAllClinics();
            setClinics(data);
        } catch (err) {
            setError('Falha ao carregar os dados das clínicas. Verifique se o backend está rodando e se você tem permissão.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, []);

    // Busca os dados quando o componente é montado
    useEffect(() => {
        fetchClinics();
    }, [fetchClinics]);

    // Funções para manipular o modal
    const handleAddNew = () => {
        setEditingClinic(null); // Limpa o estado de edição para criar uma nova clínica
        setIsModalOpen(true);
    };

    const handleEdit = (clinic) => {
        setEditingClinic(clinic); // Define a clínica a ser editada
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setEditingClinic(null);
    };

    // Função para salvar (criar ou atualizar) uma clínica
    const handleSave = async (clinicData) => {
        try {
            if (editingClinic) {
                // Se estiver editando, chama o serviço de update
                await updateClinic(editingClinic.id, clinicData);
            } else {
                // Se não, chama o serviço de adicionar
                await addClinic(clinicData);
            }
            fetchClinics(); // Recarrega a lista para mostrar a alteração
            handleCloseModal(); // Fecha o modal
        } catch (err) {
            alert('Erro ao salvar clínica. Verifique os dados e tente novamente.');
            console.error(err);
        }
    };
    
    // Função para deletar uma clínica
    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir esta clínica? Esta ação é irreversível.')) {
            try {
                await deleteClinic(id);
                fetchClinics(); // Recarrega a lista
            } catch (err) {
                alert('Falha ao excluir clínica.');
                console.error(err);
            }
        }
    };
    
    // Função para renderizar o conteúdo principal (loading, erro ou a tabela)
    const renderContent = () => {
        if (loading) {
            return <LoadingSpinner />;
        }
        if (error) {
            return <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>;
        }
        return (
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px', backgroundColor: 'white', boxShadow: 'var(--sombra-padrao)' }}>
                <thead style={{ backgroundColor: '#f8f9fa' }}>
                    <tr>
                        <th style={tableCellStyle}>Nome</th>
                        <th style={tableCellStyle}>Email</th>
                        <th style={tableCellStyle}>Telefone</th>
                        <th style={tableCellStyle}>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {clinics.map(clinic => (
                        <tr key={clinic.id} style={{ borderBottom: '1px solid #eee' }}>
                            <td style={tableCellStyle}>{clinic.name}</td>
                            <td style={tableCellStyle}>{clinic.email}</td>
                            <td style={tableCellStyle}>{clinic.phone}</td>
                            <td style={tableCellStyle}>
                                <button onClick={() => handleEdit(clinic)} style={{ ...actionButtonStyle, backgroundColor: '#ffc107' }}>
                                    Editar
                                </button>
                                <button onClick={() => handleDelete(clinic.id)} style={{ ...actionButtonStyle, backgroundColor: '#dc3545', color: 'white' }}>
                                    Excluir
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        );
    };

    return (
        <MainLayout>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
                <h1>Gerenciar Clínicas</h1>
                <button onClick={handleAddNew} style={buttonStyle}>
                    + Adicionar Nova Clínica
                </button>
            </div>
            
            {renderContent()}

            {/* O Modal é renderizado aqui, mas só fica visível quando `isModalOpen` é true */}
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