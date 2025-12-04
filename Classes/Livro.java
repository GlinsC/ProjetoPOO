import java.util.ArrayList;
import java.util.List;

public class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private List<Avaliacao> avaliacoes;
    
    public Livro(String titulo, String autor, int ano) {
        validarAno(ano);
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.avaliacoes = new ArrayList<>();
    }
    
    private void validarAno(int ano) {
        int anoAtual = java.time.Year.now().getValue();
        if (ano < 1000 || ano > anoAtual) {
            throw new IllegalArgumentException("Ano inválido");
        }
    }
    
    // Package-private accessor for LivroBO to manage avaliações
    List<Avaliacao> getAvaliacoesInterno() {
        return avaliacoes;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public int getAno() {
        return ano;
    }
    
    @Override
    public String toString() {
        double media = new LivroBO(this).calcularMediaEstrelas();
        return String.format("%s - %s (%d) | Média: %.1f★ | %d avaliações",
            titulo, autor, ano, media, avaliacoes.size());
    }
}


