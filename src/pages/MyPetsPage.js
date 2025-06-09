import React, { useState, useEffect, useCallback } from 'react';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import PetFormModal from '../components/pets/PetFormModal';
import { getAllPets, addPet, updatePet, deletePet } from '../api/petService';
import { getAllUsers } from '../api/userService';

// Estilos
const pageTitleStyle = { fontSize: '2rem', color: '#333' };
const listStyle = { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))', gap: '20px', marginTop: '2rem' };
const cardStyle = { border: '1px solid #e0e0e0', borderRadius: '8px', padding: '1.25rem', backgroundColor: '#fff', boxShadow: '0 2px 5px rgba(0,0,0,0.05)' };
const imageStyle = { width: '100%', height: '180px', objectFit: 'cover', borderRadius: '4px' };
const buttonStyle = { padding: '8px 12px', border: 'none', borderRadius: '4px', cursor: 'pointer', fontWeight: '500' };

const MyPetsPage = () => {
  const { user, isAdmin } = useAuth();
  
  // Estados do componente
  const [petsToDisplay, setPetsToDisplay] = useState([]);
  const [users, setUsers] = useState([]); // Estado para a lista de usuários (para o admin)
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  
  // Estados para controlar o Modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingPet, setEditingPet] = useState(null);

  // Função para buscar todos os dados necessários
  const fetchData = useCallback(async () => {
    if (!user) return;
    try {
      setLoading(true);
      setError('');
      
      const [petsData, usersData] = await Promise.all([
        getAllPets(),
        isAdmin ? getAllUsers() : Promise.resolve([]) // Só busca usuários se for admin
      ]);

      setUsers(usersData);

      if (isAdmin) {
        setPetsToDisplay(petsData);
      } else {
        setPetsToDisplay(petsData.filter(pet => pet.usuarioId === user.id));
      }

    } catch (err) {
      setError('Falha ao carregar os dados.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [user, isAdmin]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);
  
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingPet(null);
  };

  const handleSave = async (petData) => {
    try {
      // Define o dono do pet: se for admin, usa o valor do formulário; se não, usa o próprio usuário logado.
      const payload = { ...petData, usuarioId: isAdmin ? parseInt(petData.usuarioId) : user.id };

      if (isAdmin && !payload.usuarioId) {
        alert("Para administradores, é obrigatório selecionar o dono do pet.");
        return;
      }

      if (editingPet) {
        await updatePet(editingPet.id, payload);
      } else {
        await addPet(payload);
      }
      
      fetchData(); // Recarrega a lista de pets
      handleCloseModal();
    } catch (err) {
      alert("Erro ao salvar o pet. Verifique os dados.");
    }
  };

  const handleAddNew = () => {
    setEditingPet(null);
    setIsModalOpen(true);
  };

  const handleEdit = (pet) => {
    setEditingPet(pet);
    setIsModalOpen(true);
  };

  const handleDelete = async (petId) => {
    if (window.confirm('Tem certeza que deseja remover este pet?')) {
        try {
            await deletePet(petId);
            fetchData();
        } catch (err) {
            alert('Falha ao remover o pet.');
        }
    }
  };

  const pageTitle = isAdmin ? "Gerenciar Todos os Pets" : "Meus Queridos Pets";

  const renderContent = () => {
    if (loading) return <LoadingSpinner />;
    if (error) return <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>;
    if (petsToDisplay.length === 0) return <p>Nenhum pet encontrado.</p>;

    return (
      <div style={listStyle}>
        {petsToDisplay.map(pet => (
          <div key={pet.id} style={cardStyle}>
            <img src={pet.imageurl} alt={pet.name} style={imageStyle} />
            <h3 style={{marginTop: '10px'}}>{pet.name}</h3>
            <p>Idade: {pet.age} anos</p>
            <p>Espécie: {pet.speciespet}</p>
            <div style={{marginTop: '15px', display: 'flex', gap: '10px'}}>
                <button onClick={() => handleEdit(pet)} style={{...buttonStyle, backgroundColor: '#ffc107'}}>Editar</button>
                <button onClick={() => handleDelete(pet.id)} style={{...buttonStyle, backgroundColor: '#dc3545', color: 'white'}}>Remover</button>
            </div>
          </div>
        ))}
      </div>
    );
  };

  return (
    <MainLayout>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1 style={pageTitleStyle}>{pageTitle}</h1>
        <button onClick={handleAddNew} style={{...buttonStyle, backgroundColor: '#28a745', color: 'white'}}>+ Adicionar Pet</button>
      </div>
      
      {renderContent()}

      <PetFormModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onSave={handleSave}
        pet={editingPet}
        isAdmin={isAdmin}
        users={users}
      />
    </MainLayout>
  );
};

export default MyPetsPage;