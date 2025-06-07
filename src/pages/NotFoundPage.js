import React from 'react';
import { Link } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';

const notFoundStyle = {
  textAlign: 'center',
  padding: '100px 20px',
  fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
};

const NotFoundPage = () => {
  return (
    <MainLayout>
      <div style={notFoundStyle}>
        <h1 style={{fontSize: '5rem', color: '#34495e'}}>404</h1>
        <h2 style={{fontSize: '2rem', color: '#7f8c8d'}}>Página não encontrada</h2>
        <p>A página que você está procurando não existe ou foi movida.</p>
        <Link to="/" style={{color: '#3498db', fontWeight: 'bold', marginTop: '20px', display: 'inline-block'}}>
          Voltar para a Home
        </Link>
      </div>
    </MainLayout>
  );
};

export default NotFoundPage;