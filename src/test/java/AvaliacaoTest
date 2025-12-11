import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Avaliacao.Avaliacao;
import Service.AvaliacaoBO;

public class AvaliacaoTest {

    @Test
    void testCriacaoAvaliacaoValida() {
        Avaliacao a = new Avaliacao(4, "muito bom");
        assertEquals(4, a.getEstrelas());
        assertEquals("muito bom", a.getComentario());
        assertNotNull(a.getData());
    }

    @Test
    void testCriacaoAvaliacaoInvalida() {
        assertThrows(RuntimeException.class, () -> new Avaliacao(0, "ruim"));
        assertThrows(RuntimeException.class, () -> new Avaliacao(6, "ruim"));
    }

    @Test
    void testToStringContemEstrelas() {
        Avaliacao a = new Avaliacao(5, "excelente");
        String texto = a.toString();
        assertTrue(texto.contains("★★★★★"));
    }
}
