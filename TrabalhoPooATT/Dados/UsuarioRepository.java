    package Dados;

    import java.io.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import Pessoa.Usuario;
    import exception.PersistenceException;

    public class UsuarioRepository {
        private static final String ARQUIVO = "usuarios.dat";
        private static List<Usuario> tabelaUsuarios;

        // Bloco estático: Carrega os dados assim que o programa inicia
        static {
            tabelaUsuarios = carregarDados();
        }

        public static void salvar(Usuario usuario) {
            if (buscarPorEmail(usuario.getEmail()).isPresent()) {
                throw new PersistenceException("Email já cadastrado: " + usuario.getEmail());
            }
            tabelaUsuarios.add(usuario);
            salvarDadosEmArquivo(); // Salva no disco
        }
        
        // Atualiza um usuário existente (ex: adicionou livro) e salva
        public static void atualizar(Usuario usuario) {
            // Como estamos trabalhando com objetos em memória referenciados, 
            // a atualização do objeto já aconteceu. Só precisamos persistir no disco.
            salvarDadosEmArquivo();
        }

        public static Optional<Usuario> buscarPorEmail(String email) {
            return tabelaUsuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        }

        public static List<Usuario> listarTodos() {
            return new ArrayList<>(tabelaUsuarios);
        }
        
        // --- Persistência em Arquivo ---

        private static void salvarDadosEmArquivo() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
                oos.writeObject(tabelaUsuarios);
            } catch (IOException e) {
                System.err.println("Erro ao salvar usuários: " + e.getMessage());
            }
        }

        @SuppressWarnings("unchecked")
        private static List<Usuario> carregarDados() {
            File arquivo = new File(ARQUIVO);
            if (!arquivo.exists()) {
                // Se não existe arquivo, cria lista inicial com Admin
                List<Usuario> listaInicial = new ArrayList<>();
                listaInicial.add(new Usuario("Admin", "admin@email.com", "123456"));
                return listaInicial;
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                return (List<Usuario>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar usuários (criando nova base): " + e.getMessage());
                return new ArrayList<>();
            }
        }
    }