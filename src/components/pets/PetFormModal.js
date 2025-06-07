import React, { useState, useEffect } from 'react';
import styles from '../../pages/Admin/FormModal.module.css'; // Reutilizamos o mesmo estilo do modal de clínicas

const PetFormModal = ({ isOpen, onClose, onSave, pet }) => {
  const initialState = { name: '', age: 0, speciespet: 'CACHORRO', gender: 'Macho', imageurl: '' };
  const [formData, setFormData] = useState(initialState);

  useEffect(() => {
    if (pet) {
      setFormData({
        name: pet.name || '',
        age: pet.age || 0,
        speciespet: pet.speciespet || 'CACHORRO',
        gender: pet.gender || 'Macho',
        imageurl: pet.imageurl || ''
      });
    } else {
      setFormData(initialState);
    }
  }, [pet, isOpen]);

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
        <h2>{pet ? 'Editar Pet' : 'Adicionar Novo Pet'}</h2>
        <form onSubmit={handleSubmit}>
          <input name="name" value={formData.name} onChange={handleChange} placeholder="Nome do Pet" required className={styles.input} />
          <input name="age" type="number" value={formData.age} onChange={handleChange} placeholder="Idade" required className={styles.input} />
          <input name="imageurl" value={formData.imageurl} onChange={handleChange} placeholder="URL da Imagem" required className={styles.input} />
          <select name="speciespet" value={formData.speciespet} onChange={handleChange} className={styles.input}>
            <option value="CACHORRO">Cachorro</option>
            <option value="GATO">Gato</option>
            <option value="PASSARO">Pássaro</option>
            <option value="PEIXE">Peixe</option>
            <option value="REPTIL">Réptil</option>
            <option value="ROEDOR">Roedor</option>
          </select>
          <select name="gender" value={formData.gender} onChange={handleChange} className={styles.input}>
            <option value="Macho">Macho</option>
            <option value="Femea">Fêmea</option>
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

export default PetFormModal;