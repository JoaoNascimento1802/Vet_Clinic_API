import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { registerUser } from '../api/authService';
import MainLayout from '../layouts/MainLayout';
import styles from './RegisterPage.module.css'; // Importa o nosso novo arquivo de estilo

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    phone: '',
    address: '',
    rg: '',
    imageurl: 'https://i.pravatar.cc/150' // URL de imagem padrão
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);
    try {
      await registerUser(formData);
      setSuccess('Registro realizado com sucesso! Você será redirecionado para a página de login em 3 segundos.');
      setTimeout(() => navigate('/login'), 3000);
    } catch (err) {
      setError(err.message || 'Falha no registro. Verifique os dados e tente novamente.');
    } finally {
        setLoading(false);
    }
  };

  return (
    <MainLayout>
      <div className={styles.pageContainer}>
        <div className={styles.formWrapper}>
          <h2 className={styles.formTitle}>Crie sua Conta</h2>

          {error && <p className={`${styles.feedbackMessage} ${styles.error}`}>{error}</p>}
          {success && <p className={`${styles.feedbackMessage} ${styles.success}`}>{success}</p>}

          <form onSubmit={handleSubmit}>
            <div className={styles.inputGroup}>
                <input name="username" placeholder="Nome Completo" onChange={handleChange} required className={styles.input} />
            </div>
            <div className={styles.inputGroup}>
                <input name="email" type="email" placeholder="Seu melhor email" onChange={handleChange} required className={styles.input} />
            </div>
            <div className={styles.inputGroup}>
                <input name="password" type="password" placeholder="Crie uma senha forte" onChange={handleChange} required className={styles.input} />
            </div>
            <div className={`${styles.inputGroup} ${styles.split}`}>
                <input name="phone" placeholder="Telefone (ex: 11987654321)" onChange={handleChange} required className={styles.input} />
                <input name="rg" placeholder="RG (somente números)" onChange={handleChange} required className={styles.input} />
            </div>
            <div className={styles.inputGroup}>
                <input name="address" placeholder="Endereço completo" onChange={handleChange} required className={styles.input} />
            </div>
            
            <button type="submit" className={styles.submitButton} disabled={loading}>
              {loading ? 'Registrando...' : 'Criar Conta'}
            </button>
          </form>

          <p className={styles.loginLink}>
            Já tem uma conta? <Link to="/login">Faça Login</Link>
          </p>
        </div>
      </div>
    </MainLayout>
  );
};

export default RegisterPage;