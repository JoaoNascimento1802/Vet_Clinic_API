# 🐾 VetClinic Frontend (React)

![React](https://img.shields.io/badge/React-18.x-61DAFB?style=for-the-badge&logo=react)
![JavaScript](https://img.shields.io/badge/JavaScript-ES6%2B-F7DF1E?style=for-the-badge&logo=javascript)
![Vercel](https://img.shields.io/badge/Deployed%20on-Vercel-black?style=for-the-badge&logo=vercel)
![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)

Este é o projeto frontend da plataforma **VetClinic**, desenvolvido como uma **Single-Page Application (SPA)** usando React. Ele consome os dados da [VetClinic API](https://github.com/JoaoNascimento1802/Vet_Clinic_API) para fornecer uma interface de usuário interativa e responsiva para clientes e administradores do sistema.

---

## 🚀 Acesso ao Projeto

- **Frontend (Site Online):** **[vet-clinic-api-front.vercel.app](https://vet-clinic-api-front.vercel.app/)**
- **Documentação da API (Swagger):** **[API Backend no Azure](https://videogamee-audkgzdjceemames.brazilsouth-01.azurewebsites.net/swagger-ui.html)**

- **Repositório Frontend:** **[Branch Main_Front no repositório principal](https://github.com/JoaoNascimento1802/Vet_Clinic_API/tree/Main_Front)**

> **Credenciais de Administrador para Teste:**
> - **Email:** `meuadmin@vetclinic.com`
> - **Senha:** `MinhaSenhaAdmin@123`

---

## 📌 Telas e Funcionalidades Implementadas

A interface foi projetada para ser intuitiva e fornecer fluxos de trabalho completos para cada tipo de usuário.

### Telas Públicas
- **Página Inicial:** Tela de boas-vindas.
- **Login / Registro:** Formulários para autenticação e criação de novas contas de usuário.
- **Lista de Clínicas:** Visualização pública das clínicas parceiras.

### Telas de Usuário Autenticado (`USER`)
- **Meus Pets:**
  - Visualização dos pets do próprio usuário em formato de cards.
  - Funcionalidade completa de **CRUD** (Adicionar, Editar, Remover) através de um formulário em modal.
- **Agendamentos:**
  - Formulário completo para agendar novas consultas, com dropdowns dinâmicos para pets, clínicas e veterinários.
  - Histórico de consultas, exibindo apenas as consultas do próprio usuário.
- **Meu Perfil:** Página para visualização dos dados da conta.

### Telas de Administrador (`ADMIN`)
- **Painel de Controle:** Hub central com links para todas as telas de gerenciamento.
- **Telas de Gerenciamento Completas (CRUD em Tabela):**
  - **Gerenciar Clínicas:** Listagem, adição, edição e exclusão de clínicas.
  - **Gerenciar Veterinários:** Listagem, adição, edição e exclusão de veterinários.
  - **Gerenciar Usuários:** Listagem de todos os usuários com opção de exclusão.
  - **Gerenciar Pets:** Visão global de **todos** os pets do sistema, com CRUD completo e a capacidade de atribuir um pet a qualquer usuário.
- **Gerar Relatórios:** Página com filtros de data para solicitar a geração de relatórios em PDF da API.
- **Navegação Responsiva:** Componente de "Voltar" para melhorar a usabilidade e a navegação entre as telas.

---

## ✨ Arquitetura e Tecnologias

- **Framework:** React 18+ (usando Create React App).
- **Arquitetura de Componentes:** O projeto é construído com componentes funcionais e o uso extensivo de React Hooks (`useState`, `useEffect`, `useCallback`).
- **Gerenciamento de Estado:**
  - **Estado Local:** Gerenciado por `useState` dentro de cada componente.
  - **Estado Global:** O `Context API` é utilizado para gerenciar o estado de autenticação (usuário logado, token, status de admin), disponibilizando-o para toda a aplicação.
- **Navegação (Routing):** `React Router DOM` é usado para criar as rotas da SPA e permitir a navegação entre as páginas sem recarregar o navegador.
- **Comunicação com API:** A biblioteca `Axios` é usada para todas as requisições HTTP para a API backend, centralizada em uma instância configurada.
- **Estilização:** **CSS Modules** para criar estilos encapsulados por componente, evitando conflitos de classes. O design é **responsivo**, adaptando-se a telas de celular através de Media Queries.
- **Deploy:** A aplicação está hospedada na **Vercel**, com integração contínua a partir do repositório Git.

---

## ⚙️ Como Executar Localmente

### Pré-requisitos
- Node.js (versão 18 ou superior)
- npm ou yarn

### Configuração
1.  Clone o repositório e navegue para a pasta do frontend:
    ```bash
    git clone [https://github.com/JoaoNascimento1802/Vet_Clinic_API.git](https://github.com/JoaoNascimento1802/Vet_Clinic_API.git)
    cd Vet_Clinic_API/Vet_Clinic_API-main_frontend 
    ```
    *(Ajuste o nome da pasta `Vet_Clinic_API-main_frontend` se for diferente)*

2.  Instale todas as dependências do projeto:
    ```bash
    npm install
    ```

3.  **Crie o arquivo de ambiente (Passo Essencial):**
    - Na raiz da pasta do seu projeto frontend, crie um novo arquivo chamado `.env`.
    - Dentro deste arquivo, adicione a seguinte linha (assumindo que seu backend está rodando localmente na porta 8080):
    ```
    REACT_APP_API_URL=http://localhost:8080
    ```

4.  Execute a aplicação:
    ```bash
    npm start
    ```

5.  O site será aberto automaticamente no seu navegador em [http://localhost:3000](http://localhost:3000).

---

## 🗂 Estrutura de Pastas (Simplificada)
