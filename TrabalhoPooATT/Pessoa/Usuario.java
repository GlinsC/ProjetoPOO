package Pessoa;

import java.util.ArrayList;
import java.util.List;
import exception.ValidationException;

public class Usuario extends Pessoa {
    private String senha;
    private List<String> livros;
    
    public Usuario(String nome, String email, String senha) {
        super(nome, email);
        this.senha = senha;
        this.livros = new ArrayList<>();
    }
    
    public void adicionarLivro(String livro) {
        if (livro == null) {
            throw new ValidationException("Livro não pode ser nulo");
        }
        livros.add(livro);
    }
    
    public List<String> listarLivros() {
        return new ArrayList<>(livros);
    }
    
    public int quantidadeLivrosLidos() {
        return livros.size();
    }
    
    public boolean validarSenha(String senha) {
        return this.senha.equals(senha);
    }

    // helpers usados pelo Business Object (package-private)
    void definirSenhaInterna(String novaSenha) {
        this.senha = novaSenha;
    }

    void adicionarLivroInterno(String livro) {
        livros.add(livro);
    }

    public boolean removerLivro(String titulo) {
        return livros.remove(titulo);
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Override
    public String getTipo() {
        return "Usuario";
    }
    
    @Override
    public String toString() {
        return "Usuario: " + getNome() + " | Email: " + getEmail() + 
               " | Livros lidos: " + quantidadeLivrosLidos();
    }
}
