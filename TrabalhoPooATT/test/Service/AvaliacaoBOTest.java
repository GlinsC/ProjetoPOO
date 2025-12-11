import org.junit.jupiter.api.Test;
import Service.AvaliacaoBO;
import exception.ValidationException;
import Avaliacao.Avaliacao;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AvaliacaoBOTest {

    @Test
    public void testValidarEstrelasValido() {
        assertDoesNotThrow(() -> AvaliacaoBO.validarEstrelas(3));
    }

    @Test
    public void testValidarEstrelasInvalidoBaixo() {
        assertThrows(ValidationException.class, () -> AvaliacaoBO.validarEstrelas(0));
    }

    @Test
    public void testValidarEstrelasInvalidoAlto() {
        assertThrows(ValidationException.class, () -> AvaliacaoBO.validarEstrelas(6));
    }

    @Test
    public void testCalcularMediaVazio() {
        List<Avaliacao> lista = new ArrayList<>();
        assertEquals(0.0, AvaliacaoBO.calcularMedia(lista));
    }

    @Test
    public void testCalcularMedia() {
        List<Avaliacao> lista = new ArrayList<>();
        lista.add(new Avaliacao(5, "Ótimo"));
        lista.add(new Avaliacao(3, "Bom"));
        assertEquals(4.0, AvaliacaoBO.calcularMedia(lista));
    }

    @Test
    public void testFormatarAvaliacao() {
        Avaliacao a = new Avaliacao(5, "Excelente");
        assertNotNull(AvaliacaoBO.formatarAvaliacao(a));
    }
}
