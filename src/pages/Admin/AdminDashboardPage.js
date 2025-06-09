import React from 'react';
import { Link } from 'react-router-dom';
import MainLayout from '../../layouts/MainLayout';

const dashboardStyles = {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '20px'
};
const cardStyles = {
    padding: '30px',
    border: '1px solid #ddd',
    borderRadius: '8px',
    textDecoration: 'none',
    color: '#333',
    textAlign: 'center',
    backgroundColor: 'white',
    boxShadow: '0 2px 5px rgba(0,0,0,0.1)',
    transition: 'transform 0.2s ease, box-shadow 0.2s ease'
};

const cardHoverStyle = (e) => {
    e.currentTarget.style.transform = 'translateY(-5px)';
    e.currentTarget.style.boxShadow = '0 8px 15px rgba(0,0,0,0.1)';
}

const cardUnhoverStyle = (e) => {
    e.currentTarget.style.transform = 'translateY(0)';
    e.currentTarget.style.boxShadow = '0 2px 5px rgba(0,0,0,0.1)';
}

const AdminDashboardPage = () => {
    return (
        <MainLayout>
            <h1>Painel do Administrador</h1>
            <p style={{marginBottom: '2rem'}}>Selecione uma área para gerenciar.</p>
            <div style={dashboardStyles}>
                <Link to="/admin/clinics" style={cardStyles} onMouseEnter={cardHoverStyle} onMouseLeave={cardUnhoverStyle}>
                    <h2>Gerenciar Clínicas</h2>
                </Link>
                <Link to="/admin/vets" style={cardStyles} onMouseEnter={cardHoverStyle} onMouseLeave={cardUnhoverStyle}>
                    <h2>Gerenciar Veterinários</h2>
                </Link>
                <Link to="/admin/users" style={cardStyles} onMouseEnter={cardHoverStyle} onMouseLeave={cardUnhoverStyle}>
                    <h2>Gerenciar Usuários</h2>
                </Link>
                <Link to="/admin/pets" style={cardStyles}> {/* <-- MUDE O LINK AQUI */}
                    <h2>Gerenciar Pets</h2>
                </Link>
                <Link to="/admin/reports" style={cardStyles}>
                    <h2>Gerar Relatórios</h2>
                </Link>
            </div>
        </MainLayout>
    );
};

export default AdminDashboardPage;