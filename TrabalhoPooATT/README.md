

Estrutura do Projeto

```
Nova pasta/
├── User.java                 # Classe principal - Interface com usuário
├── 
├── Pessoa/
│   ├── Pessoa.java          # Classe abstrata base
│   └── Usuario.java         # Modelo de usuário
│
├── Model/
│   └── Livro.java           # Modelo de livro
│
├── Avaliacao/
│   └── Avaliacao.java       # Modelo de avaliação
│
├── Interface/
│   └── IAvaliavel.java      # Interface para avaliações
│
├── Service/
│   ├── AvaliacaoBO.java     # Lógica de avaliações
│   ├── LivroBO.java         # Lógica de livros
│   └── UsuarioBO.java       # Lógica de usuários
│
├── Classes/                 # Pasta anterior (pode ser removida)
└── ClassesBO/               # Pasta anterior (pode ser removida)
```

Descrição dos Componentes

*Camada Model (Modelos de Dados)*
- **Pessoa.java**: Classe abstrata que serve como base para usuários
- **Usuario.java**: Estende Pessoa, representa um usuário do sistema
- **Livro.java**: Representa um livro com título, autor, ano e avaliações
- **Avaliacao.java**: Representa uma avaliação (estrelas e comentário)

### **Camada Service (Lógica de Negócios)**
- **UsuarioBO.java**: Gerencia operações do usuário
  - Adicionar/remover livros
  - Avaliar livros
  - Listar livros
  - Atualizar perfil
  - Fazer login

- **LivroBO.java**: Gerencia operações de livros
  - Adicionar avaliações
  - Listar avaliações
  - Calcular média de estrelas

- **AvaliacaoBO.java**: Utilitários para avaliações
  - Validar estrelas (1-5)
  - Calcular média

### **Camada Interface**
- **IAvaliavel.java**: Interface que define o contrato para objetos avaliáveis

### **Classe Principal**
- **User.java**: Aplicação principal que gerencia:
  - Menu de login/registro
  - Menu de usuário autenticado
  - Interação com o usuário

## 🔄 Fluxo de Operações

```
User.java (Interface)
    ↓
UsuarioBO (Lógica)
    ├→ LivroBO (Lógica de Livros)
    │   └→ IAvaliavel (Interface)
    └→ AvaliacaoBO (Lógica de Avaliações)
    
Modelos:
    Usuario → Pessoa
    Livro
    Avaliacao
```

## 🚀 Como Usar

### Compilar
```bash
cd "c:\Users\Gabriel\Desktop\Nova pasta"
javac -d . User.java Pessoa/Pessoa.java Pessoa/Usuario.java Model/Livro.java Avaliacao/Avaliacao.java Interface/IAvaliavel.java Service/AvaliacaoBO.java Service/LivroBO.java Service/UsuarioBO.java
```

### Executar
```bash
java User
```

## ✨ Funcionalidades

1. **Autenticação**
   - Registrar novo usuário
   - Fazer login

2. **Gerenciamento de Livros**
   - Adicionar novo livro
   - Listar livros
   - Ver detalhes do livro
   - Remover livro

3. **Avaliações**
   - Avaliar livro (1-5 estrelas)
   - Adicionar comentário
   - Visualizar média de avaliações
   - Ver todas as avaliações

4. **Perfil**
   - Ver dados do perfil
   - Atualizar nome/email/senha
   - Ver total de livros

## 🔐 Segurança

- Validação de email (deve conter @)
- Validação de senha (não vazia)
- Validação de avaliações (1-5 estrelas)
- Validação de ano (entre 1000 e ano atual)

## 📝 Notas de Implementação

- A estrutura segue o padrão **BO (Business Objects)** para separação de responsabilidades
- Cada classe tem responsabilidade única
- Modelos de dados separados da lógica de negócios
- Interface IAvaliavel permite extensibilidade (outros objetos podem ser avaliáveis)

## 🎯 Melhorias Futuras

- Persistência de dados (banco de dados)
- Sistema de permissões
- Avaliações por usuário (rastrear quem avaliou)
- Recomendações de livros
- Sistema de categorias de livros
