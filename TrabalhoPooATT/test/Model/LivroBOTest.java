import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Model.LivroBO;
import exception.ValidationException;

public class LivroBOTest {

    @Test
    void testValidarAnoValido() {
        assertDoesNotThrow(() -> LivroBO.validarAno(2020));
    }

    @Test
    void testValidarAnoMuitoAntigo() {
        assertThrows(ValidationException.class, () -> LivroBO.validarAno(500));
    }

    @Test
    void testValidarAnoFuturo() {
        int anoFuturo = java.time.Year.now().getValue() + 1;
        assertThrows(ValidationException.class, () -> LivroBO.validarAno(anoFuturo));
    }
}
