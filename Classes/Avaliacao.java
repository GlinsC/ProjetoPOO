import java.time.LocalDateTime;

public class Avaliacao {
    private int estrelas;
    private String comentario;
    private LocalDateTime data;
    
    public Avaliacao(int estrelas, String comentario) {
        AvaliacaoBO.validarEstrelas(estrelas);
        this.estrelas = estrelas;
        this.comentario = comentario;
        this.data = LocalDateTime.now();
    }
    
    // Validation moved to AvaliacaoBO

    public int getEstrelas() {
        return estrelas;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public LocalDateTime getData() {
        return data;
    }
    
    @Override
    public String toString() {
        String estrelasStr = "★".repeat(estrelas) + "☆".repeat(5 - estrelas);
        return String.format("%s | %s\n%s", 
                estrelasStr, 
                data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                comentario != null ? comentario : "Sem comentário");
    }
}

