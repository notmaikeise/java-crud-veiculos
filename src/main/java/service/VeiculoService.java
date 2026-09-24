package service;

import dao.VeiculoDAO;
import model.Veiculo;

import java.time.Year;

public class VeiculoService {

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public void cadastrarVeiculo(Veiculo veiculo) {

        if (veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) {
            throw new IllegalArgumentException("Placa é obrigatória.");

        } else if (veiculo.getAno() > Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Ano inválido.");

        } else if (veiculoDAO.buscarPorPlaca(veiculo.getPlaca()) != null) {
            throw new IllegalArgumentException("Placa já cadastrada.");

        } else {
            veiculo.setAtivo(true);
            veiculoDAO.inserir(veiculo);
        }
    }

    public Veiculo consultarVeiculo(int id) {

        Veiculo veiculo = veiculoDAO.buscarPorId(id);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }

        return veiculo;
    }

    public void atualizarVeiculo(Veiculo veiculo) {

        Veiculo existente = veiculoDAO.buscarPorId(veiculo.getId());

        if (existente == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");

        } else if (!existente.isAtivo()) {
            throw new IllegalArgumentException("Não é possível atualizar inativo.");

        } else if (veiculo.getAno() > Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Ano inválido.");

        } else {
            veiculoDAO.atualizar(veiculo);
        }
    }

    public void removerVeiculo(int id) {

        Veiculo veiculo = veiculoDAO.buscarPorId(id);

        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não encontrado.");

        } else if (!veiculo.isAtivo()) {
            throw new IllegalArgumentException("Veículo já está inativo.");

        } else {
            veiculoDAO.deletar(id);
        }
    }
}