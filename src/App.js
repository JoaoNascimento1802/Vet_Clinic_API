import React from 'react';
import { AuthProvider } from './contexts/AuthContext';
import AppRoutes from './routes/AppRoutes';

/**
 * Este é o componente principal da sua aplicação.
 * Ele não tem visual próprio, mas organiza os componentes
 * essenciais que devem funcionar em todo o site.
 */
function App() {
  return (
    // O AuthProvider disponibiliza os dados de autenticação (usuário, token, etc.)
    // para todos os componentes filhos, que no caso é a aplicação inteira.
    <AuthProvider>
      
      {/* O AppRoutes é o componente que gerencia qual página será
          exibida para o usuário com base no endereço da web. */}
      <AppRoutes />

    </AuthProvider>
  );
}

export default App;