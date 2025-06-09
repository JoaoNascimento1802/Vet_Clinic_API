import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './BackButton.module.css';

/**
 * Um botão reutilizável que navega para a página anterior no histórico do navegador.
 */
const BackButton = () => {
    // O hook useNavigate nos dá acesso à função de navegação do React Router
    const navigate = useNavigate();

    // Esta função simplesmente diz ao router para voltar uma página (-1)
    const handleGoBack = () => {
        navigate(-1);
    };

    return (
        <button onClick={handleGoBack} className={styles.backButton}>
            <span className={styles.arrow}>‹</span>
            <span>Voltar</span>
        </button>
    );
};

export default BackButton;