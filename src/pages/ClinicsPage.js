import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllClinics } from '../api/clinicService';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner'

const pageTitleStyle = { fontSize: '2rem', color: '#333', textAlign: 'center' };
// ... outros estilos ...

const ClinicsPage = () => {
  const [clinics, setClinics] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // ... lógica de fetch ...
  }, []);

  if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;
  if (error) return <MainLayout><p style={{color: 'red'}}>{error}</p></MainLayout>;

  return (
    <MainLayout>
      <h1 style={pageTitleStyle}>Nossas Clínicas Parceiras</h1>
      <div /* ... estilo da lista ... */>
        {clinics.map((clinic) => (
          <div key={clinic.id} /* ... estilo do card ... */>
            {/* ... conteúdo do card ... */}
            <Link to={`/clinics/${clinic.id}`} style={{fontWeight: 'bold'}}>Ver Detalhes</Link>
          </div>
        ))}
      </div>
    </MainLayout>
  );
};

export default ClinicsPage;