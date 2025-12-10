package Interface;

import java.util.List;
import Avaliacao.Avaliacao;

public interface IAvaliavel {
    void adicionarAvaliacao(Avaliacao avaliacao);
    List<Avaliacao> listarAvaliacoes();
    double calcularMediaEstrelas();
}
