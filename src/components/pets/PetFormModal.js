import React, { useState, useEffect } from 'react';
import styles from '../../pages/Admin/FormModal.module.css'; // Reutilizamos o mesmo estilo de modal

/**
 * Modal de Formulário para Adicionar ou Editar Pets.
 * @param {boolean} isOpen - Controla se o modal está visível.
 * @param {function} onClose - Função para fechar o modal.
 * @param {function} onSave - Função para salvar os dados do formulário.
 * @param {object|null} pet - Os dados do pet para edição, ou null para adição.
 * @param {boolean} isAdmin - Indica se o usuário logado é um administrador.
 * @param {Array} users - Lista de todos os usuários (necessária para o admin).
 */
const PetFormModal = ({ isOpen, onClose, onSave, pet, isAdmin, users }) => {
  // Estado inicial do formulário
  const initialState = {
    name: '',
    age: 0,
    speciespet: 'CACHORRO',
    gender: 'Macho',
    imageurl: '',
    usuarioId: '' // Campo para o dono do pet
  };
  const [formData, setFormData] = useState(initialState);

  // Efeito para popular o formulário quando ele abre
  useEffect(() => {
    if (isOpen) {
      if (pet) {
        // Modo de Edição: preenche com os dados do pet existente
        setFormData({
          name: pet.name || '',
          age: pet.age || 0,
          speciespet: pet.speciespet || 'CACHORRO',
          gender: pet.gender || 'Macho',
          imageurl: pet.imageurl || '',
          usuarioId: pet.usuarioId || '' // Mantém o dono original
        });
      } else {
        // Modo de Adição: reseta para o estado inicial
        setFormData(initialState);
      }
    }
  }, [pet, isOpen]);

  // Manipulador para atualizar o estado do formulário
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Manipulador para submeter o formulário
  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  // Não renderiza nada se o modal não estiver aberto
  if (!isOpen) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>{pet ? 'Editar Pet' : 'Adicionar Novo Pet'}</h2>
        <form onSubmit={handleSubmit}>

          {/* Seletor de Dono (visível apenas para administradores) */}
          {isAdmin && (
            <div className={styles.inputGroup}>
                <label>Dono do Pet</label>
                <select name="usuarioId" value={formData.usuarioId} onChange={handleChange} required className={styles.input}>
                <option value="">Selecione um Dono</option>
                {/* Popula o dropdown com a lista de usuários */}
                {users.map(user => (
                    <option key={user.id} value={user.id}>{user.username} ({user.email})</option>
                ))}
                </select>
            </div>
          )}

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