# TaskMaster


# TaskMaster

## Configurar o Banco de Dados

Certifique-se de ter o PostgreSQL instalado e em execução. Siga as etapas abaixo para configurar o banco de dados para o projeto.

1. Crie um banco de dados para o projeto no PostgreSQL.
2. Configure as credenciais do banco no arquivo `application.properties` ou `application.yml` do Spring Boot, conforme o exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Instalar as Dependências

Navegue até a pasta do projeto e execute o seguinte comando para instalar as dependências:

```bash
./mvnw install
```

## Executar a Aplicação

Após a instalação das dependências, execute o seguinte comando para iniciar a aplicação:

```bash
./mvnw spring-boot:run
```

## Acessar o Sistema

Abra seu navegador e acesse: [http://localhost:8080](http://localhost:8080)

## Estrutura do Projeto

- **Backend**: Desenvolvido em Java utilizando o Spring Boot, responsável pela lógica de negócios e integração com o banco de dados PostgreSQL.
- **Frontend**: Templates Thymeleaf renderizados pelo servidor, utilizando Tailwind CSS e Bootstrap para estilização e responsividade.

## Estilo e Design

O projeto utiliza uma combinação dos frameworks **Bootstrap** e **Tailwind CSS** para estilização das páginas, proporcionando uma interface moderna e responsiva.

## Contribuição

Se você deseja contribuir com o projeto, sinta-se à vontade para enviar pull requests ou abrir issues com sugestões e melhorias.

## Licença

Este projeto está sob a licença MIT.

---
