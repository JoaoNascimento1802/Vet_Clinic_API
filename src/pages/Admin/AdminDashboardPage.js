import React from 'react';
import { Link } from 'react-router-dom';
import MainLayout from '../../layouts/MainLayout';

const dashboardStyles = {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '20px'
};
const cardStyles = {
    padding: '20px',
    border: '1px solid #ddd',
    borderRadius: '8px',
    textDecoration: 'none',
    color: '#333',
    textAlign: 'center',
    boxShadow: '0 2px 5px rgba(0,0,0,0.1)'
};

const AdminDashboardPage = () => {
    return (
        <MainLayout>
            <h1>Painel do Administrador</h1>
            <div style={dashboardStyles}>
                <Link to="/admin/clinics" style={cardStyles}>
                    <h2>Gerenciar Clínicas</h2>
                </Link>
                <Link to="/admin/vets" style={cardStyles}>
                    <h2>Gerenciar Veterinários</h2>
                </Link>
                <Link to="/admin/users" style={cardStyles}>
                    <h2>Gerenciar Usuários</h2>
                </Link>
                {/* Adicione outros links aqui */}
            </div>
        </MainLayout>
    );
};

export default AdminDashboardPage;