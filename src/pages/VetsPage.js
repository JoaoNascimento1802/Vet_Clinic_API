import React, { useState, useEffect } from 'react';
import { getAllVeterinarians } from '../api/vetService';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner'

// Reutilize os estilos da ClinicsPage
const pageTitleStyle = { fontSize: '2rem', color: '#333', textAlign: 'center' };
const listStyle = { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' };
const cardStyle = { border: '1px solid #e0e0e0', borderRadius: '8px', padding: '20px', backgroundColor: '#fff' };
const imageStyle = { width: '100px', height: '100px', objectFit: 'cover', borderRadius: '50%', marginBottom: '15px' };

const VetsPage = () => {
  const [vets, setVets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchVets = async () => {
      try {
        setLoading(true);
        const data = await getAllVeterinarians();
        setVets(data);
      } catch (err) {
        setError('Acesso negado ou falha ao carregar veterinários. Apenas administradores podem ver esta página.');
      } finally {
        setLoading(false);
      }
    };
    fetchVets();
  }, []);

  if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;

  return (
    <MainLayout>
      <h1 style={pageTitleStyle}>Corpo Clínico</h1>
      {error && <p style={{ color: 'red', textAlign: 'center' }}>{error}</p>}
      
      <div style={listStyle}>
        {vets.map((vet) => (
          <div key={vet.id} style={{...cardStyle, textAlign: 'center'}}>
            <img src={vet.imageurl} alt={vet.name} style={imageStyle} />
            <h3 style={{fontSize: '1.4rem'}}>{vet.name}</h3>
            <p><strong>Especialidade:</strong> {vet.specialityenum.replace('_', ' ')}</p>
            <p><strong>CRMV:</strong> {vet.crmv}</p>
            <p><strong>Email:</strong> {vet.email}</p>
          </div>
        ))}
      </div>
    </MainLayout>
  );
};

export default VetsPage;