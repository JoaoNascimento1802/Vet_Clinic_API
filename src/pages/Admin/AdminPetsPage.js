import React, { useState, useEffect, useCallback } from 'react';
import MainLayout from '../../layouts/MainLayout';
import LoadingSpinner from '../../components/common/loadingspinner/LoadingSpinner';
import PetFormModal from '../../components/pets/PetFormModal'; // Reutilizamos o modal de pet
import BackButton from '../../components/common/BackButton/BackButton'; // Botão de voltar

// Serviços da API necessários
import { getAllPets, addPet, updatePet, deletePet } from '../../api/petService';
import { getAllUsers } from '../../api/userService';

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

const AdminPetsPage = () => {
    // Estados para os dados da página
    const [pets, setPets] = useState([]);
    const [users, setUsers] = useState([]); // Lista de usuários para o dropdown no modal

    // Estados para controle da UI
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingPet, setEditingPet] = useState(null);

    // Função para buscar todos os dados necessários (pets e usuários)
    const fetchData = useCallback(async () => {
        try {
            setLoading(true);
            setError('');
            // Busca pets e usuários em paralelo para otimizar o carregamento
            const [petsData, usersData] = await Promise.all([
                getAllPets(), 
                getAllUsers()
            ]);
            setPets(petsData);
            setUsers(usersData);
        } catch (err) {
            setError('Falha ao carregar os dados. Verifique se o backend está rodando.');
            console.error(err);
        } finally {
            setLoading(false);
        }
    }, []);

    // Busca os dados quando o componente é montado
    useEffect(() => {
        fetchData();
    }, [fetchData]);

    // Função para salvar (acionada pelo modal)
    const handleSave = async (petData) => {
        try {
            // Garante que o ID do usuário foi convertido para número
            const payload = { ...petData, usuarioId: parseInt(petData.usuarioId) };

            if (!payload.usuarioId) {
                alert("É obrigatório selecionar o dono do pet.");
                return;
            }

            if (editingPet) {
                await updatePet(editingPet.id, payload);
            } else {
                await addPet(payload);
            }
            
            fetchData(); // Recarrega a lista para mostrar a alteração
            setIsModalOpen(false);
        } catch (err) {
            alert('Erro ao salvar o pet. Verifique os dados.');
            console.error(err);
        }
    };
    
    // Funções para controlar o modal
    const handleAddNew = () => {
        setEditingPet(null);
        setIsModalOpen(true);
    };

    const handleEdit = (pet) => {
        setEditingPet(pet);
        setIsModalOpen(true);
    };

    const handleDelete = async (id) => {
        if (window.confirm('Tem certeza que deseja excluir este pet?')) {
            try {
                await deletePet(id);
                fetchData();
            } catch (err) {
                alert('Falha ao excluir o pet.');
                console.error(err);
            }
        }
    };

    const renderContent = () => {
        if (loading) return <LoadingSpinner />;
        if (error) return <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>;
        
        return (
            <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px', backgroundColor: 'white', boxShadow: 'var(--sombra-padrao)' }}>
                <thead style={{ backgroundColor: '#f8f9fa' }}>
                    <tr>
                        <th style={tableCellStyle}>Nome do Pet</th>
                        <th style={tableCellStyle}>Espécie</th>
                        <th style={tableCellStyle}>Dono</th>
                        <th style={tableCellStyle}>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {pets.map(pet => (
                        <tr key={pet.id}>
                            <td style={tableCellStyle}>{pet.name}</td>
                            <td style={tableCellStyle}>{pet.speciespet}</td>
                            <td style={tableCellStyle}>{pet.usuarioUsername || 'N/A'}</td>
                            <td style={tableCellStyle}>
                                <button onClick={() => handleEdit(pet)} style={{ ...actionButtonStyle, backgroundColor: '#ffc107' }}>Editar</button>
                                <button onClick={() => handleDelete(pet.id)} style={{ ...actionButtonStyle, backgroundColor: '#dc3545', color: 'white' }}>Excluir</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        );
    };

    return (
        <MainLayout>
            <BackButton />
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h1>Gerenciar Pets</h1>
                <button onClick={handleAddNew} style={buttonStyle}>+ Adicionar Novo Pet</button>
            </div>
            
            {renderContent()}

            <PetFormModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                onSave={handleSave}
                pet={editingPet}
                isAdmin={true} // Sempre será admin nesta página
                users={users} // Passa a lista de usuários para o modal
            />
        </MainLayout>
    );
};

export default AdminPetsPage;