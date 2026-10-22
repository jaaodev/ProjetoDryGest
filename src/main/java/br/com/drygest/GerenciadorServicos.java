package br.com.drygest;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorServicos {
    int contadorId = 1;
    private List<Servico> servicos;

    public GerenciadorServicos() {
        this.servicos = new ArrayList<>();
    }

    public void cadastrarServico(Servico servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Cadastre um serviço válido.");
        } else if (this.servicos.contains(servico)) {
            throw new IllegalArgumentException("Serviço já cadastrado, tente novamente.");
        }
        servico.atribuirId(this.contadorId);
        this.servicos.add(servico);
        this.contadorId++;
    }

    public void verServicos() {
        this.servicos.forEach(s -> System.out.println("Serviço: " + s.getNome()
                + ", Valor: R$" + s.getValor()
                + ", br.com.drygest.Categoria: " + s.getCategoria()
                + ", br.com.drygest.Status: " + s.getStatus()));
    }

    public int quantidadeServicos() {
        return servicos.size();
    }

    public List<Servico> buscarPorCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Digite uma categoria válida.");
        }
        List<Servico> busca = servicos.stream()
                .filter(s -> s.getCategoria() == categoria)
                .toList();
        return busca;
    }

    public double somaTotalDosValores() {
        double soma = servicos.stream()
                .mapToDouble(s -> s.getValor())
                .sum();
        return soma;
    }

    public double somaValoresPorCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Digite uma categoria válida.");
        }
        double soma = servicos.stream()
                .filter(s -> s.getCategoria() == categoria)
                .mapToDouble(s -> s.getValor())
                .sum();
        return soma;
    }

    public double somaValoresPorStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Digite um status válido.");
        }
        double soma = servicos.stream()
                .filter(s -> s.getStatus() == status)
                .mapToDouble(s -> s.getValor())
                .sum();
        return soma;
    }

}
