public class AvaliacaoBO {
    // Validation logic extracted from Avaliacao entity
    public static void validarEstrelas(int estrelas) {
        if (estrelas < 1 || estrelas > 5) {
            throw new IllegalArgumentException("Estrelas devem estar entre 1 e 5");
        }
    }
}
