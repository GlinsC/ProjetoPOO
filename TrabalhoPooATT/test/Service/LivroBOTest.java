package Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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

    @Test
    void testeValidarTituloValido(){
        assertDoesNotThrow(() -> LivroBO.validarTitulo("harry potter"));
    }
    @Test
    void testValidarTituloVazio(){
        assertThrows(ValidationException.class , () -> LivroBO.validarTitulo(""));
    }
    @Test
    void validarTituloCurto(){
         assertThrows(ValidationException.class , () -> LivroBO.validarTitulo("S"));
    }
    @Test
    void testValidarAutorValido(){
        assertDoesNotThrow(() -> LivroBO.validarAutor("Machado de Assis"));
    }


    @Test
    void testValidarAutorVazio(){
        assertThrows(ValidationException.class, () -> LivroBO.validarAutor(""));
    }

    @Test
    void testValidarAutorCurto(){
        assertThrows(ValidationException.class, () -> LivroBO.validarAutor("N.R"));
    }
    @Test
    void testValidarEditoraValida(){
        assertDoesNotThrow(() -> LivroBO.validarEditora("Saraiva"));
    }
    @Test
    void testValidarEditoraVazia(){
         assertThrows(ValidationException.class, () -> LivroBO.validarEditora(""));
    }
    @Test 
    void testValidarCamposValidos(){
         assertDoesNotThrow(() -> LivroBO.validarCampos("os sertoes","euclides","principis"));
    }
    @Test 
    void testValidarCamposComErro(){
        assertThrows(ValidationException.class, () -> LivroBO.validarCampos("",//titulo invalido
                                                                           "autor",
                                                                          "editora"));
    }
    
}
