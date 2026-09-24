package org.example;

import dao.VeiculoDAO;
import model.Veiculo;

public class Main {

    public static void main(String[] args) {

        VeiculoDAO dao = new VeiculoDAO();

        Veiculo veiculo = dao.buscarPorId(1);

        if (veiculo != null) {
            System.out.println(veiculo.getId());
            System.out.println(veiculo.getPlaca());
            System.out.println(veiculo.getMarca());
            System.out.println(veiculo.getModelo());
        } else {
            System.out.println("Veículo não encontrado.");
        }
    }
}