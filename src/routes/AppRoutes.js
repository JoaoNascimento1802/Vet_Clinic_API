import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProtectedRoute from './ProtectedRoute';

// Páginas Públicas
import HomePage from '../pages/HomePage';
import LoginPage from '../pages/LoginPage';
import RegisterPage from '../pages/RegisterPage';
import ClinicsPage from '../pages/ClinicsPage';
import ClinicDetailPage from '../pages/ClinicDetailPage';
import NotFoundPage from '../pages/NotFoundPage';

// Páginas de Usuário Autenticado
import MyPetsPage from '../pages/MyPetsPage';
import AppointmentsPage from '../pages/AppointmentsPage';
import ProfilePage from '../pages/ProfilePage';

// Páginas de Administrador
import AdminDashboardPage from '../pages/Admin/AdminDashboardPage';
import AdminClinicsPage from '../pages/Admin/AdminClinicsPage';
import AdminVetsPage from '../pages/Admin/AdminVetsPage';
import AdminUsersPage from '../pages/Admin/AdminUsersPage';
import VetsPage from '../pages/VetsPage'; // Página de veterinários agora é de Admin

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        {/* ======================================= */}
        {/* 1. ROTAS PÚBLICAS             */}
        {/* ======================================= */}
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/clinics" element={<ClinicsPage />} />
        <Route path="/clinics/:id" element={<ClinicDetailPage />} />


        {/* ====================================================== */}
        {/* 2. ROTAS PARA USUÁRIOS LOGADOS (USER E ADMIN)     */}
        {/* ====================================================== */}
        <Route element={<ProtectedRoute roles={['USER', 'ADMIN']} />}>
          <Route path="/my-pets" element={<MyPetsPage />} />
          <Route path="/appointments" element={<AppointmentsPage />} />
          <Route path="/profile" element={<ProfilePage />} />
        </Route>


        {/* =============================================== */}
        {/* 3. ROTAS EXCLUSIVAS PARA ADMIN        */}
        {/* =============================================== */}
        <Route element={<ProtectedRoute roles={['ADMIN']} />}>
          <Route path="/admin/dashboard" element={<AdminDashboardPage />} />
          <Route path="/admin/clinics" element={<AdminClinicsPage />} />
          <Route path="/admin/vets" element={<AdminVetsPage />} />
          <Route path="/admin/users" element={<AdminUsersPage />} />
          
          {/* A lista de veterinários também é uma rota de admin, conforme o backend */}
          <Route path="/veterinarians" element={<VetsPage />} /> 
        </Route>


        {/* =============================================== */}
        {/* 4. ROTA DE PÁGINA NÃO ENCONTRADA      */}
        {/* =============================================== */}
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;