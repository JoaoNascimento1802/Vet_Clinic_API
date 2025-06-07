import React from 'react';
import { Link } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import useAuth from '../hooks/useAuth';

const heroStyle = {
  textAlign: 'center',
  padding: '80px 20px',
  background: 'linear-gradient(to right, #3498db, #2980b9)',
  color: 'white',
  fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
};

const ctaButtonStyle = {
    display: 'inline-block',
    padding: '12px 25px',
    backgroundColor: '#fff',
    color: '#3498db',
    borderRadius: '5px',
    textDecoration: 'none',
    fontWeight: 'bold',
    marginTop: '20px',
    transition: 'transform 0.2s ease',
};

const HomePage = () => {
  const { isAuthenticated, user } = useAuth();

  return (
    <MainLayout>
      <div style={heroStyle}>
        <h1>Bem-vindo à VetClinic</h1>
        <p style={{ fontSize: '1.2rem', opacity: 0.9 }}>A saúde e o bem-estar do seu pet em primeiro lugar.</p>
        {isAuthenticated ? (
          <div>
            <h2 style={{fontWeight: 'normal'}}>Olá, {user.username}!</h2>
            <Link to="/my-pets" style={ctaButtonStyle}>Ver Meus Pets</Link>
          </div>
        ) : (
          <Link to="/register" style={ctaButtonStyle}>Crie sua Conta</Link>
        )}
      </div>
      {/* Aqui você pode adicionar seções com features, depoimentos, etc. */}
    </MainLayout>
  );
};

export default HomePage;