# Sistema de FAQ Interativo com LLM

## Descrição

Este projeto é um sistema de FAQ interativo desenvolvido com o objetivo de melhorar a eficiência e a integração de novos colaboradores dentro de empresas, utilizando uma Inteligência Artificial (IA) de código aberto chamada Ollama. A aplicação permite que os usuários façam perguntas e recebam respostas dinâmicas e contextualizadas, utilizando uma API em Python com FastAPI e GroqAPI para a comunicação com a LLM. Além disso, o sistema conta com funcionalidades de gerenciamento de usuários para administradores, proporcionando controle sobre o acesso e permissões de usuários.

## Tecnologias Utilizadas

- **Frontend**: 
  - **Java** com **JFrame** para a interface gráfica.
  - Integração com a API utilizando bibliotecas Java como `Gson` para manipulação de JSON.
  
- **Backend**: 
  - **Python** com **FastAPI** para o desenvolvimento da API.
  - **SQLite** como banco de dados local para armazenar perguntas e respostas.
  - **GroqAPI** para integração com a LLM (Ollama).

## Funcionalidades Implementadas

1. **Login e Autenticação**:
   - Os usuários podem se registrar e fazer login no sistema.

2. **Cadastro de Perguntas**:
   - O sistema permite o cadastro de perguntas no banco de dados, que podem ser consultadas posteriormente.

3. **Consulta de Perguntas/Respostas**:
   - O usuário pode consultar perguntas já cadastradas e visualizar as respostas.

4. **Integração com LLM**:
   - A LLM responde perguntas feitas pelos usuários, utilizando a API GroqAPI.

5. **Chat Interativo**:
   - Usuários podem interagir diretamente com a LLM, recebendo respostas em tempo real.

6. **Gerenciamento de Usuários (para Administradores)**:
   - Administradores podem adicionar, editar ou remover usuários e gerenciar permissões de acesso.

## Como Funciona

O sistema permite que os usuários interajam com uma LLM para encontrar respostas para perguntas frequentes. A cada interação, as respostas podem ser dinâmicas e baseadas no contexto das perguntas feitas, utilizando IA para fornecer respostas mais precisas. Além disso, o sistema oferece uma interface de gerenciamento para administradores, que podem controlar o acesso dos usuários e suas permissões.

## Possíveis Melhorias Futuras

- **Automatização do Banco de Dados**: Criar um sistema que atualize automaticamente com novas perguntas e respostas.
- **Aprimorar Respostas da LLM**: Tornar as respostas mais precisas e inteligentes.
- **Melhorias na Interface**: Melhorar a experiência do usuário com mais interatividade e design.
- **Segurança**: Implementar criptografia e outras melhorias de segurança no armazenamento de dados.

## Licença

Este projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
