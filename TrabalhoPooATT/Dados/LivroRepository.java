package Dados;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import Model.Livro;
import exception.PersistenceException;

public class LivroRepository {
    private static final String ARQUIVO = "livros.dat";
    private static List<Livro> tabelaLivros;

    static {
        tabelaLivros = carregarDados();
    }

    public static void salvar(Livro livro) {
        if (buscarPorTitulo(livro.getTitulo()).isPresent()) {
            throw new PersistenceException("Livro já existe: " + livro.getTitulo());
        }
        tabelaLivros.add(livro);
        salvarDadosEmArquivo();
    }
    
    // Método para forçar salvamento quando houver avaliação nova
    public static void atualizar(Livro livro) {
        salvarDadosEmArquivo();
    }

    public static Optional<Livro> buscarPorTitulo(String titulo) {
        return tabelaLivros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public static List<Livro> listarTodos() {
        return new ArrayList<>(tabelaLivros);
    }

    // --- Persistência ---

    private static void salvarDadosEmArquivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(tabelaLivros);
        } catch (IOException e) {
            System.err.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Livro> carregarDados() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            List<Livro> listaInicial = new ArrayList<>();
            listaInicial.add(new Livro("Dom Casmurro", "Machado de Assis", 1899));
            listaInicial.add(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954));
            listaInicial.add(new Livro("Clean Code", "Robert C. Martin", 2008));
            return listaInicial;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Livro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}