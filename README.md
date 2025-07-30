# Base de Dados de Monitoramento Ambiental
Projeto deenvolvido como parte da diciplina Estrutura de Dados 2, com o objetivo de similuar um 
SGBD. A aplicação teve três estágios de desenvolvimento o primeiro utiliza uma árvore AVL como estrutura principal para indexação e busca dos registros, 
além de uma lista encadeada para guardar os dados, no segundo estágio foi adicionado a uma tabela hash com o mesmo propósito da AVL e por último foi 
adicionado uma camada de mensagem que faz a comunicação entre o servido, cliente.

# Funcionalidades
- **Cadastro de Registros**: Dados capturados por microcontroladores, como informações ambientais.
- **Busca de Registros**:
  - Por ID do registro. 
  - Por ID do dispositivo que gerou os dados.
- **Remoção de Registros**. 
- **Alteração de Registros**. 
- **Listagem Geral ou por Dispositivo**. 
- **Compressão de Dados**: 
  - Compressão dos dados armazenados para otimizar o uso de memória.
  - Compressão dos logs de operações para reduzir o espaço ocupado por eles.
- **Logs de Operações e Compressão**:
  - Todas as operações de inserção, remoção e rotações da árvore são registradas em um arquivo de log.
  - Todas compressões são registradas em um arquivo de log.
- **Interface via Terminal**: Usuário pode interagir com o sistema simulando um cliente ou um microcontrolador.

# Arquitetura 
- **Servidor**: Responsável por gerenciar o banco de dados e processar as requisições.
- **Proxy do Servidor**: Atua como intermediário, registrando logs e monitorando operações.
- **Clientes**:
  - **Microcontrolador (Simulado)**: Realiza inserções e alterações nos dados. 
  - **Cliente (Usuário)**: Realiza consultas e visualizações dos registros.

## Demonstrção do funcionamento
![Diagrama do Sistema](imagens/image.png)

# Tecnologias
- Linguagem: Java
- Manipulação de arquivos para persistência de logs
- Gerenciamento de dados totalmente em memória, simulando um SGBD.

# Autor
**Lucas Silva de Souza**\
Curso: Ciência da Computação - UFERSA