package Model;

import java.time.Year;
import exception.ValidationException;

public class LivroBO {
    /**
     * Valida se o ano do livro é aceitável.
     */
    public static void validarAno(int ano) {
        int anoAtual = Year.now().getValue();
        if (ano < 1000 || ano > anoAtual) {
            throw new ValidationException("Ano inválido");
        }
    }
}
