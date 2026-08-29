# 🐾 VetClinic API

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring)
![JWT](https://img.shields.io/badge/Security-JWT-black?style=for-the-badge&logo=jsonwebtokens)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql)
![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)

**VetClinic API** é um sistema backend completo e robusto para o gerenciamento de clínicas veterinárias, desenvolvido com Spring Boot. A plataforma oferece um sistema de autenticação seguro baseado em JWT, controle de acesso por papéis (Usuário e Administrador) e uma API RESTful completa para gerenciar clínicas, veterinários, pets, usuários, consultas e relatórios.

---

## 🎯 Funcionalidades Principais

A aplicação possui dois níveis de acesso principais: Usuário Comum e Administrador.

### Para Usuários Comuns (`ROLE_USER`):
- ✅ **Autenticação Segura:** Cadastro de novas contas e login com JWT.
- ✅ **Visualização de Informações:** Acesso à lista de clínicas e veterinários disponíveis.
- ✅ **Gerenciamento de Pets:** CRUD completo para seus próprios animais de estimação.
- ✅ **Agendamento de Consultas:**
  - Capacidade de agendar novas consultas para seus pets, escolhendo a clínica, especialidade e veterinário.
  - O sistema valida conflitos de horário, impedindo agendamentos com menos de 40 minutos de intervalo para o mesmo médico.
- ✅ **Histórico:** Visualização de seu próprio histórico de consultas agendadas.

### Para Administradores (`ROLE_ADMIN`):
- ✅ **Acesso Total:** Todas as funcionalidades de um usuário comum.
- ✅ **Painel de Controle:** Acesso a uma área de gerenciamento centralizada.
- ✅ **CRUD Completo:** Gerenciamento total de **todas** as entidades do sistema:
    - Clínicas
    - Veterinários
    - Usuários (listagem e exclusão)
    - Pets (de todos os usuários)
    - Consultas (de todos os usuários)
- ✅ **Geração de Relatórios:** Ferramenta para gerar relatórios em PDF das movimentações de consultas, com filtros por período.

---

## 🏗️ Arquitetura e Padrões

O projeto foi construído seguindo as melhores práticas de desenvolvimento para APIs REST, garantindo um código limpo, seguro e escalável.

- **API RESTful:** Endpoints bem definidos seguindo as convenções REST para cada recurso.
- **Arquitetura em Camadas:** Divisão clara de responsabilidades entre `Controller` (camada de API), `Service` (camada de regras de negócio) e `Repository` (camada de acesso a dados).
- **Segurança Stateless com JWT:** A autenticação é feita via tokens JWT, tornando a API sem estado e ideal para ser consumida por SPAs (Single-Page Applications) ou aplicativos móveis.
- **Controle de Acesso por Papel (RBAC):** Spring Security é usado para definir permissões granulares para cada endpoint com base nos papéis `ROLE_USER` e `ROLE_ADMIN`.
- **Padrão DTO (Data Transfer Object):** Uso extensivo de DTOs para requisição e resposta, desacoplando a API da estrutura do banco de dados, aumentando a segurança e evitando erros de serialização.
- **Mappers:** Classes dedicadas para a conversão entre Entidades JPA e DTOs.
- **Gerenciamento de Exceções Global:** Um `@RestControllerAdvice` centraliza o tratamento de erros, retornando respostas JSON padronizadas e amigáveis para o frontend.
- **Perfis de Configuração:** Uso de perfis (`dev` e `prod`) para separar as configurações de banco de dados de desenvolvimento (H2 em memória) e produção (MySQL).

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework Principal:** Spring Boot 3.x
- **Módulos Spring:** Spring Web, Spring Data JPA, Spring Security
- **Banco de Dados:** H2 (para desenvolvimento local) e MySQL (para produção)
- **Persistência:** Hibernate
- **Segurança:** JSON Web Tokens (JJWT)
- **Geração de PDF:** iTextPDF
- **Ferramentas:** Lombok, Maven
- **Documentação da API:** Swagger / OpenAPI 3

---

## ⚙️ Como Executar Localmente

### Pré-requisitos
- JDK 17 ou superior
- Apache Maven 3.8+

### Configuração
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/JoaoNascimento1802/Vet_Clinic_API.git](https://github.com/JoaoNascimento1802/Vet_Clinic_API.git)
    cd Vet_Clinic_API
    ```
2.  **Nenhuma configuração de banco de dados é necessária para rodar localmente!** O projeto está configurado com um perfil `dev` que utiliza o banco de dados em memória H2 por padrão. Ele será criado e populado automaticamente ao iniciar a aplicação.

3.  Execute o projeto usando o Maven Wrapper:
    ```bash
    ./mvnw spring-boot:run
    ```

4.  A API estará disponível em `http://localhost:8080`.

5.  Acesse a documentação interativa da API via Swagger em:
    [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 👨‍💻 Autor

Este projeto foi inteiramente desenvolvido e mantido por:

- **João Emanuel** - [@JoaoNascimento1802](https://github.com/JoaoNascimento1802)

---

## 📄 Licença

Este projeto é para fins educacionais e de portfólio, demonstrando a aplicação de tecnologias modernas de backend e boas práticas de desenvolvimento.
