package org.example;

import model.Veiculo;
import service.VeiculoService;

public class Main {

    public static void main(String[] args) {

        VeiculoService service = new VeiculoService();

        try {

            // 1. CADASTRAR
            System.out.println("=== CADASTRO ===");

            Veiculo carro = new Veiculo(
                    "UVW1X23",
                    "Honda",
                    "Civic",
                    2022,
                    "Branco"
            );

            service.cadastrarVeiculo(carro);

            System.out.println();


            // 2. CONSULTAR
            System.out.println("=== CONSULTA ===");

            Veiculo encontrado = service.consultarVeiculo(3);

            System.out.println("ID: " + encontrado.getId());
            System.out.println("Placa: " + encontrado.getPlaca());
            System.out.println("Marca: " + encontrado.getMarca());
            System.out.println("Modelo: " + encontrado.getModelo());
            System.out.println("Ano: " + encontrado.getAno());
            System.out.println("Cor: " + encontrado.getCor());
            System.out.println("Ativo: " + encontrado.isAtivo());

            System.out.println();


            // 3. ATUALIZAR
            System.out.println("=== ATUALIZAÇÃO ===");

            encontrado.setCor("Preto");
            encontrado.setAno(2023);

            service.atualizarVeiculo(encontrado);

            Veiculo atualizado = service.consultarVeiculo(encontrado.getId());

            System.out.println("Nova cor: " + atualizado.getCor());
            System.out.println("Novo ano: " + atualizado.getAno());

            System.out.println();


            // 4. REMOVER
            System.out.println("=== REMOÇÃO ===");

            service.removerVeiculo(atualizado.getId());

            System.out.println("Veículo removido.");

        } catch (IllegalArgumentException e) {

            System.out.println("Erro de regra de negócio:");
            System.out.println(e.getMessage());

        }
    }
}