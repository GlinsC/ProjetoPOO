package Service;

import exception.ValidationException;

public class AvaliacaoBO {
    /**
     * Valida se a quantidade de estrelas está entre 1 e 5
     * @param estrelas quantidade de estrelas
     * @throws ValidationException se as estrelas forem inválidas
     */
    public static void validarEstrelas(int estrelas) {
        if (estrelas < 1 || estrelas > 5) {
            throw new ValidationException("Estrelas devem estar entre 1 e 5");
        }
    }

    /**
     * Calcula a média de estrelas para uma lista de avaliações
     * @param avaliacoes lista de avaliações
     * @return média de estrelas ou 0 se vazia
     */
    public static double calcularMedia(java.util.List<Avaliacao.Avaliacao> avaliacoes) {
        if (avaliacoes == null || avaliacoes.isEmpty()) {
            return 0.0;
        }
        return avaliacoes.stream()
                .mapToInt(Avaliacao.Avaliacao::getEstrelas)
                .average()
                .orElse(0.0);
    }

    /**
     * Formata uma avaliação para exibição
     * @param avaliacao avaliação a formatar
     * @return string formatada da avaliação
     */
    public static String formatarAvaliacao(Avaliacao.Avaliacao avaliacao) {
        return avaliacao.toString();
    }
}
