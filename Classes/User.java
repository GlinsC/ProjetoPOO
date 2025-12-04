import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {
    private Usuario usuario;
    private Map<String, Livro> livros;
    private Scanner scanner;
    private boolean logado;

    public User() {
        this.livros = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.logado = false;
    }

    public static void main(String[] args) {
        User app = new User();
        app.iniciar();
    }

    private void iniciar() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Bem-vindo ao Sistema de Livros!      ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        while (true) {
            if (!logado) {
                menuPrincipal();
            } else {
                menuUsuario();
            }
        }
    }

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

            usuario = new Usuario(nome, email, senha);
            logado = true;
            System.out.println("✓ Usuário registrado com sucesso!");
            System.out.println("✓ Bem-vindo, " + nome + "!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void fazerLogin() {
        if (usuario == null) {
            System.out.println("❌ Nenhum usuário registrado ainda!");
            return;
        }

        System.out.println("\n┌─ Login ───────────────────────────────┐");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        if (usuario.getEmail().equals(email) && usuario.validarSenha(senha)) {
            logado = true;
            System.out.println("✓ Login realizado com sucesso!");
            System.out.println("✓ Bem-vindo, " + usuario.getNome() + "!");
        } else {
            System.out.println("❌ Email ou senha incorretos!");
        }
    }

    private void menuUsuario() {
        System.out.println("\n┌─ Menu do Usuário " + usuario.getNome() + " ───────┐");
        System.out.println("│ 1. Registrar novo livro               │");
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
                registrarLivro();
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

    private void registrarLivro() {
        System.out.println("\n┌─ Registrar Novo Livro ────────────────┐");
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
            String chave = titulo.toLowerCase();
            livros.put(chave, livro);
            usuario.adicionarLivro(chave);

            System.out.println("✓ Livro registrado com sucesso!");
            System.out.println("✓ " + livro);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void avaliarLivro() {
        System.out.println("\n┌─ Avaliar um Livro ────────────────────┐");

        if (livros.isEmpty()) {
            System.out.println("❌ Você ainda não tem livros registrados!");
            return;
        }

        System.out.println("Seus livros:");
        List<String> chaves = new ArrayList<>(livros.keySet());
        for (int i = 0; i < chaves.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(chaves.get(i)).getTitulo());
        }

        System.out.print("Selecione o número do livro: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (indice < 0 || indice >= chaves.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Deve ser um número!");
            return;
        }

        Livro livro = livros.get(chaves.get(indice));
        LivroBO livroBO = new LivroBO(livro);

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
            Avaliacao avaliacao = new Avaliacao(estrelas, comentario);
            livroBO.adicionarAvaliacao(avaliacao);
            System.out.println("✓ Avaliação adicionada com sucesso!");
            System.out.println(avaliacao);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void listarLivros() {
        System.out.println("\n┌─ Meus Livros ─────────────────────────┐");

        if (livros.isEmpty()) {
            System.out.println("Você ainda não tem livros registrados.");
            return;
        }

        System.out.println("Total de livros: " + livros.size() + "\n");
        int i = 1;
        for (Livro livro : livros.values()) {
            System.out.println(i + ". " + livro);
            i++;
        }
    }

    private void verDetalhesLivro() {
        System.out.println("\n┌─ Detalhes do Livro ────────────────────┐");

        if (livros.isEmpty()) {
            System.out.println("❌ Você ainda não tem livros registrados!");
            return;
        }

        System.out.println("Seus livros:");
        List<String> chaves = new ArrayList<>(livros.keySet());
        for (int i = 0; i < chaves.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(chaves.get(i)).getTitulo());
        }

        System.out.print("Selecione o número do livro: ");
        int indice;
        try {
            indice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (indice < 0 || indice >= chaves.size()) {
                System.out.println("❌ Número inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Deve ser um número!");
            return;
        }

        Livro livro = livros.get(chaves.get(indice));
        LivroBO livroBO = new LivroBO(livro);

        System.out.println("\n" + livro);
        System.out.println("\nAvaliaçõe:");

        List<Avaliacao> avaliacoes = livroBO.listarAvaliacoes();
        if (avaliacoes.isEmpty()) {
            System.out.println("Este livro ainda não tem avaliações.");
        } else {
            for (int i = 0; i < avaliacoes.size(); i++) {
                System.out.println("\n--- Avaliação " + (i + 1) + " ---");
                System.out.println(avaliacoes.get(i));
            }
        }
    }

    private void verPerfil() {
        System.out.println("\n┌─ Meu Perfil ──────────────────────────┐");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Total de livros: " + usuario.quantidadeLivrosLidos());
        System.out.println("Tipo: " + usuario.getTipo());
        System.out.println(usuario);
    }

    private void logout() {
        System.out.println("\n✓ Logout realizado com sucesso!");
        logado = false;
    }
}
