import axiosInstance from './axiosInstance';

export const getAllUsers = async () => {
  try {
    const response = await axiosInstance.get('/users');
    return response.data;
  } catch (error) {
    console.error("Error fetching users:", error.response || error.message);
    throw error;
  }
};

export const deleteUser = async (id) => {
    try {
      await axiosInstance.delete(`/users/${id}`);
    } catch (error)
   {
      console.error(`Error deleting user ${id}:`, error.response || error.message);
      throw error;
    }
  };