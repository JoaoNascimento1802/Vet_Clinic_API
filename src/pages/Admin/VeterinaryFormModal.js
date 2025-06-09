import React, { useState, useEffect } from 'react';
import styles from './FormModal.module.css'; // Reutilizamos o mesmo estilo

const specialities = [
    'CLINICA_GERAL', 'CIRURGIA', 'ODONTOLOGIA', 'OFTALMOLOGIA',
    'DERMATOLOGIA', 'ORTOPEDIA', 'CARDIOLOGIA', 'ONCOLOGIA',
    'ANESTESIOLOGIA', 'EXAMES_IMAGEM', 'VACINACAO', 'INTERNACAO',
    'EMERGENCIA', 'FISIOTERAPIA'
];

const VeterinaryFormModal = ({ isOpen, onClose, onSave, vet }) => {
  const initialState = { name: '', email: '', password: '', crmv: '', specialityenum: 'CLINICA_GERAL', phone: '', imageurl: '' };
  const [formData, setFormData] = useState(initialState);

  useEffect(() => {
    if (vet) {
      setFormData({ ...vet, password: '' }); // Limpa a senha por segurança
    } else {
      setFormData(initialState);
    }
  }, [vet, isOpen]);

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  if (!isOpen) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>{vet ? 'Editar Veterinário' : 'Adicionar Novo Veterinário'}</h2>
        <form onSubmit={handleSubmit}>
          <input name="name" value={formData.name} onChange={handleChange} placeholder="Nome do Veterinário" required className={styles.input} />
          <input name="email" type="email" value={formData.email} onChange={handleChange} placeholder="Email" required className={styles.input} />
          <input name="password" type="password" value={formData.password} onChange={handleChange} placeholder={vet ? 'Nova Senha (deixe em branco para não alterar)' : 'Senha'} className={styles.input} />
          <input name="crmv" value={formData.crmv} onChange={handleChange} placeholder="CRMV" required className={styles.input} />
          <input name="phone" value={formData.phone} onChange={handleChange} placeholder="Telefone" required className={styles.input} />
          <input name="imageurl" value={formData.imageurl} onChange={handleChange} placeholder="URL da Imagem" required className={styles.input} />
          <select name="specialityenum" value={formData.specialityenum} onChange={handleChange} className={styles.input}>
            {specialities.map(spec => <option key={spec} value={spec}>{spec.replace('_', ' ')}</option>)}
          </select>
          <div className={styles.buttonGroup}>
            <button type="button" onClick={onClose} className={styles.cancelButton}>Cancelar</button>
            <button type="submit" className={styles.saveButton}>Salvar</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default VeterinaryFormModal;