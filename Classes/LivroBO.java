import java.util.ArrayList;
import java.util.List;

public class LivroBO implements IAvaliavel{
	private Livro livro;

	public LivroBO(Livro livro) {
		if (livro == null) {
			throw new IllegalArgumentException("Livro não pode ser nulo");
		}
		this.livro = livro;
	}

	@Override
	public void adicionarAvaliacao(Avaliacao avaliacao) {
		if (avaliacao == null) {
			throw new IllegalArgumentException("Avaliação não pode ser nula");
		}
		livro.getAvaliacoesInterno().add(avaliacao);
	}

	@Override
	public List<Avaliacao> listarAvaliacoes() {
		return new ArrayList<>(livro.getAvaliacoesInterno());
	}

	@Override
	public double calcularMediaEstrelas() {
		List<Avaliacao> avals = livro.getAvaliacoesInterno();
		if (avals.isEmpty()) {
			return 0.0;
		}
		return avals.stream()
				.mapToInt(Avaliacao::getEstrelas)
				.average()
				.orElse(0.0);
	}

}

