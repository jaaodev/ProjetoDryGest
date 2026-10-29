import br.com.drygest.Categoria;
import br.com.drygest.GerenciadorServicos;
import br.com.drygest.Servico;
import br.com.drygest.Status;

import java.text.NumberFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciadorServicos gs = new GerenciadorServicos();
        int opcao;

        do {

            System.out.println("======== DryGest ========");
            System.out.println("1 - Cadastrar Serviço");
            System.out.println("2 - Listar Serviços");
            System.out.println("3 - Iniciar serviço");
            System.out.println("4 - Concluir serviço");
            System.out.println("5 - Cancelar serviço");
            System.out.println("6 - Arquivar serviço");
            System.out.println("7 - Histórico de serviços");
            System.out.println("0 - Sair");
            System.out.println("Escolha uma opção: ");
            opcao = lerInteiro(sc);
            switch (opcao) {
                case 1:
                    cadastrarServico(sc, gs);
                    break;
                case 2:
                    listarServicos(gs);
                    break;
                case 3:
                    iniciarServico(sc, gs);
                    break;
                case 4:
                    concluirServico(sc, gs);
                    break;
                case 5:
                    cancelarServico(sc, gs);
                    break;
                case 6:
                    arquivarServico(sc, gs);
                    break;
                case 7:
                    historicoServicosArquivados(gs);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opcão inválida, tente novamente.");
                    break;
            }

        } while (opcao != 0);
    }

    private static void cadastrarServico(
            Scanner sc,
            GerenciadorServicos gs
    ) {
        System.out.println("Cadastro de serviço selecionado.");
        System.out.print("Digite o nome do serviço: ");
        String nomeServico = sc.nextLine();
        System.out.println("Digite o valor do serviço: ");
        double valorServico = lerDouble(sc);
        System.out.println("Digite a largura: ");
        double largura = lerDouble(sc);
        System.out.println("Digite o comprimento: ");
        double comprimento = lerDouble(sc);
        System.out.println("Escolha a categoria do serviço: ");
        System.out.println("1 - FORRO");
        System.out.println("2 - PAREDE");
        System.out.println("3 - SANCA");
        System.out.println("4 - LINEAR");
        System.out.println("Digite a opção: ");
        Categoria categoria;
        int opcaoCategoria = lerInteiro(sc);
        switch (opcaoCategoria) {
            case 1:
                categoria = Categoria.FORRO;
                break;
            case 2:
                categoria = Categoria.PAREDE;
                break;
            case 3:
                categoria = Categoria.SANCA;
                break;
            case 4:
                categoria = Categoria.LINEAR;
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }
        try {
            Servico servico = new Servico(nomeServico, valorServico, categoria, largura, comprimento);
            gs.cadastrarServico(servico);
            System.out.println("Serviço cadastrado com sucesso!");
            System.out.println("ID: " + servico.getId());
            System.out.println("Nome do serviço: " + servico.getNome());
            System.out.println("Categoria: " + servico.getCategoria());
            System.out.println("Valor: " + formatarMoeda(servico.getValor()));
            System.out.println("Área: " + servico.calcularArea()
                    + "m²");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void iniciarServico(
            Scanner sc,
            GerenciadorServicos gs
    ) {
        try {
            System.out.println("Digite o ID do serviço que será iniciado: ");
            int id = lerInteiro(sc);
            gs.iniciarServicoPorId(id);
            System.out.println("Serviço #"+ id +" iniciado com sucesso!");
        } catch (NoSuchElementException e) {
            System.out.println("Serviço não encontrado.");
        } catch (IllegalStateException e) {
            System.out.println("Não é possível iniciar esse serviço.");
        }
    }

    private static void concluirServico(
            Scanner sc,
            GerenciadorServicos gs
    ) {
        try {
            System.out.println("Digite o ID do serviço que será concluído: ");
            int id = lerInteiro(sc);
            gs.concluirServicoPorId(id);
            System.out.println("Serviço #"+ id +" concluido com sucesso!");
        } catch (NoSuchElementException e) {
            System.out.println("Serviço não encontrado.");
        } catch (IllegalStateException e) {
            System.out.println("Não é possível concluir esse serviço.");
        }
    }

    private static void cancelarServico(
            Scanner sc,
            GerenciadorServicos gs
    ) {
        try {
            System.out.println("Digite o ID do serviço que será cancelado: ");
            int id = lerInteiro(sc);
            gs.cancelarServicoPorId(id);
            System.out.println("Serviço #"+ id +" cancelado com sucesso!");
        } catch (NoSuchElementException e) {
            System.out.println("Serviço não encontrado.");
        } catch (IllegalStateException e) {
            System.out.println("Não é possível cancelar esse serviço.");
        }
    }

    private static void arquivarServico(
            Scanner sc,
            GerenciadorServicos gs
    ) {
        try {
            System.out.println("Digite o ID do serviço que será arquivado: ");
            int id = lerInteiro(sc);
            gs.arquivarServicoPorId(id);
            System.out.println("Serviço #"+ id +" arquivado com sucesso!");
        } catch (NoSuchElementException e) {
            System.out.println("Serviço não encontrado.");
        } catch (IllegalStateException e) {
            System.out.println("Não é possível arquivar esse serviço.");
        }
    }

    private static int lerInteiro(Scanner sc) {
        while (true) {
            int numeroInteiro;
            try {
                numeroInteiro = sc.nextInt();
                sc.nextLine();
                return numeroInteiro;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                sc.nextLine();
            }
        }
    }

    private static double lerDouble(Scanner sc) {
        while (true) {
            double numeroDouble;
            try {
                numeroDouble = sc.nextDouble();
                sc.nextLine();
                return numeroDouble;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                sc.nextLine();
            }
        }
    }

    private static String formatarMoeda(double valor) {
        Locale brasil = Locale.of("pt", "BR");

        NumberFormat formatoMoeda =
                NumberFormat.getCurrencyInstance(brasil);
        return formatoMoeda.format(valor);
    }

    private static void exibirServico(Servico servico) {
        System.out.println("ID: " + servico.getId()
                + " | Serviço: " + servico.getNome()
                + " | Valor: " + formatarMoeda(servico.getValor())
                + " | Categoria: " + servico.getCategoria()
                + " | Status: " + servico.getStatus());
    }

    private static void listarServicos(GerenciadorServicos gs) {
        List<Servico> servicos = gs.buscarPorArquivamento(false);
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado.");
            return;
        } System.out.println("======== LISTA DE SERVIÇOS ========");
        servicos.forEach(Main::exibirServico);
    }

    private static void historicoServicosArquivados(GerenciadorServicos gs) {
        List<Servico> servicos = gs.buscarPorArquivamento(true);
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço arquivado encontrado.");
            return;
        } System.out.println("======== HISTÓRICO DE SERVIÇOS ARQUIVADOS ========");
        servicos.forEach(Main::exibirServico);
    }
}
