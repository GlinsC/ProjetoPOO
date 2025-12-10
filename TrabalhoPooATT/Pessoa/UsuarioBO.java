package Pessoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Service.LivroBO;
import Model.Livro;
import Avaliacao.Avaliacao;
import exception.ValidationException;
import exception.NotFoundException;

public class UsuarioBO {
    private Usuario usuario;
    private Map<String, Livro> livros;

    public UsuarioBO(Usuario usuario) {
        if (usuario == null) {
            throw new ValidationException("Usuário não pode ser nulo");
        }
        this.usuario = usuario;
        this.livros = new HashMap<>();
    }

    /**
     * Adiciona um novo livro à biblioteca do usuário
     */
    public void adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new ValidationException("Livro não pode ser nulo");
        }
        String chave = livro.getTitulo().toLowerCase();
        livros.put(chave, livro);
        usuario.adicionarLivroInterno(chave);
    }

    /**
     * Lista todos os livros do usuário
     */
    public List<Livro> listarLivros() {
        return new ArrayList<>(livros.values());
    }

    /**
     * Obtém um livro pelo título
     */
    public Livro obterLivro(String titulo) {
        return livros.get(titulo.toLowerCase());
    }

    /**
     * Remove um livro da biblioteca
     */
    public boolean removerLivro(String titulo) {
        String chave = titulo.toLowerCase();
        if (livros.containsKey(chave)) {
            livros.remove(chave);
            return true;
        }
        return false;
    }

    /**
     * Avalia um livro
     */
    public void avaliarLivro(String titulo, int estrelas, String comentario) {
        Livro livro = obterLivro(titulo);
        if (livro == null) {
            throw new NotFoundException("Livro não encontrado: " + titulo);
        }

        Avaliacao avaliacao = new Avaliacao(estrelas, comentario);
        LivroBO livroBO = new LivroBO(livro);
        livroBO.adicionarAvaliacao(avaliacao);
    }

    /**
     * Obtém detalhes de um livro
     */
    public String obterDetalhesLivro(String titulo) {
        Livro livro = obterLivro(titulo);
        if (livro == null) {
            return null;
        }
        return livro.toString();
    }

    /**
     * Obtém as avaliações de um livro
     */
    public List<Avaliacao> obterAvaliacoesLivro(String titulo) {
        Livro livro = obterLivro(titulo);
        if (livro == null) {
            return new ArrayList<>();
        }
        LivroBO livroBO = new LivroBO(livro);
        return livroBO.listarAvaliacoes();
    }

    /**
     * Retorna a quantidade de livros do usuário
     */
    public int quantidadeLivros() {
        return livros.size();
    }

    /**
     * Verifica se o usuário tem um livro específico
     */
    public boolean temLivro(String titulo) {
        return livros.containsKey(titulo.toLowerCase());
    }

    /**
     * Obtém o usuário
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Atualiza o nome do usuário
     */
    public void atualizarNome(String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new ValidationException("Nome não pode ser vazio");
        }
        usuario.setNome(novoNome);
    }

    /**
     * Atualiza o email do usuário
     */
    public void atualizarEmail(String novoEmail) {
        usuario.setEmail(novoEmail);
    }

    /**
     * Atualiza (valida) a senha do usuário — lógica do Business Object
     */
    public void setSenha(String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new ValidationException("Senha não pode ser vazia");
        }
        usuario.definirSenhaInterna(novaSenha);
    }

    /**
     * Realiza login validando email e senha
     */
    public boolean fazerLogin(String email, String senha) {
        return usuario.getEmail().equals(email) && usuario.validarSenha(senha);
    }

    /**
     * Obtém o perfil do usuário formatado
     */
    public String obterPerfil() {
        return "╔════════════════════════════════════════╗\n" +
               "║          PERFIL DO USUÁRIO             ║\n" +
               "╠════════════════════════════════════════╣\n" +
               "║ Nome: " + String.format("%-34s", usuario.getNome()) + " ║\n" +
               "║ Email: " + String.format("%-33s", usuario.getEmail()) + " ║\n" +
               "║ Total de Livros: " + String.format("%-22d", quantidadeLivros()) + " ║\n" +
               "║ Tipo: " + String.format("%-34s", usuario.getTipo()) + " ║\n" +
               "╚════════════════════════════════════════╝";
    }
}
