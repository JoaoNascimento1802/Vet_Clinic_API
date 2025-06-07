import React from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import useAuth from '../../../hooks/useAuth';
import styles from './Navbar.module.css';

const Navbar = () => {
  const { user, logout, isAdmin } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className={styles.navbar}>
      <div className={styles.navContainer}>
        <Link to="/" className={styles.navLogo}>
          VetClinic
        </Link>
        <ul className={styles.navMenu}>
          <li className={styles.navItem}>
            <NavLink to="/" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink}>Home</NavLink>
          </li>
          <li className={styles.navItem}>
            <NavLink to="/clinics" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink}>Clínicas</NavLink>
          </li>
          {isAdmin && (
            <li className={styles.navItem}>
              <NavLink to="/veterinarians" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink}>Veterinários</NavLink>
            </li>
          )}
          {user && (
            <>
              <li className={styles.navItem}>
                <NavLink to="/my-pets" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink}>Meus Pets</NavLink>
              </li>
              <li className={styles.navItem}>
                <NavLink to="/appointments" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink}>Consultas</NavLink>
              </li>
            </>
          )}
          {isAdmin && (
            <li className={styles.navItem}>
              <NavLink to="/admin/dashboard" className={({isActive}) => isActive ? `${styles.navLink} ${styles.active}` : styles.navLink}>Admin</NavLink>
            </li>
          )}
        </ul>
        <div className={styles.navAuth}>
          {user ? (
            <>
              <span className={styles.userName}>Olá, {user.username}</span>
              <button onClick={handleLogout} className={styles.navButton}>Sair</button>
            </>
          ) : (
            <>
              <Link to="/login" className={`${styles.navLink} ${styles.navButton}`}>Login</Link>
              <Link to="/register" className={`${styles.navLink} ${styles.navButton} ${styles.navButtonPrimary}`}>Registrar</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;