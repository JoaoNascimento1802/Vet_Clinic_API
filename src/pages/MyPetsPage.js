import React, { useState, useEffect, useCallback } from 'react';
// AQUI ESTÁ A CORREÇÃO: Adicionamos 'updatePet' à lista de imports
import { getAllPets, addPet, updatePet, deletePet } from '../api/petService';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import PetFormModal from '../components/pets/PetFormModal';

// Reutilize estilos
const pageTitleStyle = { fontSize: '2rem', color: '#333' };
const listStyle = { display: 'flex', flexWrap: 'wrap', gap: '20px' };
const cardStyle = { border: '1px solid #e0e0e0', borderRadius: '8px', padding: '20px', backgroundColor: '#fff', width: '250px' };
const imageStyle = { width: '100%', height: '180px', objectFit: 'cover', borderRadius: '4px' };
const buttonStyle = { padding: '8px 12px', border: 'none', borderRadius: '4px', cursor: 'pointer' };

const MyPetsPage = () => {
  const { user } = useAuth();
  const [myPets, setMyPets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingPet, setEditingPet] = useState(null);

  const fetchMyPets = useCallback(async () => {
    if (!user) return;
    try {
      setLoading(true);
      const allPets = await getAllPets();
      const userPets = allPets.filter(pet => pet.usuario?.id === user.id);
      setMyPets(userPets);
    } catch (err) {
      setError('Falha ao carregar seus pets.');
    } finally {
      setLoading(false);
    }
  }, [user]);

  useEffect(() => {
    fetchMyPets();
  }, [fetchMyPets]);
  
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingPet(null);
  };

  const handleSave = async (petData) => {
    // Adiciona o ID do usuário ao payload
    const payload = { ...petData, usuarioId: user.id };
    try {
      if (editingPet) {
        // Agora a função updatePet está definida e pode ser chamada
        await updatePet(editingPet.id, payload);
      } else {
        await addPet(payload);
      }
      fetchMyPets();
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
            fetchMyPets();
        } catch (err) {
            alert('Falha ao remover o pet.');
        }
    }
  }

  if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;

  return (
    <MainLayout>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1 style={pageTitleStyle}>Meus Queridos Pets</h1>
        <button onClick={handleAddNew} style={{...buttonStyle, backgroundColor: '#28a745', color: 'white'}}>+ Adicionar Pet</button>
      </div>
      
      {error && <p style={{ color: 'red' }}>{error}</p>}
      
      {myPets.length > 0 ? (
        <div style={listStyle}>
          {myPets.map(pet => (
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
      ) : (
        <p>Você ainda não cadastrou nenhum pet.</p>
      )}

      <PetFormModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
        onSave={handleSave}
        pet={editingPet}
      />
    </MainLayout>
  );
};

export default MyPetsPage;