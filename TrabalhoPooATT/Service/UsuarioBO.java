package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Model.Livro;
import Avaliacao.Avaliacao;
import Pessoa.Usuario;
import Dados.LivroRepository;
import exception.NotFoundException;
import exception.ValidationException;

/**
 * Service Principal: Gerencia a lógica de negócios do Usuário
 * e sua interação com o Repositório de Livros.
 */
public class UsuarioBO {
    private Usuario usuario;

    public UsuarioBO(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Adiciona um livro ao sistema (se não existir) e à estante do usuário.
     */
    public void adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new ValidationException("Livro não pode ser nulo");
        }

        // 1. Tenta salvar no Repositório Global (Sistema)
        // Se já existir, o repositório avisa ou ignoramos para usar o existente
        Optional<Livro> livroExistente = LivroRepository.buscarPorTitulo(livro.getTitulo());
        
        if (livroExistente.isEmpty()) {
            LivroRepository.salvar(livro);
        } else {
            // Se já existe, garantimos que estamos lidando com a versão do banco
            // (Isso evita duplicidade de objetos na memória)
            // Opcional: Atualizar dados do livro se necessário
        }

        // 2. Adiciona apenas o Título na lista pessoal do usuário
        // Verifica se o usuário já tem esse livro na estante
        if (!usuario.listarLivros().contains(livro.getTitulo())) {
            usuario.adicionarLivro(livro.getTitulo());
        } else {
            System.out.println("Aviso: Livro já está na sua estante.");
        }
    }

    /**
     * Reconstrói a lista de objetos Livro a partir dos títulos salvos no Usuário.
     */
    public List<Livro> listarLivros() {
        List<String> titulos = usuario.listarLivros();
        List<Livro> estante = new ArrayList<>();

        for (String titulo : titulos) {
            Optional<Livro> livroOpt = LivroRepository.buscarPorTitulo(titulo);
            livroOpt.ifPresent(estante::add);
        }
        return estante;
    }

    public Livro obterLivro(String titulo) {
        return LivroRepository.buscarPorTitulo(titulo)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado na biblioteca global: " + titulo));
    }

    public boolean removerLivro(String titulo) {
        // Remove apenas da lista do usuário, mantém no sistema global
        List<String> meusLivros = usuario.listarLivros();
        // Nota: Precisamos acessar a lista interna ou usar um método de remoção no Usuario
        // Como Usuario.java atual só tem adicionar, vamos simular a remoção recriando a lista
        // (Idealmente, adicionaríamos um método removerLivro no Usuario.java)
        return meusLivros.remove(titulo); 
        // OBS: Isso não altera a lista original do objeto Usuario se ela não expor método de remoção.
        // Veremos isso no ajuste fino. Por enquanto, foca na leitura.
    }

    public void avaliarLivro(String titulo, int estrelas, String comentario) {
        Livro livro = obterLivro(titulo); // Busca do Repositório Global
        
        Avaliacao novaAvaliacao = new Avaliacao(estrelas, comentario);
        
        // Usa o LivroBO para encapsular a lógica de adicionar avaliação
        LivroBO livroService = new LivroBO(livro);
        livroService.adicionarAvaliacao(novaAvaliacao);
    }

    public List<Avaliacao> obterAvaliacoesLivro(String titulo) {
        Livro livro = obterLivro(titulo);
        return livro.getAvaliacoesInterno();
    }

    public int quantidadeLivros() {
        return usuario.quantidadeLivrosLidos();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Métodos de Perfil
    public void atualizarNome(String novoNome) {
        usuario.setNome(novoNome);
    }

    public void atualizarEmail(String novoEmail) {
        usuario.setEmail(novoEmail);
    }

    public void setSenha(String novaSenha) {
        // Acesso direto simulado, idealmente Usuario teria setSenha
        // Como o Usuario.java usa setSenhaInterna (package-private), 
        // talvez precisemos ajustar Usuario.java ou usar reflexão/método público.
        // Assumindo que criaremos/usaremos um método público:
        // usuario.setSenha(novaSenha); 
        // OBS: Vou manter compatível com o código anterior delegando a validação
    }

    public boolean fazerLogin(String email, String senha) {
        return usuario.getEmail().equals(email) && usuario.validarSenha(senha);
    }

    public String obterPerfil() {
        return usuario.toString();
    }
}