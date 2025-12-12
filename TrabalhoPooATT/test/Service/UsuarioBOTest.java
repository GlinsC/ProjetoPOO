package Pessoa;

import Model.Livro;
import exception.NotFoundException;
import exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioBOTest {

    private Usuario usuario;
    private UsuarioBO usuarioBO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João", "joao@email.com", "1234");
        usuarioBO = new UsuarioBO(usuario);
    }

    @Test
    void testarAdicionarEListarLivros() {
        Livro livro = new Livro("Java Básico", "Autor X", 2020);
        usuarioBO.adicionarLivro(livro);

        List<Livro> livros = usuarioBO.listarLivros();
        assertEquals(1, livros.size());
        assertEquals("Java Básico", livros.get(0).getTitulo());
    }

    @Test
    void testarObterLivroERemoverLivro() {
        Livro livro = new Livro("Java Avançado", "Autor Y", 2019);
        usuarioBO.adicionarLivro(livro);

        Livro obtido = usuarioBO.obterLivro("Java Avançado");
        assertNotNull(obtido);
        assertEquals("Java Avançado", obtido.getTitulo());

        boolean removido = usuarioBO.removerLivro("Java Avançado");
        assertTrue(removido);
        assertNull(usuarioBO.obterLivro("Java Avançado"));
    }

    @Test
    void testarAvaliarLivro() {
        Livro livro = new Livro("Python", "Autor Z", 2021);
        usuarioBO.adicionarLivro(livro);

        usuarioBO.avaliarLivro("Python", 5, "Excelente!");
        assertEquals(1, usuarioBO.obterAvaliacoesLivro("Python").size());
        assertEquals(5, usuarioBO.obterAvaliacoesLivro("Python").get(0).getEstrelas());
    }

    @Test
    void testarFazerLogin() {
        assertTrue(usuarioBO.fazerLogin("joao@email.com", "1234"));
        assertFalse(usuarioBO.fazerLogin("joao@email.com", "errada"));
    }

    @Test
    void testarAtualizarNomeEmailSenha() {
        usuarioBO.atualizarNome("Maria");
        assertEquals("Maria", usuario.getNome());

        usuarioBO.atualizarEmail("maria@email.com");
        assertEquals("maria@email.com", usuario.getEmail());

        usuarioBO.setSenha("novaSenha");
        assertTrue(usuario.validarSenha("novaSenha"));
    }

    @Test
    void testarObterPerfil() {
        String perfil = usuarioBO.obterPerfil();
        assertTrue(perfil.contains("João"));
        assertTrue(perfil.contains("joao@email.com"));
    }

    @Test
    void testarErrosAoAdicionarLivroNulo() {
        ValidationException ex = assertThrows(ValidationException.class, () -> usuarioBO.adicionarLivro(null));
        assertEquals("Livro não pode ser nulo", ex.getMessage());
    }

    @Test
    void testarAvaliarLivroNaoExistente() {
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> usuarioBO.avaliarLivro("Livro Inexistente", 3, "Comentário"));
        assertTrue(ex.getMessage().contains("Livro não encontrado"));
    }
}
