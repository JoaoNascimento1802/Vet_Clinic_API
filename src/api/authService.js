import axiosInstance from './axiosInstance';

// A função de login agora chama o backend de verdade
export const loginUser = async (email, password) => {
  try {
    const response = await axiosInstance.post('/auth/login', {
      email,
      password,
    });
    // O backend retorna um objeto { token: "..." }
    return response.data;
  } catch (error) {
    console.error("Login API error:", error.response || error.message);
    const errorMessage = error.response?.data?.message || 'Falha no login. Verifique suas credenciais.';
    throw new Error(errorMessage);
  }
};

// A função de registro continua igual e já é compatível
export const registerUser = async (userData) => {
  try {
    const payload = {
      ...userData,
      role: 'USER'
    };
    // O endpoint /users/register foi definido como público no SecurityConfig
    const response = await axiosInstance.post('/users/register', payload);
    return response.data;
  } catch (error) {
    console.error("Register API error:", error.response?.data || error.message);
    const errorMessage = error.response?.data?.message || 'Falha no registro. Verifique os dados.';
    throw new Error(errorMessage);
  }
};