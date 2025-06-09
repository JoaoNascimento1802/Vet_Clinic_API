import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css'; // <--- ESTA LINHA IMPORTA OS ESTILOS GLOBAIS
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);