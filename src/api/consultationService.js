import axiosInstance from './axiosInstance';

/**
 * Busca apenas as consultas do usuário autenticado.
 */
export const getMyConsultations = async () => {
  try {
    const response = await axiosInstance.get('/consultas/my-consultations');
    return response.data;
  } catch (error) {
    console.error("Error fetching my consultations:", error.response?.data || error);
    throw error;
  }
};

/**
 * Busca TODAS as consultas do sistema (apenas para Admin).
 */
export const getAllConsultationsForAdmin = async () => {
  try {
    const response = await axiosInstance.get('/consultas/all');
    return response.data;
  } catch (error) {
    console.error("Error fetching all consultations:", error.response?.data || error);
    throw error;
  }
};

/**
 * Cria uma nova consulta.
 */
export const createConsultation = async (consultationData) => {
  try {
    const response = await axiosInstance.post('/consultas', consultationData);
    return response.data;
  } catch (error) {
    console.error("Error creating consultation:", error.response?.data || error);
    throw error;
  }
};