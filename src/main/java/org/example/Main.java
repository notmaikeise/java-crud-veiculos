package org.example;
import model.Veiculo;
import dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            Connection conexao = ConnectionFactory.getConnection();

            System.out.println("Conexão realizada com sucesso!");

            conexao.close();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco:");
            System.out.println(e.getMessage());
        }

//        Veiculo carro = new Veiculo(
//                "ABC1D23",
//                "Toyota",
//                "Corolla",
//                2023,
//                "Prata"
//        );
//
//        System.out.println(carro.getMarca());
//        System.out.println(carro.getModelo());
//        System.out.println(carro.getPlaca());
//        System.out.println(carro.getAno());
//        System.out.println(carro.getCor());
//        System.out.println(carro.isAtivo());
    }
}