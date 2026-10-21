import br.com.drygest.Categoria;
import br.com.drygest.GerenciadorServicos;
import br.com.drygest.Servico;
import br.com.drygest.Status;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        GerenciadorServicos gs = new GerenciadorServicos();
        Servico servico1 = new Servico("Forro de Sala", 1500, Categoria.FORRO, 4, 5);
        Servico servico2 = new Servico("Parede de Drywall", 2000, Categoria.PAREDE, 3, 4);
        Servico servico3 = new Servico("Forro de Cozinha", 800, Categoria.FORRO, 2, 3);
        gs.cadastrarServico(servico1);
        gs.cadastrarServico(servico2);
        gs.cadastrarServico(servico3);
        gs.verServicos();
        System.out.println(
                "Quantidade de serviços: " + gs.quantidadeServicos()
        );

        System.out.println("Buscando serviços da categoria FORRO...");
        gs.buscarPorCategoria(Categoria.FORRO).forEach(s -> System.out.println(s.getNome()));

        System.out.println("Buscando serviços da categoria SANCA...");
        List<Servico> sanca = gs.buscarPorCategoria(Categoria.SANCA);
        if (sanca.isEmpty()) {
            sanca.forEach(s -> System.out.println(s.getNome()));
        } else {
            System.out.println("Nenhum serviço encontrado.");
        }

        try {
            gs.buscarPorCategoria(null).forEach(s -> System.out.println(s.getNome()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("O valor total de todos os serviços contratados é de R$"+gs.somaTotalDosValores());


        System.out.println("O valor total dos serviços da categoria FORRO é de R$"+gs.somaValoresPorCategoria(Categoria.FORRO));
        System.out.println("O valor total dos serviços da categoria PAREDE é de R$"+gs.somaValoresPorCategoria(Categoria.PAREDE));
        System.out.println("O valor total dos serviços da categoria SANCA é de R$"+gs.somaValoresPorCategoria(Categoria.SANCA));
        try {
            System.out.println("O valor total dos serviços da categoria null é de R$" + gs.somaValoresPorCategoria(null));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        servico1.iniciarServico();
        System.out.println("br.com.drygest.Status do serviço: " + servico1.getStatus());
        try {
            servico1.iniciarServico();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        servico1.concluirServico();
        System.out.println("br.com.drygest.Status do serviço: " + servico1.getStatus());
        try {
            servico1.concluirServico();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        double somaStatus = gs.somaValoresPorStatus(Status.PENDENTE);
        System.out.println("A soma dos valores PENDENTES é de R$"+somaStatus);

        try {
            gs.cadastrarServico(servico1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}
