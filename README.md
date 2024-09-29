
# TaskMaster

TaskMaster é um sistema de gerenciamento de tarefas e hábitos, desenvolvido para auxiliar no acompanhamento e organização de atividades diárias. Com uma interface intuitiva, é possível criar, editar, listar e excluir tarefas e hábitos, além de contar com um sistema de autenticação de usuários.

## Tecnologias Utilizadas

- **Java 17**: Linguagem principal utilizada no backend.
- **Spring Boot 3.1.5**: Framework para desenvolvimento de aplicações web, facilitando a criação de APIs e a integração com o frontend.
- **Thymeleaf**: Motor de templates usado para renderizar páginas HTML no servidor.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações de usuários, tarefas e hábitos.
- **Tailwind CSS**: Framework de utilitários CSS para estilização das páginas.
- **Bootstrap 5**: Framework CSS utilizado para design responsivo e componentes de interface.

## Funcionalidades

- **Cadastro e Login de Usuários**: Sistema de autenticação para registro e login de usuários, com validação de dados.
- **Gerenciamento de Tarefas**: Criação, edição, listagem e exclusão de tarefas, com controle de status (pendente/concluída).
- **Gerenciamento de Hábitos**: Adição de novos hábitos e visualização de hábitos existentes.
- **Página Inicial**: Resumo das tarefas e hábitos recentes, com acesso rápido a ações como ver tarefas, adicionar tarefas e ver hábitos.
- **Validação e Feedback**: Formulários com validação de dados e mensagens de erro/sucesso para melhor experiência do usuário.

## Instalação e Configuração

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/taskMaster.git
   ```
2. **Configurar o Banco de Dados**:
   - Certifique-se de ter o PostgreSQL instalado e em execução.
   - Crie um banco de dados para o projeto.
   - Configure as credenciais do banco no arquivo `application.properties` ou `application.yml` do Spring Boot.
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     ```

3. **Instalar as Dependências**:
   Navegue até a pasta do projeto e execute o seguinte comando para instalar as dependências:
   ```bash
   ./mvnw install
   ```

4. **Executar a Aplicação**:
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Acessar o Sistema**:
   Abra seu navegador e acesse `http://localhost:8080`.

## Estrutura do Projeto

- **Backend**: Desenvolvido em Java utilizando o Spring Boot, responsável pela lógica de negócios e integração com o banco de dados PostgreSQL.
- **Frontend**: Templates Thymeleaf renderizados pelo servidor, utilizando Tailwind CSS e Bootstrap para estilização e responsividade.

## Estilo e Design

O projeto utiliza uma combinação dos frameworks **Bootstrap** e **Tailwind CSS** para estilização das páginas, proporcionando uma interface moderna e responsiva.

## Contribuição

Se você deseja contribuir com o projeto, sinta-se à vontade para enviar pull requests ou abrir issues com sugestões e melhorias.

## Licença

Este projeto está sob a licença [MIT](https://choosealicense.com/licenses/mit/).

---

Feito com ❤️ por [Seu Nome]
