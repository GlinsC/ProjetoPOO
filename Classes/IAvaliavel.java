import java.util.List;
interface IAvaliavel {
    void adicionarAvaliacao(Avaliacao avaliacao);
    List<Avaliacao> listarAvaliacoes();
    double calcularMediaEstrelas();
}

