import axiosInstance from './axiosInstance';

// --- Leitura ---
export const getAllClinics = async () => {
  try {
    const response = await axiosInstance.get('/clinic');
    return response.data;
  } catch (error) {
    console.error("Error fetching clinics:", error.response || error.message);
    throw error;
  }
};

export const getClinicById = async (id) => {
  try {
    const response = await axiosInstance.get(`/clinic/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching clinic ${id}:`, error.response || error.message);
    throw error;
  }
};

// --- Escrita (Admin) ---
export const addClinic = async (clinicData) => {
  try {
    const response = await axiosInstance.post('/clinic', clinicData);
    return response.data;
  } catch (error) {
    console.error("Error adding clinic:", error.response || error.message);
    throw error;
  }
};

export const updateClinic = async (id, clinicData) => {
  try {
    const response = await axiosInstance.put(`/clinic/${id}`, clinicData);
    return response.data;
  } catch (error) {
    console.error(`Error updating clinic ${id}:`, error.response || error.message);
    throw error;
  }
};

export const deleteClinic = async (id) => {
  try {
    await axiosInstance.delete(`/clinic/${id}`);
  } catch (error) {
    console.error(`Error deleting clinic ${id}:`, error.response || error.message);
    throw error;
  }
};