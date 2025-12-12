package Pessoa;

public class Usuario {

    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // usado nos testes
    public boolean validarSenha(String tentativa) {
        return this.senha.equals(tentativa);
    }

    // apenas UsuarioBO deve mudar a senha
    void setSenhaInterna(String novaSenha) {
        this.senha = novaSenha;
    }
}
