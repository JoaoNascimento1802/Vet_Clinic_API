import React, { createContext, useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode'; // Importe a nova biblioteca
import { loginUser, registerUser } from '../api/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(() => localStorage.getItem('authToken'));
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        // Verifica se o token não expirou
        if (decodedToken.exp * 1000 > Date.now()) {
          // Os claims que você colocou no TokenService (role, userId, username)
          const userData = {
            id: decodedToken.userId,
            email: decodedToken.sub, // 'sub' (subject) é o email
            role: decodedToken.role,
            username: decodedToken.username,
          };
          setUser(userData);
        } else {
          // Token expirado
          localStorage.removeItem('authToken');
          setToken(null);
        }
      } catch (error) {
        console.error("Invalid token:", error);
        localStorage.removeItem('authToken');
        setToken(null);
      }
    }
    setLoading(false);
  }, [token]);

  const login = async (email, password) => {
    try {
      // authService.loginUser agora retorna { token: "..." }
      const response = await loginUser(email, password);
      const newToken = response.token;
      
      localStorage.setItem('authToken', newToken);
      setToken(newToken); // Isso vai disparar o useEffect para decodificar e definir o usuário

    } catch (error) {
      console.error('Login failed:', error);
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('authToken');
    setUser(null);
    setToken(null);
  };

  const register = async (userData) => {
    try {
      const newUser = await registerUser(userData);
      // Opcional: fazer login automaticamente após o registro
      // await login(userData.email, userData.password);
      return newUser;
    } catch (error) {
      console.error('Registration failed:', error);
      throw error;
    }
  };

  if (loading) {
    return <div>Carregando aplicação...</div>;
  }

  return (
    <AuthContext.Provider value={{ user, token, login, logout, register, isAuthenticated: !!user, isAdmin: user?.role === 'ADMIN' }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;