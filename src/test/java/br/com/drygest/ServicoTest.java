package br.com.drygest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServicoTest {

    private Servico servico;

    @BeforeEach
    void preparar() {
        servico = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5
        );
    }

    @Test
    void naoDeveConcluirServicoPendente() {
        assertThrows(
                IllegalStateException.class,
                () -> servico.concluirServico()
        );
    }
    @Test
    void naoDeveConcluirServicoJaConcluido() {
        servico.iniciarServico();
        servico.concluirServico();

        assertThrows(
                IllegalStateException.class,
                () -> servico.concluirServico()
        );
    }
    @Test
    void deveConcluirServicoEmAndamento() {
        servico.iniciarServico();
        servico.concluirServico();

        assertEquals(
                Status.CONCLUIDO,
                servico.getStatus()
        );
    }
    @Test
    void verificarInicializacao() {
        servico.iniciarServico();

        assertEquals(
                Status.EM_ANDAMENTO,
                servico.getStatus()
        );
    }
    @Test
    void naoIniciarEmAndamento() {
        servico.iniciarServico();

        assertThrows(
                IllegalStateException.class,
                () -> servico.iniciarServico()
        );
        assertEquals(
                Status.EM_ANDAMENTO,
                servico.getStatus()
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
    @Test
    void deveArquivarServicoConcluido() {
        servico.iniciarServico();
        servico.concluirServico();
        servico.arquivarServico();
        assertEquals(
                true,
                servico.isArquivado()
        );
    }
    @Test
    void deveArquivarServicoCancelado() {
        servico.iniciarServico();
        servico.cancelarServico();
        servico.arquivarServico();
        assertEquals(
                true,
                servico.isArquivado()
        );
    }
    @Test
    void naoDeveArquivarServicoPendente() {
        assertThrows(
                IllegalStateException.class,
                () -> servico.arquivarServico()
        );
        assertEquals(
                false,
                servico.isArquivado()
        );
    }
    @Test
    void naoDeveArquivarServicoDuasVezes() {
        servico.iniciarServico();
        servico.cancelarServico();
        servico.arquivarServico();
        assertThrows(
                IllegalStateException.class,
                () -> servico.arquivarServico()
        );
        assertEquals(
                true,
                servico.isArquivado()
        );
    }
}
