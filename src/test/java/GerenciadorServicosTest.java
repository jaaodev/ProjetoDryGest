import br.com.drygest.Categoria;
import br.com.drygest.GerenciadorServicos;
import br.com.drygest.Servico;
import br.com.drygest.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GerenciadorServicosTest {
    @Test
    void deveRetornarZero() {
        GerenciadorServicos gs1 = new GerenciadorServicos();
        assertEquals(
                0.00,
                gs1.somaValoresPorCategoria(Categoria.SANCA));
    }

    @Test
    void deveRejeitarCategoriaNull() {
        GerenciadorServicos gs1 = new GerenciadorServicos();
        assertThrows(
                IllegalArgumentException.class,
                () -> gs1.somaValoresPorCategoria(null));
    }

    @Test
    void deveSomarPorCategoria() {
        GerenciadorServicos gs = new GerenciadorServicos();
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
        GerenciadorServicos gs = new GerenciadorServicos();
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
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
        GerenciadorServicos gs = new GerenciadorServicos();
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
        GerenciadorServicos gs = new GerenciadorServicos();
        Servico servico1 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        Servico servico2 = new Servico(
                "Forro de Sala",
                2000,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        gs.cadastrarServico(servico2);
        assertEquals(
                2,
                gs.quantidadeServicos()
        );
    }
    @Test
    void verificarIds() {
        GerenciadorServicos gs = new GerenciadorServicos();
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
        gs.cadastrarServico(servico1);
        Servico servico2 = new Servico(
                "Forro da Garagem",
                1800,
                Categoria.FORRO,
                6,
                6);
        gs.cadastrarServico(servico2);
        Servico servico3 = new Servico(
                "Parede do Quarto",
                1400,
                Categoria.PAREDE,
                4,
                2.80);
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
        GerenciadorServicos gs = new GerenciadorServicos();
        Servico servico1 = new Servico(
                "Forro de Sala",
                1500,
                Categoria.FORRO,
                4,
                5);
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
                5);
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
}
