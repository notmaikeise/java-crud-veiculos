package org.example;

import dao.VeiculoDAO;
import model.Veiculo;
import service.VeiculoService;

public class Main {

    private static int testesExecutados = 0;
    private static int testesAprovados = 0;

    public static void main(String[] args) {

        VeiculoService service = new VeiculoService();
        VeiculoDAO dao = new VeiculoDAO();

        // Escolha um veículo ATIVO já existente no banco
        int idTeste = 7;

        System.out.println("==============================================");
        System.out.println("        TESTES DO CRUD DE VEÍCULOS");
        System.out.println("==============================================");

        testarCadastroValido(service);
        testarPlacaDuplicada(service);
        testarAnoInvalido(service);
        testarConsultaPorId(service, idTeste);
        testarAtualizacao(service, idTeste);
        testarRemocaoLogica(service, dao, idTeste);
        testarRemocaoDuplicada(service, idTeste);

        exibirResultadoFinal();
    }


    // =========================================================
    // TESTE 01 - CADASTRO VÁLIDO
    // =========================================================

    private static void testarCadastroValido(VeiculoService service) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Cadastro de veículo válido"
        );

        Veiculo veiculo = new Veiculo(
                "TST9A99",
                "Toyota",
                "Corolla",
                2023,
                "Prata"
        );

        System.out.println("ENTRADA:");
        exibirVeiculo(veiculo);

        System.out.println();
        System.out.println("ESPERADO:");
        System.out.println("O veículo deve ser cadastrado com sucesso.");

        try {

            service.cadastrarVeiculo(veiculo);

            aprovado(
                    "O veículo foi cadastrado corretamente."
            );

        } catch (Exception e) {

            falhou(
                    "O cadastro não foi concluído.",
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // TESTE 02 - PLACA DUPLICADA
    // =========================================================

    private static void testarPlacaDuplicada(VeiculoService service) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Bloqueio de placa duplicada"
        );

        Veiculo veiculo = new Veiculo(
                "TST9A99",
                "Toyota",
                "Corolla",
                2023,
                "Prata"
        );

        System.out.println("ENTRADA:");
        System.out.println(
                "Tentativa de cadastrar novamente a placa: "
                        + veiculo.getPlaca()
        );

        System.out.println();
        System.out.println("ESPERADO:");
        System.out.println(
                "O sistema deve impedir o cadastro da placa duplicada."
        );

        try {

            service.cadastrarVeiculo(veiculo);

            falhou(
                    "O sistema permitiu cadastrar uma placa duplicada.",
                    "A validação de duplicidade não funcionou."
            );

        } catch (IllegalArgumentException e) {

            if ("Placa já cadastrada.".equals(e.getMessage())) {

                aprovado(
                        "Cadastro bloqueado corretamente: "
                                + e.getMessage()
                );

            } else {

                falhou(
                        "Foi recebida uma mensagem diferente da esperada.",
                        e.getMessage()
                );
            }
        }
    }


    // =========================================================
    // TESTE 03 - ANO INVÁLIDO
    // =========================================================

    private static void testarAnoInvalido(VeiculoService service) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Bloqueio de ano inválido"
        );

        Veiculo veiculo = new Veiculo(
                "TST8A88",
                "Honda",
                "Civic",
                2100,
                "Preto"
        );

        System.out.println("ENTRADA:");
        exibirVeiculo(veiculo);

        System.out.println();
        System.out.println("ESPERADO:");
        System.out.println(
                "O sistema deve impedir o cadastro por ano inválido."
        );

        try {

            service.cadastrarVeiculo(veiculo);

            falhou(
                    "O sistema permitiu cadastrar um ano inválido.",
                    "A validação do ano não funcionou."
            );

        } catch (IllegalArgumentException e) {

            if ("Ano inválido.".equals(e.getMessage())) {

                aprovado(
                        "Cadastro bloqueado corretamente: "
                                + e.getMessage()
                );

            } else {

                falhou(
                        "Foi recebida uma mensagem diferente da esperada.",
                        e.getMessage()
                );
            }
        }
    }


    // =========================================================
    // TESTE 04 - CONSULTA POR ID
    // =========================================================

    private static void testarConsultaPorId(
            VeiculoService service,
            int idTeste
    ) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Consulta de veículo por ID"
        );

        System.out.println("ENTRADA:");
        System.out.println("ID informado: " + idTeste);

        System.out.println();
        System.out.println("ESPERADO:");
        System.out.println(
                "O sistema deve encontrar e retornar o veículo."
        );

        try {

            Veiculo veiculo =
                    service.consultarVeiculo(idTeste);

            System.out.println();
            System.out.println("VEÍCULO ENCONTRADO:");
            exibirVeiculoCompleto(veiculo);

            aprovado(
                    "O veículo foi localizado corretamente."
            );

        } catch (Exception e) {

            falhou(
                    "O veículo não pôde ser consultado.",
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // TESTE 05 - ATUALIZAÇÃO
    // =========================================================

    private static void testarAtualizacao(
            VeiculoService service,
            int idTeste
    ) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Atualização de veículo"
        );

        try {

            Veiculo veiculo =
                    service.consultarVeiculo(idTeste);

            System.out.println("ENTRADA:");
            System.out.println("ID: " + idTeste);
            System.out.println(
                    "Cor atual: " + veiculo.getCor()
            );
            System.out.println("Nova cor: Azul");

            System.out.println();
            System.out.println("ESPERADO:");
            System.out.println(
                    "A cor do veículo deve ser atualizada para Azul."
            );

            veiculo.setCor("Azul");

            service.atualizarVeiculo(veiculo);

            Veiculo atualizado =
                    service.consultarVeiculo(idTeste);

            System.out.println();
            System.out.println("OBTIDO:");
            System.out.println(
                    "Cor armazenada no banco: "
                            + atualizado.getCor()
            );

            if ("Azul".equals(atualizado.getCor())) {

                aprovado(
                        "O veículo foi atualizado corretamente."
                );

            } else {

                falhou(
                        "A atualização não foi aplicada corretamente.",
                        "A cor encontrada foi: "
                                + atualizado.getCor()
                );
            }

        } catch (Exception e) {

            falhou(
                    "Não foi possível atualizar o veículo.",
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // TESTE 06 - REMOÇÃO LÓGICA
    // =========================================================

    private static void testarRemocaoLogica(
            VeiculoService service,
            VeiculoDAO dao,
            int idTeste
    ) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Remoção lógica de veículo"
        );

        System.out.println("ENTRADA:");
        System.out.println("ID informado: " + idTeste);

        System.out.println();
        System.out.println("ESPERADO:");
        System.out.println(
                "O veículo deve continuar no banco,"
                        + " mas com ativo = false."
        );

        try {

            service.removerVeiculo(idTeste);

            Veiculo veiculo =
                    dao.buscarPorId(idTeste);

            System.out.println();
            System.out.println("OBTIDO:");

            if (veiculo != null) {
                System.out.println(
                        "Veículo encontrado no banco: SIM"
                );
                System.out.println(
                        "Ativo: " + veiculo.isAtivo()
                );
            }

            if (
                    veiculo != null
                            && !veiculo.isAtivo()
            ) {

                aprovado(
                        "A remoção lógica foi realizada corretamente."
                );

            } else {

                falhou(
                        "A remoção lógica não funcionou.",
                        "O veículo continua ativo ou não foi encontrado."
                );
            }

        } catch (Exception e) {

            falhou(
                    "Não foi possível remover o veículo.",
                    e.getMessage()
            );
        }
    }


    // =========================================================
    // TESTE 07 - REMOÇÃO DUPLICADA
    // =========================================================

    private static void testarRemocaoDuplicada(
            VeiculoService service,
            int idTeste
    ) {

        testesExecutados++;

        iniciarTeste(
                testesExecutados,
                "Bloqueio de remoção duplicada"
        );

        System.out.println("ENTRADA:");
        System.out.println(
                "Tentativa de remover novamente o veículo de ID "
                        + idTeste
        );

        System.out.println();
        System.out.println("ESPERADO:");
        System.out.println(
                "O sistema deve impedir a remoção de um veículo já inativo."
        );

        try {

            service.removerVeiculo(idTeste);

            falhou(
                    "O sistema permitiu remover novamente o veículo.",
                    "A validação de veículo inativo não funcionou."
            );

        } catch (IllegalArgumentException e) {

            if (
                    "Veículo já está inativo."
                            .equals(e.getMessage())
            ) {

                aprovado(
                        "Remoção bloqueada corretamente: "
                                + e.getMessage()
                );

            } else {

                falhou(
                        "Foi recebida uma mensagem diferente da esperada.",
                        e.getMessage()
                );
            }
        }
    }


    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================

    private static void iniciarTeste(
            int numero,
            String nome
    ) {

        System.out.println();
        System.out.println(
                "----------------------------------------------"
        );

        System.out.println(
                "[TESTE "
                        + String.format("%02d", numero)
                        + "] "
                        + nome
        );

        System.out.println(
                "----------------------------------------------"
        );
    }


    private static void aprovado(
            String resultado
    ) {

        testesAprovados++;

        System.out.println();
        System.out.println("RESULTADO:");
        System.out.println(resultado);

        System.out.println();
        System.out.println("STATUS: [OK]");
    }


    private static void falhou(
            String resultado,
            String motivo
    ) {

        System.out.println();
        System.out.println("RESULTADO:");
        System.out.println(resultado);

        System.out.println();
        System.out.println("STATUS: [FALHOU]");
        System.out.println("MOTIVO: " + motivo);
    }


    private static void exibirVeiculo(
            Veiculo veiculo
    ) {

        System.out.println(
                "Placa: " + veiculo.getPlaca()
        );
        System.out.println(
                "Marca: " + veiculo.getMarca()
        );
        System.out.println(
                "Modelo: " + veiculo.getModelo()
        );
        System.out.println(
                "Ano: " + veiculo.getAno()
        );
        System.out.println(
                "Cor: " + veiculo.getCor()
        );
    }


    private static void exibirVeiculoCompleto(
            Veiculo veiculo
    ) {

        System.out.println(
                "ID: " + veiculo.getId()
        );
        System.out.println(
                "Placa: " + veiculo.getPlaca()
        );
        System.out.println(
                "Marca: " + veiculo.getMarca()
        );
        System.out.println(
                "Modelo: " + veiculo.getModelo()
        );
        System.out.println(
                "Ano: " + veiculo.getAno()
        );
        System.out.println(
                "Cor: " + veiculo.getCor()
        );
        System.out.println(
                "Ativo: " + veiculo.isAtivo()
        );
    }


    private static void exibirResultadoFinal() {

        int testesFalharam =
                testesExecutados - testesAprovados;

        System.out.println();
        System.out.println();
        System.out.println(
                "=============================================="
        );
        System.out.println(
                "                RESULTADO FINAL"
        );
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "Testes executados: " + testesExecutados
        );
        System.out.println(
                "Testes aprovados:  " + testesAprovados
        );
        System.out.println(
                "Testes falharam:   " + testesFalharam
        );

        System.out.println();

        if (testesFalharam == 0) {

            System.out.println(
                    "RESULTADO GERAL: TODOS OS TESTES PASSARAM."
            );

        } else {

            System.out.println(
                    "RESULTADO GERAL: EXISTEM TESTES COM FALHA."
            );
        }

        System.out.println(
                "=============================================="
        );
    }
}