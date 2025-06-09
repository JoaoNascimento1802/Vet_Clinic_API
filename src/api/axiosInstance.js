import axios from 'axios';

const API_BASE_URL = 'https://videogamee-audkgzdjceemames.brazilsouth-01.azurewebsites.net/'; // URL do seu backend

const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
});

// Interceptor para adicionar o token de autenticação a cada requisição
axiosInstance.interceptors.request.use(
  (config) => {
    // Pega o token do localStorage
    const token = localStorage.getItem('authToken');
    if (token) {
      // Adiciona o cabeçalho 'Authorization' no formato Bearer
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;