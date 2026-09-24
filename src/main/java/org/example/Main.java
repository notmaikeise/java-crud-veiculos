package org.example;

import dao.VeiculoDAO;

public class Main {

    public static void main(String[] args) {

        VeiculoDAO dao = new VeiculoDAO();

        dao.deletar(1);
    }
}