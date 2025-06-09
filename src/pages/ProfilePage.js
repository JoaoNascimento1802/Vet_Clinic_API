import React from 'react';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';

// NOTA: Esta é uma página de visualização. Editar o perfil exigiria
// um endpoint no backend como PUT /users/me que não existe atualmente.
const ProfilePage = () => {
  const { user } = useAuth();

  if (!user) {
    return (
      <MainLayout>
        <p>Você precisa estar logado para ver seu perfil.</p>
      </MainLayout>
    );
  }

  return (
    <MainLayout>
      <h1>Perfil de {user.username}</h1>
      <div>
        <p><strong>ID de Usuário:</strong> {user.id}</p>
        <p><strong>Nome de Usuário:</strong> {user.username}</p>
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Perfil:</strong> {user.role}</p>
        {/* Você pode adicionar os outros campos (endereço, telefone) se eles forem incluídos no token JWT */}
      </div>
      <button style={{marginTop: '20px'}}>Editar Perfil (Funcionalidade Futura)</button>
    </MainLayout>
  );
};

export default ProfilePage;