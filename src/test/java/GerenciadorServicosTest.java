import br.com.drygest.Categoria;
import br.com.drygest.GerenciadorServicos;
import br.com.drygest.Servico;
import br.com.drygest.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorServicosTest {

    private GerenciadorServicos gs;

    @BeforeEach
    void preparar() {
        gs = new GerenciadorServicos();
    }

    @Test
    void deveRetornarZero() {
        assertEquals(
                0.00,
                gs.somaValoresPorCategoria(Categoria.SANCA));
    }

    @Test
    void deveRejeitarCategoriaNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> gs.somaValoresPorCategoria(null));
    }

    @Test
    void deveSomarPorCategoria() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        Servico servico2 = new Servico("Parede de Drywall",
                2000,
                Categoria.PAREDE,
                3,
                4);
        Servico servico3 = new Servico("Forro de Cozinha",
                800,
                Categoria.FORRO,
                2,
                3);
        gs.cadastrarServico(servico1);
        gs.cadastrarServico(servico2);
        gs.cadastrarServico(servico3);
        assertEquals(
                2300.0,
                gs.somaValoresPorCategoria(Categoria.FORRO));
    }

    @Test
    void relatorioDinamico() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5
        );
        gs.cadastrarServico(servico1);
        assertEquals(
                2000,
                gs.somaValoresPorStatus(Status.PENDENTE)
        );
        assertEquals(
                0.00,
                gs.somaValoresPorStatus(Status.EM_ANDAMENTO)
        );
        servico1.iniciarServico();
        assertEquals(
                0.00,
                gs.somaValoresPorStatus(Status.PENDENTE)
        );
        assertEquals(
                2000.00,
                gs.somaValoresPorStatus(Status.EM_ANDAMENTO)
        );

    }

    @Test
    void rejeitarOMesmoObjeto() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        assertThrows(
                IllegalArgumentException.class,
                () -> gs.cadastrarServico(servico1)
        );
        assertEquals(
                1,
                gs.quantidadeServicos()
        );
    }

    @Test
    void verificarObjetosParecidos() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5
        );
        Servico servico2 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5
        );
        gs.cadastrarServico(servico1);
        gs.cadastrarServico(servico2);
        assertEquals(
                2,
                gs.quantidadeServicos()
        );
    }

    @Test
    void verificarIds() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5
        );
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Forro da Garagem",
                1800,
                Categoria.FORRO,
                6,
                6
        );
        gs.cadastrarServico(servico2);
        Servico servico3 = new Servico(
                "Parede do Quarto",
                1400,
                Categoria.PAREDE,
                4,
                2.80
        );
        gs.cadastrarServico(servico3);
        assertEquals(
                1,
                servico1.getId()
        );
        assertEquals(
                2,
                servico2.getId()
        );
        assertEquals(
                3,
                servico3.getId()
        );
    }

    @Test
    void verificarCadastroDuplicado() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5
        );
        gs.cadastrarServico(servico1);
        assertThrows(
                IllegalArgumentException.class,
                () -> gs.cadastrarServico(servico1)
        );
        Servico servico2 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5
        );
        gs.cadastrarServico(servico2);
        assertEquals(
                2,
                servico2.getId()
        );
        assertEquals(
                2,
                gs.quantidadeServicos()
        );
    }

    @Test
    void buscarIdExistente() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        assertSame(
                servico1,
                gs.buscarServicoPorId(1)
        );
    }

    @Test
    void buscarIdInvalido() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        assertThrows(
                IllegalArgumentException.class,
                () -> gs.buscarServicoPorId(-5)
        );
    }

    @Test
    void buscarIdInexistente() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        assertThrows(
                NoSuchElementException.class,
                () -> gs.buscarServicoPorId(2)
        );
    }

    @Test
    void deveIniciarServicoEncontradoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        Servico encontrado = gs.buscarServicoPorId(2);
        encontrado.iniciarServico();
        assertEquals(
                Status.EM_ANDAMENTO,
                servico2.getStatus()
        );
        assertEquals(
                Status.PENDENTE,
                servico1.getStatus()
        );
    }

    @Test
    void deveIniciarServicoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        gs.iniciarServicoPorId(2);
        assertSame(
                Status.EM_ANDAMENTO,
                servico2.getStatus()
        );
        assertSame(
                Status.PENDENTE,
                servico1.getStatus()
        );
    }

    @Test
    void deveConcluirServicoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        gs.iniciarServicoPorId(2);
        gs.concluirServicoPorId(2);
        assertSame(
                Status.CONCLUIDO,
                servico2.getStatus()
        );
        assertSame(
                Status.PENDENTE,
                servico1.getStatus()
        );
    }

    @Test
    void deveCancelarServicoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        gs.iniciarServicoPorId(2);
        gs.cancelarServicoPorId(2);
        assertSame(
                Status.CANCELADO,
                servico2.getStatus()
        );
        assertSame(
                Status.PENDENTE,
                servico1.getStatus()
        );
    }

    @Test
    void naoCancelarServicoConcluidoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        gs.iniciarServicoPorId(2);
        gs.concluirServicoPorId(2);
        assertThrows(
                IllegalStateException.class,
                () -> gs.cancelarServicoPorId(2)
        );
        assertEquals(
                Status.CONCLUIDO,
                servico2.getStatus()
        );
    }
    @Test
    void verificarBuscarPorStatusComElementos() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        Servico servico3 = new Servico(
                "Forro da Varanda",
                1000,
                Categoria.FORRO,
                2,
                2);
        gs.cadastrarServico(servico3);
        gs.iniciarServicoPorId(2);
        gs.iniciarServicoPorId(3);
        List<Servico> servicosEM_ANDAMENTO = gs.buscarPorStatus(Status.EM_ANDAMENTO);
        assertEquals(
                true,
                servicosEM_ANDAMENTO.contains(servico2)
        );
        assertEquals(
                true,
                servicosEM_ANDAMENTO.contains(servico3)
        );
        assertEquals(
                2,
                servicosEM_ANDAMENTO.size()
        );
    }
    @Test
    void verificarBuscarPorStatusSemElementos() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Parede da Cozinha",
                1200,
                Categoria.PAREDE,
                3,
                2.80);
        gs.cadastrarServico(servico2);
        Servico servico3 = new Servico(
                "Forro da Varanda",
                1000,
                Categoria.FORRO,
                2,
                2);
        gs.cadastrarServico(servico3);
        List<Servico> servicosCONCLUIDO = gs.buscarPorStatus(Status.CONCLUIDO);
        assertEquals(
                true,
                servicosCONCLUIDO.isEmpty()
        );
    }
    @Test
    void verificarBuscarPorStatusNull() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        assertThrows(
                IllegalArgumentException.class,
                () -> gs.buscarPorStatus(null)
        );
    }
    @Test
    void deveArquivarServicoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        servico1.iniciarServico();
        Servico servico2 = new Servico(
                "Forro de Varanda",
                1200,
                Categoria.FORRO,
                3,
                3);
        gs.cadastrarServico(servico2);
        servico2.iniciarServico();
        servico1.concluirServico();
        gs.arquivarServicoPorId(1);
        assertEquals(
                true,
                servico1.isArquivado()
        );
        assertEquals(
                false,
                servico2.isArquivado()
        );
    }
    @Test
    void naoDeveArquivarServicoEmAndamentoPorId() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        servico1.iniciarServico();
        Servico servico2 = new Servico(
                "Forro de Varanda",
                1200,
                Categoria.FORRO,
                3,
                3);
        gs.cadastrarServico(servico2);
        servico2.iniciarServico();
        servico1.concluirServico();
        assertThrows(
                IllegalStateException.class,
                () -> gs.arquivarServicoPorId(2)
        );
        assertEquals(
                false,
                servico1.isArquivado()
        );
        assertEquals(
                false,
                servico2.isArquivado()
        );
    }
    @Test
    void naoDeveArquivarServicoComIdInexistente() {
        assertThrows(
                NoSuchElementException.class,
                () -> gs.arquivarServicoPorId(4)
        );
    }
    @Test
    void deveBuscarServicosNaoArquivados() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        servico1.iniciarServico();
        Servico servico2 = new Servico(
                "Forro de Varanda",
                1200,
                Categoria.FORRO,
                3,
                3);
        gs.cadastrarServico(servico2);
        servico2.iniciarServico();
        servico2.concluirServico();
        gs.arquivarServicoPorId(2);
        gs.buscarServicosNaoArquivados();
        List<Servico> servicosNaoArquivados = gs.buscarServicosNaoArquivados();
        assertTrue(
                servicosNaoArquivados.contains(servico1)
        );
        assertEquals(
                1,
                servicosNaoArquivados.size()
        );
        assertFalse(
                servicosNaoArquivados.contains(servico2)
        );
    }
}
