import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getClinicById } from '../api/clinicService';
import MainLayout from '../layouts/MainLayout';
import LoadingSpinner from '../components/common/loadingspinner/LoadingSpinner';

const ClinicDetailPage = () => {
  const { id } = useParams(); // Pega o ID da URL
  const [clinic, setClinic] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchClinic = async () => {
      try {
        setLoading(true);
        const data = await getClinicById(id);
        setClinic(data);
      } catch (err) {
        setError('Clínica não encontrada ou falha ao carregar.');
      } finally {
        setLoading(false);
      }
    };
    fetchClinic();
  }, [id]);

  if (loading) return <MainLayout><LoadingSpinner /></MainLayout>;
  if (error) return <MainLayout><p style={{ color: 'red' }}>{error}</p></MainLayout>;
  if (!clinic) return null;

  return (
    <MainLayout>
      <img src={clinic.imageurl} alt={clinic.name} style={{width: '100%', height: '300px', objectFit: 'cover'}}/>
      <h1>{clinic.name}</h1>
      <p><strong>Endereço:</strong> {clinic.address}</p>
      <p><strong>Email:</strong> {clinic.email}</p>
      <p><strong>Telefone:</strong> {clinic.phone}</p>
      <p><strong>Serviço Principal:</strong> {clinic.careServices.replace('_', ' ')}</p>
      {/* Aqui você pode adicionar mais detalhes, como um mapa, lista de veterinários, etc. */}
    </MainLayout>
  );
};

export default ClinicDetailPage;