import React from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

const ProtectedRoute = ({ roles }) => {
  const { user, isAuthenticated } = useAuth();
  const location = useLocation();

  if (!isAuthenticated) {
    // Redireciona para o login se não estiver autenticado
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Verifica se o usuário tem um dos papéis permitidos
  const isAuthorized = roles && roles.includes(user?.role);

  if (!isAuthorized) {
    // Redireciona para uma página de "Não Autorizado" se não tiver o papel correto
    // Você pode criar uma página simples para isso. Por enquanto, redirecionamos para a home.
    return <Navigate to="/" state={{ from: location }} replace />;
  }

  // Se estiver autenticado e autorizado, renderiza a rota filha (página)
  return <Outlet />;
};

export default ProtectedRoute;