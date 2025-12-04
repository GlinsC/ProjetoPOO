import java.util.ArrayList;
import java.util.List;

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
            throw new IllegalArgumentException("Livro não pode ser nulo");
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
    
    @Override
    public String getTipo() {
        return "Usuario";
    }
    
    @Override
    public String toString() {
        return "Usuario: " + getNome() + " | Email: " + getEmail() + 
               " | Livros lidos: " + quantidadeLivrosLidos();
    }



    public static void main(String[] args) {
        Usuario usuario = new Usuario("João Silva", "joao@email.com", "senha123");
        usuario.adicionarLivro("1984");
        usuario.adicionarLivro("O Senhor dos Anéis");
        System.out.println(usuario);
        System.out.println("Livros lidos: " + usuario.listarLivros());

    }

    
}



