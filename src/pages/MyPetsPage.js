import React, { useState, useEffect, useCallback } from 'react';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import PetFormModal from '../components/pets/PetFormModal'; // O modal que criamos antes

// Serviços da API
import { getAllPets, addPet, updatePet, deletePet } from '../api/petService';

// Estilos para a página (podem ser movidos para um .module.css se preferir)
const pageTitleStyle = { 
  fontSize: '2rem', 
  color: '#333',
  fontFamily: "'Poppins', sans-serif",
};
const listStyle = { 
  display: 'grid', 
  gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))', 
  gap: '20px', 
  marginTop: '2rem' 
};
const cardStyle = { 
  border: '1px solid #e0e0e0', 
  borderRadius: '8px', 
  padding: '1.25rem', 
  backgroundColor: '#fff', 
  boxShadow: '0 2px 5px rgba(0,0,0,0.05)' 
};
const imageStyle = { 
  width: '100%', 
  height: '180px', 
  objectFit: 'cover', 
  borderRadius: '4px' 
};
const buttonStyle = { 
  padding: '8px 12px', 
  border: 'none', 
  borderRadius: '4px', 
  cursor: 'pointer',
  fontWeight: '500'
};

const MyPetsPage = () => {
  const { user, isAdmin } = useAuth();

  // Estados do componente
  const [petsToDisplay, setPetsToDisplay] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  
  // Estados para controlar o Modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingPet, setEditingPet] = useState(null);

  // Função para buscar os pets
  const fetchPets = useCallback(async () => {
    if (!user) return; // Não faz nada se não houver usuário logado

    try {
      setLoading(true);
      setError(''); // Limpa erros anteriores
      const allPets = await getAllPets();

      if (isAdmin) {
        // Se for admin, mostra todos os pets
        setPetsToDisplay(allPets);
      } else {
        // Se for usuário comum, filtra apenas os seus pets
        const userPets = allPets.filter(pet => pet.usuarioId === user.id);
        setPetsToDisplay(userPets);
      }

    } catch (err) {
      setError('Falha ao carregar os pets. Verifique sua conexão.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, [user, isAdmin]);

  // Executa a busca de pets quando o componente é montado ou o usuário muda
  useEffect(() => {
    fetchPets();
  }, [fetchPets]);
  
  // Funções para controlar o modal
  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingPet(null);
  };

  const handleAddNew = () => {
    setEditingPet(null); // Garante que o formulário estará vazio
    setIsModalOpen(true);
  };

  const handleEdit = (pet) => {
    setEditingPet(pet); // Passa os dados do pet para o formulário
    setIsModalOpen(true);
  };
  
  // Função para salvar (adicionar ou editar)
  const handleSave = async (petData) => {
    try {
      const payload = { ...petData, usuarioId: user.id };
      
      if (editingPet) {
        await updatePet(editingPet.id, payload);
      } else {
        await addPet(payload);
      }
      
      fetchPets(); // Recarrega a lista de pets para mostrar a alteração
      handleCloseModal(); // Fecha o modal
    } catch (err) {
      alert("Erro ao salvar o pet. Verifique os dados e tente novamente.");
      console.error(err);
    }
  };

  // Função para deletar
  const handleDelete = async (petId) => {
    if (window.confirm('Tem certeza que deseja remover este pet? Esta ação não pode ser desfeita.')) {
        try {
            await deletePet(petId);
            fetchPets(); // Recarrega a lista
        } catch (err) {
            alert('Falha ao remover o pet.');
        }
    }
  }

  // Define o título da página com base no papel do usuário
  const pageTitle = isAdmin ? "Gerenciar Todos os Pets" : "Meus Queridos Pets";

  const renderContent = () => {
    if (loading) {
      return <LoadingSpinner />;
    }
    if (error) {
      return <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>;
    }
    if (petsToDisplay.length === 0) {
      return <p>Nenhum pet encontrado.</p>;
    }
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
        {!isAdmin && ( // Apenas usuários comuns veem o botão para adicionar para si mesmos
            <button onClick={handleAddNew} style={{...buttonStyle, backgroundColor: '#28a745', color: 'white'}}>+ Adicionar Pet</button>
        )}
      </div>
      
      {renderContent()}

      {/* O componente do Modal é renderizado aqui, mas só aparece quando isModalOpen é true */}
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