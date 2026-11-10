-- DryGest - estrutura inicial do banco de dados PostgreSQL
-- Execute este script no banco de dados "drygest".
-- Os valores de categoria e status são armazenados como texto,
-- compatível com os enums Categoria e Status do projeto.

CREATE TABLE IF NOT EXISTS servicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor DOUBLE PRECISION NOT NULL CHECK (valor >= 0),
    categoria VARCHAR(30) NOT NULL
        CHECK (categoria IN ('FORRO', 'PAREDE', 'SANCA', 'LINEAR')),
    status VARCHAR(30) NOT NULL DEFAULT 'PENDENTE'
        CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO')),
    largura DOUBLE PRECISION NOT NULL CHECK (largura > 0),
    comprimento DOUBLE PRECISION NOT NULL CHECK (comprimento > 0),
    arquivado BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT ck_servico_arquivamento_status CHECK (
        NOT arquivado OR status IN ('CONCLUIDO', 'CANCELADO')
    )
);
