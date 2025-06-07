import axiosInstance from './axiosInstance';

export const createConsultation = async (consultationData) => {
  try {
    const response = await axiosInstance.post('/consultas', consultationData);
    return response.data;
  } catch (error) {
    console.error("Error creating consultation:", error.response || error.message);
    throw error;
  }
};

export const getAllConsultations = async () => {
    try {
      const response = await axiosInstance.get('/consultas');
      return response.data;
    } catch (error) {
      console.error("Error fetching consultations:", error.response || error.message);
      throw error;
    }
  };