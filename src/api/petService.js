import axiosInstance from './axiosInstance';

export const getAllPets = async () => {
  try {
    // Endpoint para usuários logados
    const response = await axiosInstance.get('/pets');
    return response.data;
  } catch (error) {
    console.error("Error fetching pets:", error.response || error.message);
    throw error;
  }
};

export const addPet = async (petData) => {
  try {
    const response = await axiosInstance.post('/pets', petData);
    return response.data;
  } catch (error) {
    console.error("Error adding pet:", error.response || error.message);
    throw error;
  }
};

export const updatePet = async (id, petData) => {
  try {
    const response = await axiosInstance.put(`/pets/${id}`, petData);
    return response.data;
  } catch (error) {
    console.error("Error updating pet:", error.response || error.message);
    throw error;
  }
};

export const deletePet = async (id) => {
  try {
    await axiosInstance.delete(`/pets/${id}`);
  } catch (error) {
    console.error("Error deleting pet:", error.response || error.message);
    throw error;
  }
};