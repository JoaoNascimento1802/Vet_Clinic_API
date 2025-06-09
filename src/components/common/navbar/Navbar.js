import React, { useState } from 'react'; // Importe o useState
import { Link, NavLink, useNavigate } from 'react-router-dom';
import useAuth from '../../../hooks/useAuth';
import styles from './Navbar.module.css';

const Navbar = () => {
  const { user, logout, isAdmin } = useAuth();
  const navigate = useNavigate();
  
  // Novo estado para controlar o menu mobile
  const [menuOpen, setMenuOpen] = useState(false);

  const handleLogout = () => {
    setMenuOpen(false); // Fecha o menu ao deslogar
    logout();
    navigate('/login');
  };

  return (
    <nav className={styles.navbar}>
      <div className={styles.navContainer}>
        <Link to="/" className={styles.navLogo} onClick={() => setMenuOpen(false)}>
          VetClinic
        </Link>
        
        {/* Ícone do Menu Hambúrguer (só aparece em telas pequenas via CSS) */}
        <div className={styles.menuIcon} onClick={() => setMenuOpen(!menuOpen)}>
          ☰
        </div>

        {/* Adiciona a classe 'active' quando o menu está aberto */}
        <ul className={`${styles.navMenu} ${menuOpen ? styles.active : ''}`}>
          <li className={styles.navItem}>
            <NavLink to="/" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink} onClick={() => setMenuOpen(false)}>Home</NavLink>
          </li>
          <li className={styles.navItem}>
            <NavLink to="/clinics" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink} onClick={() => setMenuOpen(false)}>Clínicas</NavLink>
          </li>
          {/* ... outros links do menu, adicione onClick={() => setMenuOpen(false)} em todos ... */}
           <li className={styles.navItem}>
            <NavLink to="/my-pets" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink} onClick={() => setMenuOpen(false)}>Meus Pets</NavLink>
          </li>
          <li className={styles.navItem}>
            <NavLink to="/appointments" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink} onClick={() => setMenuOpen(false)}>Consultas</NavLink>
          </li>
          {isAdmin && (
            <li className={styles.navItem}>
                <NavLink to="/admin/dashboard" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink} onClick={() => setMenuOpen(false)}>Admin</NavLink>
            </li>
          )}
        </ul>
        
        {/* A parte de autenticação pode ser movida para dentro do menu mobile se desejar */}
        <div className={styles.navAuth}>
          {user ? (
            <>
              <span className={styles.userName}>Olá, {user.username}</span>
              <button onClick={handleLogout} className={styles.navButton}>Sair</button>
            </>
          ) : (
             <Link to="/login" className={`${styles.navLink} ${styles.navButton}`}>Login</Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;