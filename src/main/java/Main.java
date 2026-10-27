import br.com.drygest.Categoria;
import br.com.drygest.GerenciadorServicos;
import br.com.drygest.Servico;
import br.com.drygest.Status;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciadorServicos gs = new GerenciadorServicos();
        int opcao = -1;

        do {

            System.out.println("======== DryGest ========");
            System.out.println();
            System.out.println("1 - Cadastrar Serviço");
            System.out.println("2 - Listar Serviços");
            System.out.println("3 - Iniciar serviço");
            System.out.println("4 - Concluir serviço");
            System.out.println("5 - Cancelar serviço");
            System.out.println("6 - Arquivar serviço");
            System.out.println("7 - Histórico de serviços");
            System.out.println("0 - Sair");
            System.out.println();
            System.out.println("Escolha uma opção: ");
            try {
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                sc.nextLine();
                continue;
            }
            switch (opcao) {
                case 1:
                    cadastrarServico(sc, gs);
                    break;
                case 2:
                    gs.verServicos();
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
                    historicoServico(gs);
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
        double valorServico = sc.nextDouble();
        System.out.println("Digite a largura: ");
        double largura = sc.nextDouble();
        System.out.println("Digite o comprimento: ");
        double comprimento = sc.nextDouble();
        System.out.println("Escolha a categoria do serviço: ");
        System.out.println();
        System.out.println("1 - FORRO");
        System.out.println("2 - PAREDE");
        System.out.println("3 - SANCA");
        System.out.println("4 - LINEAR");
        System.out.println();
        System.out.println("Digite a opção: ");
        Categoria categoria;
        int opcaoCategoria = sc.nextInt();
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
        Servico servico = new Servico(nomeServico, valorServico, categoria, largura, comprimento);
        gs.cadastrarServico(servico);
        System.out.println("Serviço cadastrado com sucesso!");
        System.out.println("ID: " + servico.getId());
        System.out.println("Nome do serviço: " + servico.getNome());
        System.out.println("Categoria: " + servico.getCategoria());
        System.out.println("Valor: R$ " + servico.getValor());
        System.out.println("Área: " + servico.calcularArea()
                + "m²");
    }

    private static void iniciarServico(
            Scanner sc,
            GerenciadorServicos gs
    ) {
        try {
            System.out.println("Digite o ID do serviço que será iniciado: ");
            gs.iniciarServicoPorId(sc.nextInt());
            System.out.println("Serviço iniciado com sucesso!");
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
            gs.concluirServicoPorId(sc.nextInt());
            System.out.println("Serviço concluido com sucesso!");
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
            gs.cancelarServicoPorId(sc.nextInt());
            System.out.println("Serviço cancelado com sucesso!");
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
            gs.arquivarServicoPorId(sc.nextInt());
            System.out.println("Serviço arquivado com sucesso!");
        } catch (NoSuchElementException e) {
            System.out.println("Serviço não encontrado.");
        } catch (IllegalStateException e) {
            System.out.println("Não é possível arquivar esse serviço.");
        }
    }

    private static void historicoServico(GerenciadorServicos gs) {
        System.out.println("======== HISTÓRICO DE SERVIÇOS ========");
        List<Servico> resultado = gs.buscarPorArquivamento(true);
        if (resultado.isEmpty()) {
            System.out.println("Histórico vazio...");
        } else {
            resultado.forEach(s -> System.out.println("ID: " + s.getId()
                    + " | Serviço: " + s.getNome()
                    + " | Valor: R$" + s.getValor()
                    + " | Categoria: " + s.getCategoria()
                    + " | Status: " + s.getStatus()));
        }
    }
}
