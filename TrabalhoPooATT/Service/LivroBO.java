package Service;

import java.util.ArrayList;
import java.util.List;
import Model.Livro;
import Avaliacao.Avaliacao;
import Interface.IAvaliavel;
import java.time.Year;
import exception.ValidationException;

public class LivroBO implements IAvaliavel {
    private Livro livro;

    public LivroBO(Livro livro) {
        if (livro == null) {
            throw new ValidationException("Livro não pode ser nulo");
        }
        this.livro = livro;
    }

    /**
     * Valida ano de publicação — lógica de negócio centralizada no BO.
     */
    public static void validarAno(int ano) {
        int atual = Year.now().getValue();
        if (ano <= 0 || ano > atual) {
            throw new ValidationException("Ano de publicação inválido: " + ano);
        }
    }

    @Override
    public void adicionarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao == null) {
            throw new ValidationException("Avaliação não pode ser nula");
        }
        livro.getAvaliacoesInterno().add(avaliacao);
    }

    @Override
    public List<Avaliacao> listarAvaliacoes() {
        List<Avaliacao> result = new ArrayList<>();
        for (Object obj : livro.getAvaliacoesInterno()) {
            if (obj instanceof Avaliacao) {
                result.add((Avaliacao) obj);
            }
        }
        return result;
    }

    @Override
    public double calcularMediaEstrelas() {
        List<Avaliacao> avals = listarAvaliacoes();
        if (avals.isEmpty()) {
            return 0.0;
        }
        return avals.stream()
                .mapToInt(Avaliacao::getEstrelas)
                .average()
                .orElse(0.0);
    }

    /**
     * Obtém o livro
     */
    public Livro getLivro() {
        return livro;
    }

    /**
     * Retorna as informações do livro formatadas
     */
    public String obterDetalhesLivro() {
        return livro.toString();
    }

    /**
     * Verifica se o livro tem avaliações
     */
    public boolean temAvaliacoes() {
        return !livro.getAvaliacoesInterno().isEmpty();
    }

    /**
     * Obtém a quantidade de avaliações
     */
    public int obterQuantidadeAvaliacoes() {
        return livro.getAvaliacoesInterno().size();
    }
}
