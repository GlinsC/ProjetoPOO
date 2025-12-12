import java.util.List;
import java.util.Scanner;
import java.util.Optional;

import Pessoa.Usuario;
import Model.Livro;
import Avaliacao.Avaliacao;
import Service.UsuarioBO;
import Dados.UsuarioRepository; // Importante: Acesso ao Banco de Dados
import exception.ValidationException;
import exception.NotFoundException;
import exception.PersistenceException;

/**
 * Aplicação principal do Sistema de Livros
 * Agora integrada com Repositórios Globais
 */
public class User {
    private UsuarioBO usuarioBO; // Só é inicializado após login
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

    private void iniciar() {
        exibirBemVindo();

        while (true) {
            try {
                if (!logado) {
                    menuPrincipal();
                } else {
                    menuUsuario();
                }
            } catch (Exception e) {
                System.out.println("❌ Erro inesperado: " + e.getMessage());
                scanner.nextLine(); // Limpa buffer
            }
        }
    }

    private void exibirBemVindo() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   Bem-vindo ao LetterBook (Sistema de Livros) ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");
    }

    // --- MENUS ---

    private void menuPrincipal() {
        System.out.println("\n┌─ Menu Principal ──────────────────────┐");
        System.out.println("│ 1. Registrar novo usuário             │");
        System.out.println("│ 2. Fazer login                        │");
        System.out.println("│ 3. Sair                               │");
        System.out.println("└───────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");

        String opcao = scanner.nextLine().trim();

        switch (opcao) {
            case "1": registrarUsuario(); break;
            case "2": fazerLogin(); break;
            case "3": 
                System.out.println("Saindo..."); 
                System.exit(0); 
                break;
            default: System.out.println("❌ Opção inválida!");
        }
    }

    private void menuUsuario() {
        System.out.println("\n┌─ Usuário: " + usuarioBO.getUsuario().getNome() + " ───────────────────┐");
        System.out.println("│ 1. Adicionar novo livro               │");
        System.out.println("│ 2. Avaliar um livro                   │");
        System.out.println("│ 3. Listar meus livros                 │");
        System.out.println("│ 4. Avaliações globais                 │");
        System.out.println("│ 5. Perfil                             │");
        System.out.println("│ 6. Logout                             │");
        System.out.println("└───────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");

        String opcao = scanner.nextLine().trim();

        switch (opcao) {
            case "1": adicionarLivro(); break;
            case "2": avaliarLivro(); break;
            case "3": listarLivros(); break;
            case "4": avaliacoesGlobais(); break;
            case "5": verPerfil(); break;
            case "6": logout(); break;
            default: System.out.println("❌ Opção inválida!");
        }
    }

    // --- AÇÕES PRINCIPAIS ---

    private void registrarUsuario() {
        System.out.println("\n>>> Registro");
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();
            
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Senha: ");
            String senha = scanner.nextLine().trim();

            Usuario usuario = new Usuario(nome, email, senha);
            
            // Salva no Repositório Global
            UsuarioRepository.salvar(usuario);
            
            System.out.println("✓ Usuário registrado! Faça login para continuar.");
            
        } catch (ValidationException | PersistenceException e) {
            System.out.println("❌ Erro de validação: " + e.getMessage());
        }
    }

    private void fazerLogin() {
        System.out.println("\n>>> Login");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String senha = scanner.nextLine().trim();

        // Busca no Repositório Global
        Optional<Usuario> userOpt = UsuarioRepository.buscarPorEmail(email);

        if (userOpt.isPresent()) {
            Usuario usuarioEncontrado = userOpt.get();
            if (usuarioEncontrado.validarSenha(senha)) {
                // Inicializa o Service com o usuário encontrado
                this.usuarioBO = new UsuarioBO(usuarioEncontrado);
                this.logado = true;
                System.out.println("✓ Login realizado com sucesso!");
            } else {
                System.out.println("❌ Senha incorreta!");
            }
        } else {
            System.out.println("❌ Usuário não encontrado!");
        }
    }

    // --- AÇÕES DO USUÁRIO ---

    private void adicionarLivro() {
        System.out.println("\n>>> Adicionar Livro à Estante");
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine().trim();
            
            System.out.print("Autor: ");
            String autor = scanner.nextLine().trim();

            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine().trim());

            Livro livro = new Livro(titulo, autor, ano);
            usuarioBO.adicionarLivro(livro);
            System.out.println("✓ Livro registrado na sua estante!");
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Ano inválido.");
        } catch (ValidationException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void listarLivros() {
        System.out.println("\n>>> Minha Estante");
        List<Livro> livros = usuarioBO.listarLivros();
        
        if (livros.isEmpty()) {
            System.out.println("Sua estante está vazia.");
        } else {
            for (Livro l : livros) {
                System.out.println("📖 " + l.toString());
            }
        }
    }

    private void avaliarLivro() {
        System.out.println("\n>>> Avaliar Livro");
        // Listar primeiro para facilitar
        List<Livro> livros = usuarioBO.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Adicione livros antes de avaliar.");
            return;
        }

        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i+1) + ". " + livros.get(i).getTitulo());
        }
        
        System.out.print("Número do livro: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= livros.size()) return;
            
            Livro alvo = livros.get(idx);
            
            System.out.print("Estrelas (1-5): ");
            int estrelas = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Comentário: ");
            String comentario = scanner.nextLine();
            
            usuarioBO.avaliarLivro(alvo.getTitulo(), estrelas, comentario);
            System.out.println("✓ Avaliação registrada no sistema global!");
            
        } catch (Exception e) {
            System.out.println("❌ Erro ao avaliar: " + e.getMessage());
        }
    }
    
    private void avaliacoesGlobais() {
        System.out.println("\n>>> Avaliações (Visão Global)");
        System.out.print("Digite o título exato do livro: ");
        String titulo = scanner.nextLine().trim();
        
        try {
            Livro livro = usuarioBO.obterLivro(titulo);
            System.out.println(livro.toString());
            System.out.println("--- Avaliações da Comunidade ---");
            
            List<Avaliacao> avaliacoes = usuarioBO.obterAvaliacoesLivro(titulo);
            if (avaliacoes.isEmpty()) {
                System.out.println("Nenhuma avaliação ainda.");
            } else {
                avaliacoes.forEach(a -> System.out.println(a));
            }
            
        } catch (NotFoundException e) {
            System.out.println("❌ Livro não encontrado na biblioteca global.");
        }
    }

    private void verPerfil() {
        System.out.println("\n>>> Perfil");
        System.out.println(usuarioBO.obterPerfil());
    }

    private void logout() {
        this.usuarioBO = null;
        this.logado = false;
        System.out.println("✓ Logout realizado.");
    }
}