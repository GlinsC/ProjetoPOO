package Model;

import java.util.ArrayList;
import java.util.List;
import Avaliacao.Avaliacao;
import Service.LivroBO;

public class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private List<Avaliacao> avaliacoes;
    
    public Livro(String titulo, String autor, int ano) {
        LivroBO.validarAno(ano);
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.avaliacoes = new ArrayList<>();
    }
    
    
    // Package-private accessor for LivroBO to manage avaliações
    public List<Avaliacao> getAvaliacoesInterno() {
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
        double media = 0;
        if (!avaliacoes.isEmpty()) {
            media = avaliacoes.stream()
                    .mapToInt(Avaliacao::getEstrelas)
                    .average()
                    .orElse(0.0);
        }
        return String.format("%s - %s (%d) | Média: %.1f★ | %d avaliações",
            titulo, autor, ano, media, avaliacoes.size());
    }
}
