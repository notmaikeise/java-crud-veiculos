package org.example;
import model.Veiculo;

public class Main {

    public static void main(String[] args) {

        Veiculo carro = new Veiculo(
                "ABC1D23",
                "Toyota",
                "Corolla",
                2023,
                "Prata"
        );

        System.out.println(carro.getMarca());
        System.out.println(carro.getModelo());
        System.out.println(carro.getPlaca());
        System.out.println(carro.getAno());
        System.out.println(carro.getCor());
        System.out.println(carro.isAtivo());
    }
}