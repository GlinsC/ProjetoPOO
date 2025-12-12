package Pessoa;

import java.io.Serializable;
import exception.ValidationException;

// Adicionado 'implements Serializable'
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L; // Versão da classe para controle
    
    private String nome;
    private String email;
    
    public Pessoa(String nome, String email) {
        validarEmail(email);
        this.nome = nome;
        this.email = email;
    }
    
    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new ValidationException("Email inválido");
        }
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
        validarEmail(email);
        this.email = email;
    }
    
    public abstract String getTipo();
}