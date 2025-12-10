package Service;

import java.util.List;
import Model.Livro;
import Avaliacao.Avaliacao;
import Pessoa.Usuario;

/**
 * Wrapper compat layer: delega para `Pessoa.UsuarioBO`.
 * Mantém compatibilidade com código que importava `Service.UsuarioBO`.
 */
public class UsuarioBO {
    private Pessoa.UsuarioBO delegate;

    public UsuarioBO(Usuario usuario) {
        this.delegate = new Pessoa.UsuarioBO(usuario);
    }

    public void adicionarLivro(Livro livro) {
        delegate.adicionarLivro(livro);
    }

    public List<Livro> listarLivros() {
        return delegate.listarLivros();
    }

    public Livro obterLivro(String titulo) {
        return delegate.obterLivro(titulo);
    }

    public boolean removerLivro(String titulo) {
        return delegate.removerLivro(titulo);
    }

    public void avaliarLivro(String titulo, int estrelas, String comentario) {
        delegate.avaliarLivro(titulo, estrelas, comentario);
    }

    public List<Avaliacao> obterAvaliacoesLivro(String titulo) {
        return delegate.obterAvaliacoesLivro(titulo);
    }

    public int quantidadeLivros() {
        return delegate.quantidadeLivros();
    }

    public boolean temLivro(String titulo) {
        return delegate.temLivro(titulo);
    }

    public Usuario getUsuario() {
        return delegate.getUsuario();
    }

    public void atualizarNome(String novoNome) {
        delegate.atualizarNome(novoNome);
    }

    public void atualizarEmail(String novoEmail) {
        delegate.atualizarEmail(novoEmail);
    }

    public void setSenha(String novaSenha) {
        delegate.setSenha(novaSenha);
    }

    public boolean fazerLogin(String email, String senha) {
        return delegate.fazerLogin(email, senha);
    }

    public String obterPerfil() {
        return delegate.obterPerfil();
    }
}
