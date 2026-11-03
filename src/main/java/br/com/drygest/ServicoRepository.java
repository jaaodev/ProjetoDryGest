package br.com.drygest;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {

    public List<Servico> buscarTodos() throws SQLException {
        ArrayList<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conexao = ConexaoPostgreSQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    double valor = resultado.getDouble("valor");
                    String categoriaBD = resultado.getString("categoria");
                    Categoria categoria = Categoria.valueOf(categoriaBD);
                    String statusBD = resultado.getString("status");
                    Status status = Status.valueOf(statusBD);
                    double largura = resultado.getDouble("largura");
                    double comprimento = resultado.getDouble("comprimento");
                    boolean arquivado = resultado.getBoolean("arquivado");
                    servicos.add(Servico.restaurar(id, nome, valor, categoria, status, largura, comprimento, arquivado));
                }
            }
        }
        return servicos;
    }

    public void salvar(Servico servico) throws SQLException {
        if (servico.getId() != 0) {
            throw new IllegalStateException("O serviço já tem um ID definido.");
        }
        String sql = "INSERT INTO servicos\n" +
                "(nome, valor, categoria, largura, comprimento)\n" +
                "VALUES (?, ?, ?, ?, ?)" +
                "RETURNING id;";
        try (Connection conexao = ConexaoPostgreSQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, servico.getNome());
            comando.setDouble(2, servico.getValor());
            comando.setString(3, servico.getCategoria().name());
            comando.setDouble(4, servico.getLargura());
            comando.setDouble(5, servico.getComprimento());
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    int id = resultado.getInt("id");
                    servico.atribuirId(id);
                } else {
                    throw new SQLException(
                            "O banco não retornou o ID do serviço."
                    );
                }
            }
        }
    }

    public void atualizarStatus(Servico original, Servico copia) throws SQLException {
        String sql = "UPDATE servicos\n" +
                "SET status = ?\n" +
                "WHERE id = ? " +
                "AND status = ?;";
        try (Connection conexao = ConexaoPostgreSQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, copia.getStatus().name());
            comando.setInt(2, original.getId());
            comando.setString(3, original.getStatus().name());
            int resultado = comando.executeUpdate();
            if (resultado != 1) {
                throw new ConflitoAtualizacaoException(
                        "Os dados do serviço foram alterados."
                );
            }

        }
    }

    public void atualizarArquivamento(Servico original, Servico copia) throws SQLException {
        String sql = "UPDATE servicos\n" +
                "SET arquivado = ?\n" +
                "WHERE id = ? " +
                "AND arquivado = ?;";
        try (Connection conexao = ConexaoPostgreSQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setBoolean(1, copia.getArquivado());
            comando.setInt(2, original.getId());
            comando.setBoolean(3, original.getArquivado());
            int resultado = comando.executeUpdate();
            if (resultado != 1) {
                throw new ConflitoAtualizacaoException(
                        "Os dados do serviço foram alterados."
                );
            }

        }
    }
}
