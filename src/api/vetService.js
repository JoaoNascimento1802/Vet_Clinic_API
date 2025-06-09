import axiosInstance from './axiosInstance';

export const getAllVeterinarians = async () => {
  try {
    const response = await axiosInstance.get('/veterinary');
    return response.data;
  } catch (error) {
    console.error("Error fetching veterinarians:", error.response || error.message);
    throw error;
  }
};

export const addVeterinary = async (vetData) => {
  try {
    const response = await axiosInstance.post('/veterinary', vetData);
    return response.data;
  } catch (error) {
    console.error("Error adding veterinarian:", error.response || error.message);
    throw error;
  }
};

export const updateVeterinary = async (id, vetData) => {
  try {
    const response = await axiosInstance.put(`/veterinary/${id}`, vetData);
    return response.data;
  } catch (error) {
    console.error(`Error updating veterinarian ${id}:`, error.response || error.message);
    throw error;
  }
};

export const deleteVeterinary = async (id) => {
  try {
    await axiosInstance.delete(`/veterinary/${id}`);
  } catch (error)
 {
    console.error(`Error deleting veterinarian ${id}:`, error.response || error.message);
    throw error;
  }
};