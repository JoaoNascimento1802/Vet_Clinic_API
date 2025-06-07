import React, { useState } from 'react';
import { useNavigate, Link, useLocation } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import MainLayout from '../layouts/MainLayout';
import styles from './LoginPage.module.css'; // Importa o nosso novo arquivo de estilo

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(email, password);
      navigate(from, { replace: true });
    } catch (err) {
      setError(err.message || 'Falha ao fazer login. Verifique suas credenciais.');
    } finally {
        setLoading(false);
    }
  };

  return (
    <MainLayout>
      <div className={styles.pageContainer}>
        <div className={styles.formWrapper}>
          <h2 className={styles.formTitle}>Acessar Conta</h2>
          
          {error && <p className={styles.feedbackMessage}>{error}</p>}

          <form onSubmit={handleSubmit}>
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className={styles.input}
            />
            <input
              type="password"
              placeholder="Senha"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className={styles.input}
            />
            <button type="submit" className={styles.submitButton} disabled={loading}>
              {loading ? 'Entrando...' : 'Entrar'}
            </button>
          </form>

          <p className={styles.registerLink}>
            Não tem uma conta? <Link to="/register">Crie uma agora</Link>
          </p>
        </div>
      </div>
    </MainLayout>
  );
};

export default LoginPage;