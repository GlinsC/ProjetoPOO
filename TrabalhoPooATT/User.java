import java.util.List;
import java.util.Scanner;
import Pessoa.Usuario;
import Model.Livro;
import Avaliacao.Avaliacao;
import Pessoa.UsuarioBO;
import exception.ValidationException;
import exception.NotFoundException;

/**
 * Aplicação principal do Sistema de Livros
 * Gerencia o menu e interação com o usuário
 */
public class User {
    private UsuarioBO usuarioBO;
    private Scanner scanner;
    private boolean logado;

    public User() {
        this.scanner = new Scanner(System.in);
        this.logado = false;
    }

    public static void main(String[] args) {
        User app = new User();
        app.iniciar();
    }

    /**
     * Inicia a aplicação
     */
    private void iniciar() {
        exibirBemVindo();

        while (true) {
            if (!logado) {
                menuPrincipal();
            } else {
                menuUsuario();
            }
        }
    }

    /**
     * Exibe a mensagem de boas-vindas
     */
    private void exibirBemVindo() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Bem-vindo ao Sistema de Livros!      ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    /**
     * Menu principal - antes de fazer login
     */
    private void menuPrincipal() {
        System.out.println("\n┌─ Menu Principal ─────────────────────┐");
        System.out.println("│ 1. Registrar novo usuário             │");
        System.out.println("│ 2. Fazer login                        │");
        System.out.println("│ 3. Sair                               │");
        System.out.println("└──────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");

        String opcao = scanner.nextLine().trim();

        switch (opcao) {
            case "1":
                registrarUsuario();
                break;
            case "2":
                fazerLogin();
                break;
            case "3":
                System.out.println("\nAté logo!");
                System.exit(0);
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    /**
     * Registra um novo usuário
     */
    private void registrarUsuario() {
        System.out.println("\n┌─ Registro de Novo Usuário ───────────┐");
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("❌ Nome não pode ser vazio!");
                return;
            }

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Senha: ");
            String senha = scanner.nextLine().trim();
            if (senha.isEmpty()) {
                System.out.println("❌ Senha não pode ser vazia!");
                return;
            }

            Usuario usuario = new Usuario(nome, email, senha);
            usuarioBO = new UsuarioBO(usuario);
            logado = true;
            System.out.println("✓ Usuário registrado com sucesso!");
            System.out.println("✓ Bem-vindo, " + nome + "!");
        } catch (ValidationException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    /**
     * Faz login do usuário
     */
    private void fazerLogin() {
        if (usuarioBO == null) {
            System.out.println("❌ Nenhum usuário registrado ainda!");
            return;
        }

        System.out.println("\n┌─ Login ───────────────────────────────┐");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        if (usuarioBO.fazerLogin(email, senha)) {
            logado = true;
            System.out.println("✓ Login realizado com sucesso!");
            System.out.println("✓ Bem-vindo, " + usuarioBO.getUsuario().getNome() + "!");
        } else {
            System.out.println("❌ Email ou senha incorretos!");
        }
    }

    /**
     * Menu do usuário - após fazer login
     */
    private void menuUsuario() {
        System.out.println("\n┌─ Menu do Usuário: " + usuarioBO.getUsuario().getNome() + " ───┐");
        System.out.println("│ 1. Adicionar novo livro               │");
        System.out.println("│ 2. Avaliar um livro                   │");
        System.out.println("│ 3. Listar meus livros                 │");
        System.out.println("│ 4. Ver detalhes de um livro           │");
        System.out.println("│ 5. Ver meu perfil                     │");
        System.out.println("│ 6. Logout                             │");
        System.out.println("└──────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");

        String opcao = scanner.nextLine().trim();

        switch (opcao) {
            case "1":
                adicionarLivro();
                break;
            case "2":
                avaliarLivro();
                break;
            case "3":
                listarLivros();
                break;
            case "4":
                verDetalhesLivro();
                break;
            case "5":
                verPerfil();
                break;
            case "6":
                logout();
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
    }

    /**
     * Adiciona um novo livro à biblioteca do usuário
     */
    private void adicionarLivro() {
        System.out.println("\n┌─ Adicionar Novo Livro ────────────────┐");
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim();
            if (titulo.isEmpty()) {
                System.out.println("❌ Título não pode ser vazio!");
                return;
            }

            System.out.print("Autor: ");
            String autor = scanner.nextLine().trim();
            if (autor.isEmpty()) {
                System.out.println("❌ Autor não pode ser vazio!");
                return;
            }

            System.out.print("Ano de publicação: ");
            int ano;
            try {
                ano = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Ano deve ser um número inteiro!");
                return;
            }

            Livro livro = new Livro(titulo, autor, ano);
            usuarioBO.adicionarLivro(livro);

            System.out.println("✓ Livro adicionado com sucesso!");
            System.out.println("✓ " + livro);
        } catch (ValidationException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    /**
     * Avalia um livro
     */
    private void avaliarLivro() {
        System.out.println("\n┌─ Avaliar um Livro ────────────────────┐");

        if (usuarioBO.quantidadeLivros() == 0) {
            System.out.println("❌ Você ainda não tem livros adicionados!");
            return;
        }

        System.out.println("Seus livros:");
        List<Livro> livros = usuarioBO.listarLivros();
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i).getTitulo());
        }

        System.out.print("Selecione o número do livro: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (indice < 0 || indice >= livros.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Deve ser um número!");
            return;
        }

        Livro livro = livros.get(indice);

        System.out.print("Quantidade de estrelas (1-5): ");
        int estrelas;
        try {
            estrelas = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Deve ser um número!");
            return;
        }

        System.out.print("Comentário (ou pressione Enter para pular): ");
        String comentario = scanner.nextLine().trim();
        if (comentario.isEmpty()) {
            comentario = null;
        }

        try {
            usuarioBO.avaliarLivro(livro.getTitulo(), estrelas, comentario);
            System.out.println("✓ Avaliação adicionada com sucesso!");
            Avaliacao avaliacao = new Avaliacao(estrelas, comentario);
            System.out.println(avaliacao);
        } catch (ValidationException | NotFoundException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    /**
     * Lista todos os livros do usuário
     */
    private void listarLivros() {
        System.out.println("\n┌─ Meus Livros ─────────────────────────┐");

        if (usuarioBO.quantidadeLivros() == 0) {
            System.out.println("Você ainda não tem livros adicionados.");
            return;
        }

        System.out.println("Total de livros: " + usuarioBO.quantidadeLivros() + "\n");
        List<Livro> livros = usuarioBO.listarLivros();
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i));
        }
    }

    /**
     * Mostra detalhes de um livro específico
     */
    private void verDetalhesLivro() {
        System.out.println("\n┌─ Detalhes do Livro ────────────────────┐");

        if (usuarioBO.quantidadeLivros() == 0) {
            System.out.println("❌ Você ainda não tem livros adicionados!");
            return;
        }

        System.out.println("Seus livros:");
        List<Livro> livros = usuarioBO.listarLivros();
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i).getTitulo());
        }

        System.out.print("Selecione o número do livro: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (indice < 0 || indice >= livros.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Deve ser um número!");
            return;
        }

        Livro livro = livros.get(indice);

        System.out.println("\n" + livro);
        System.out.println("\nAvaliações:");

        List<Avaliacao> avaliacoes = usuarioBO.obterAvaliacoesLivro(livro.getTitulo());
        if (avaliacoes.isEmpty()) {
            System.out.println("Este livro ainda não tem avaliações.");
        } else {
            for (int i = 0; i < avaliacoes.size(); i++) {
                System.out.println("\n--- Avaliação " + (i + 1) + " ---");
                System.out.println(avaliacoes.get(i));
            }
        }
    }

    /**
     * Exibe o perfil do usuário
     */
    private void verPerfil() {
        System.out.println("\n" + usuarioBO.obterPerfil());
    }

    /**
     * Realiza logout do usuário
     */
    private void logout() {
        System.out.println("\n✓ Logout realizado com sucesso!");
        logado = false;
        usuarioBO = null;
    }
}
