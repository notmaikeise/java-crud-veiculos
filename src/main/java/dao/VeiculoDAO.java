package dao;

import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public void inserir(Veiculo veiculo) {

        String sql = """
            INSERT INTO veiculo
            (placa, marca, modelo, ano, cor, ativo)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAno());
            stmt.setString(5, veiculo.getCor());
            stmt.setBoolean(6, veiculo.isAtivo());

            stmt.executeUpdate();

            System.out.println("Veículo cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar veículo:");
            System.out.println(e.getMessage());
        }
    }

    public List<Veiculo> listarTodos() {

        String sql = "SELECT * FROM veiculo";

        List<Veiculo> veiculos = new ArrayList<>();

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Veiculo veiculo = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getString("cor")
                );

                veiculo.setId(rs.getInt("id"));
                veiculo.setAtivo(rs.getBoolean("ativo"));

                veiculos.add(veiculo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar veículos:");
            System.out.println(e.getMessage());
        }

        return veiculos;
    }

    public Veiculo buscarPorId(int id) {

        String sql = "SELECT * FROM veiculo WHERE id = ?";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Veiculo veiculo = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getString("cor")
                );

                veiculo.setId(rs.getInt("id"));
                veiculo.setAtivo(rs.getBoolean("ativo"));

                return veiculo;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar veículo:");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void atualizar(Veiculo veiculo) {

        String sql = """
            UPDATE veiculo
            SET placa = ?, marca = ?, modelo = ?, ano = ?, cor = ?, ativo = ?
            WHERE id = ?
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAno());
            stmt.setString(5, veiculo.getCor());
            stmt.setBoolean(6, veiculo.isAtivo());
            stmt.setInt(7, veiculo.getId());

            stmt.executeUpdate();

            System.out.println("Veículo atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar veículo:");
            System.out.println(e.getMessage());
        }
    }

    public void deletar(int id) {

        String sql = "DELETE FROM veiculo WHERE id = ?";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Veículo removido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover veículo:");
            System.out.println(e.getMessage());
        }
    }
}