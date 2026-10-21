package br.com.drygest;

public class Servico {
    private int id;
    private String nome;
    private double valor;
    private Categoria categoria;
    private Status status;
    private double largura;
    private double comprimento;

    public Servico(String nome, double valor, Categoria categoria, double largura, double comprimento) {
        setNome(nome);
        if (valor < 0) {
            throw new IllegalArgumentException("Digite um valor válido.");
        }
        this.valor = valor;
        if (categoria == null) {
            throw new IllegalArgumentException("Digite uma categoria válida.");
        }
        this.categoria = categoria;
        this.status = Status.PENDENTE;
        if (largura <= 0) {
            throw new IllegalArgumentException("Digite uma largura válida.");
        }
        this.largura = largura;
        if (comprimento <= 0) {
            throw new IllegalArgumentException("Digite um comprimento válido.");
        }
        this.comprimento = comprimento;
    }

    void atribuirId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido, tente novamente.");
        } if (this.id != 0) {
            throw new IllegalStateException("O serviço já possui um id.");
        }
        this.id = id;
    }

    public void iniciarServico() {
        if (this.status == Status.PENDENTE) {
            this.status = Status.EM_ANDAMENTO;
        } else {
            throw new IllegalStateException(
                    "Não é possível iniciar esse serviço."
            );
        }
    }

    public void concluirServico() {
        if (this.status == Status.EM_ANDAMENTO) {
            this.status = Status.CONCLUIDO;
        } else {
            throw new IllegalStateException(
                    "Não é possível concluir esse serviço."
            );
        }
    }

    public void cancelarServico() {
        if (this.status == Status.PENDENTE || this.status == Status.EM_ANDAMENTO) {
            this.status = Status.CANCELADO;
        } else {
            throw new IllegalStateException(
                    "Não é possível cancelar esse serviço."
            );
        }
    }

    public double calcularArea() {
        return this.largura * this.comprimento;
    }

    public double calcularMetroSanca() {
        return this.largura + this.comprimento + this.largura + this.comprimento;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Digite um nome válido.");
        }
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Status getStatus() {
        return status;
    }

    public double getLargura() {
        return largura;
    }

    public double getComprimento() {
        return comprimento;
    }

}
