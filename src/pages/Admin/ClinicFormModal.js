import React, { useState, useEffect } from 'react';
import styles from './FormModal.module.css'; // Criaremos este CSS

const ClinicFormModal = ({ isOpen, onClose, onSave, clinic }) => {
  const [formData, setFormData] = useState({ name: '', email: '', phone: '', address: '', careServices: 'CLINICA_GERAL', imageurl: '' });

  useEffect(() => {
    // Preenche o formulário se estiver editando uma clínica existente
    if (clinic) {
      setFormData(clinic);
    } else {
      // Limpa o formulário se for para adicionar uma nova
      setFormData({ name: '', email: '', phone: '', address: '', careServices: 'CLINICA_GERAL', imageurl: '' });
    }
  }, [clinic, isOpen]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  if (!isOpen) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>{clinic ? 'Editar Clínica' : 'Adicionar Nova Clínica'}</h2>
        <form onSubmit={handleSubmit}>
          <input name="name" value={formData.name} onChange={handleChange} placeholder="Nome da Clínica" required className={styles.input} />
          <input name="email" type="email" value={formData.email} onChange={handleChange} placeholder="Email" required className={styles.input} />
          <input name="phone" value={formData.phone} onChange={handleChange} placeholder="Telefone" required className={styles.input} />
          <input name="address" value={formData.address} onChange={handleChange} placeholder="Endereço" required className={styles.input} />
          <input name="imageurl" value={formData.imageurl} onChange={handleChange} placeholder="URL da Imagem" required className={styles.input} />
          {/* Adicione um <select> para careServices se desejar */}
          <div className={styles.buttonGroup}>
            <button type="button" onClick={onClose} className={styles.cancelButton}>Cancelar</button>
            <button type="submit" className={styles.saveButton}>Salvar</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ClinicFormModal;