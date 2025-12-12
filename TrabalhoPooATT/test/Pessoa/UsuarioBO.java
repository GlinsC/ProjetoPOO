package Pessoa;

import Model.Livro;
import Model.Avaliacao;
import exception.NotFoundException;
import exception.ValidationException;

import java.util.*;

public class UsuarioBO {

    private Usuario usuario;
    private final List<Livro> livros = new ArrayList<>();
    private final Map<String, List<Avaliacao>> avaliacoes = new HashMap<>();

    public UsuarioBO(Usuario usuario) {
        this.usuario = usuario;
    }

    // ---------- LIVROS ----------
    public void adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new ValidationException("Livro não pode ser nulo");
        }
        livros.add(livro);
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

    public Livro obterLivro(String titulo) {
        if (titulo == null) return null;

        for (Livro l : livros) {
            if (titulo.equals(l.getTitulo())) {
                return l;
            }
        }
        return null;
    }

    public boolean removerLivro(String titulo) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getTitulo().equals(titulo)) {
                livros.remove(i);
                avaliacoes.remove(titulo);
                return true;
            }
        }
        return false;
    }

    // ---------- AVALIAÇÃO ----------
    public void avaliarLivro(String titulo, int estrelas, String comentario) {
        Livro livro = obterLivro(titulo);

        if (livro == null) {
            throw new NotFoundException("Livro não encontrado: " + titulo);
        }

        Avaliacao avaliacao = new Avaliacao(estrelas, comentario);
        avaliacoes.computeIfAbsent(titulo, k -> new ArrayList<>()).add(avaliacao);
    }

    public List<Avaliacao> obterAvaliacoesLivro(String titulo) {
        return new ArrayList<>(avaliacoes.getOrDefault(titulo, new ArrayList<>()));
    }

    // ---------- LOGIN ----------
    public boolean fazerLogin(String email, String senha) {
        return usuario.getEmail().equals(email) &&
               usuario.validarSenha(senha);
    }

    // ---------- ATUALIZAÇÕES ----------
    public void atualizarNome(String nome) {
        usuario.setNome(nome);
    }

    public void atualizarEmail(String email) {
        usuario.setEmail(email);
    }

    public void setSenha(String novaSenha) {
        usuario.setSenhaInterna(novaSenha);
    }

    // ---------- PERFIL ----------
    public String obterPerfil() {
        return "Nome: " + usuario.getNome() + "\nEmail: " + usuario.getEmail();
    }
}
