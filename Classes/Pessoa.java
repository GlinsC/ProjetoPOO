
abstract class Pessoa {
    private String nome;
    private String email;
    
    public Pessoa(String nome, String email) {
        validarEmail(email);
        this.nome = nome;
        this.email = email;
    }
    
    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
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