package br.com.drygest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicoTest {
    @Test
    void naoDeveConcluirServicoPendente() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);

        assertThrows(
                IllegalStateException.class,
                () -> servico1.concluirServico()
        );
    }
    @Test
    void naoDeveConcluirServicoJaConcluido() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();
        servico1.concluirServico();

        assertThrows(
                IllegalStateException.class,
                () -> servico1.concluirServico()
        );
    }
    @Test
    void deveConcluirServicoEmAndamento() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();
        servico1.concluirServico();

        assertEquals(
                Status.CONCLUIDO,
                servico1.getStatus()
        );
    }
    @Test
    void verificarInicializacao() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();

        assertEquals(
                Status.EM_ANDAMENTO,
                servico1.getStatus()
        );
    }
    @Test
    void naoIniciarEmAndamento() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();

        assertThrows(
                IllegalStateException.class,
                () -> servico1.iniciarServico()
        );
        assertEquals(
                Status.EM_ANDAMENTO,
                servico1.getStatus()
        );
    }
    @Test
    void cancelarStatusPendente() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.cancelarServico();

        assertEquals(
                Status.CANCELADO,
                servico1.getStatus()
        );
    }
    @Test
    void cancelarStatusEmAndamento() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();
        servico1.cancelarServico();

        assertEquals(
                Status.CANCELADO,
                servico1.getStatus()
        );
    }
    @Test
    void cancelarStatusConcluido() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();
        servico1.concluirServico();

        assertThrows(
                IllegalStateException.class,
                () -> servico1.cancelarServico()
        );
        assertEquals(
                Status.CONCLUIDO,
                servico1.getStatus()
        );
    }
    @Test
    void cancelarStatusCancelado() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        servico1.iniciarServico();
        servico1.cancelarServico();

        assertThrows(
                IllegalStateException.class,
                () -> servico1.cancelarServico()
        );
        assertEquals(
                Status.CANCELADO,
                servico1.getStatus()
        );
    }
    @Test
    void testarCicloDeStatus() {
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        assertEquals(
                Status.PENDENTE,
                servico1.getStatus()
        );

        servico1.iniciarServico();
        assertEquals(
                Status.EM_ANDAMENTO,
                servico1.getStatus()
        );

        servico1.concluirServico();
        assertEquals(
                Status.CONCLUIDO,
                servico1.getStatus()
        );

        assertThrows(
                IllegalStateException.class,
                () -> servico1.cancelarServico()
        );
        assertEquals(
                Status.CONCLUIDO,
                servico1.getStatus()
        );
    }
    @Test
    void verificarAlteracaoId() {
        GerenciadorServicos gs = new GerenciadorServicos();
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        assertThrows(
                IllegalStateException.class,
                () -> servico1.atribuirId(4)
        );
        assertEquals(
                1,
                servico1.getId()
        );
    }
}
