import React, { useState, useEffect } from 'react';
import styles from './FormModal.module.css'; // Reutilizamos o mesmo estilo

// Array de especialidades CORRETO e COMPLETO, baseado na sua classe Java
const specialities = [
    'CLINICO_GERAL', 'ANESTESIOLOGIA', 'CARDIOLOGIA', 'DERMATOLOGIA',
    'ENDOCRINOLOGIA', 'GASTROENTEROLOGIA', 'NEUROLOGIA', 'NUTRICAO',
    'OFTALMOLOGIA', 'ONCOLOGIA', 'ORTOPEDIA', 'REPRODUCAO_ANIMAL',
    'PATOLOGIA', 'CIRURGIA_GERAL', 'CIRURGIA_ORTOPEDICA', 'ODONTOLOGIA',
    'ZOOTECNIA', 'EXOTICOS', 'ACUPUNTURA', 'FISIOTERAPIA', 'IMAGINOLOGIA'
];

const VeterinaryFormModal = ({ isOpen, onClose, onSave, vet }) => {
  const initialState = { name: '', email: '', password: '', crmv: '', specialityenum: 'CLINICO_GERAL', phone: '', imageurl: '' };
  const [formData, setFormData] = useState(initialState);

  useEffect(() => {
    if (vet) {
      setFormData({ ...vet, password: '' }); 
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
          
          {/* AQUI ESTÁ A LÓGICA CORRIGIDA DO DROPDOWN */}
          <select name="specialityenum" value={formData.specialityenum} onChange={handleChange} required className={styles.input}>
            {specialities.map(spec => (
              <option key={spec} value={spec}>
                {/* Transforma 'CLINICO_GERAL' em 'CLINICO GERAL' apenas para exibição */}
                {spec.replace(/_/g, ' ')}
              </option>
            ))}
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