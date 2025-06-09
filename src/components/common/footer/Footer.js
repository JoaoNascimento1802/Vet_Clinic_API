import React from 'react';

const footerStyle = {
  backgroundColor: '#2c3e50',
  color: 'white',
  textAlign: 'center',
  padding: '2rem 1rem',
  marginTop: 'auto', // Garante que o footer fique no final
  fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif"
};

const Footer = () => {
  return (
    <footer style={footerStyle}>
      <p>&copy; {new Date().getFullYear()} VetClinic. Todos os direitos reservados.</p>
      <p>Cuidando do seu melhor amigo com tecnologia e carinho.</p>
    </footer>
  );
};

export default Footer;