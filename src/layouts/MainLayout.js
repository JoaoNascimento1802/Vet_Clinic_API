import React from 'react';
import Navbar from '../components/common/navbar/Navbar';
import Footer from '../components/common/footer/Footer';

const layoutStyle = {
  display: 'flex',
  flexDirection: 'column',
  minHeight: '100vh',
};

const mainContentStyle = {
  flex: 1,
  padding: '20px',
  maxWidth: '1200px',
  width: '100%',
  margin: '0 auto',
};

const MainLayout = ({ children }) => {
  return (
    <div style={layoutStyle}>
      <Navbar />
      <main style={mainContentStyle}>
        {children}
      </main>
      <Footer />
    </div>
  );
};

export default MainLayout;