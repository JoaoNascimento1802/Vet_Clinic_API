import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { getAllClinics } from '../api/clinicService';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';
import useAuth from '../hooks/useAuth'; // Importe o useAuth

const pageTitleStyle = { fontSize: '2rem', color: '#333', textAlign: 'center' };
const listStyle = { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' };
const cardStyle = { border: '1px solid #e0e0e0', borderRadius: '8px', padding: '20px', backgroundColor: '#fff' };
const imageStyle = { width: '100%', height: '180px', objectFit: 'cover', borderRadius: '4px', marginBottom: '15px' };
const errorBoxStyle = { border: '1px solid #f5c6cb', backgroundColor: '#f8d7da', color: '#721c24', padding: '1rem', borderRadius: '8px', textAlign: 'center' };

const ClinicsPage = () => {
  const [clinics, setClinics] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const { isAdmin } = useAuth(); // Pega o status de admin do usuário logado

  const fetchClinics = useCallback(async () => {
    // Só tenta buscar os dados se o usuário for admin
    if (!isAdmin) {
      setError('Acesso negado. Apenas administradores podem ver esta página.');
      setLoading(false);
      return;
    }

    try {
      setLoading(true);
      const data = await getAllClinics();
      setClinics(data);
    } catch (err) {
      // Trata erros de rede ou outros problemas da API
      setError('Falha ao carregar clínicas. Tente novamente mais tarde.');
      console.error(err);
    } finally {
      // Este bloco SEMPRE será executado, parando o "loading infinito"
      setLoading(false);
    }
  }, [isAdmin]); // A função será recriada se o status de admin mudar

  useEffect(() => {
    fetchClinics();
  }, [fetchClinics]);

  // Renderiza o conteúdo com base nos estados
  const renderContent = () => {
    if (loading) {
      return <LoadingSpinner />;
    }
    if (error) {
      return <div style={errorBoxStyle}>{error}</div>;
    }
    if (clinics.length === 0) {
      return <p>Nenhuma clínica encontrada.</p>;
    }
    return (
      <div style={listStyle}>
        {clinics.map((clinic) => (
          <div key={clinic.id} style={cardStyle}>
            <img src={clinic.imageurl || 'https://via.placeholder.com/300x180.png?text=Clínica'} alt={clinic.name} style={imageStyle} />
            <h3 style={{fontSize: '1.4rem', color: '#2c3e50', marginBottom: '8px'}}>{clinic.name}</h3>
            <p><strong>Email:</strong> {clinic.email}</p>
            {/* Adicione um Link para Detalhes se desejar */}
          </div>
        ))}
      </div>
    );
  };

  return (
    <MainLayout>
      <h1 style={pageTitleStyle}>Clínicas Parceiras</h1>
      {renderContent()}
    </MainLayout>
  );
};

export default ClinicsPage;