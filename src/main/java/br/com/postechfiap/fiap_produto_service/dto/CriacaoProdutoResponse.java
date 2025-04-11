package br.com.postechfiap.fiap_produto_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CriacaoProdutoResponse(
        Long id,
        String nome,
        String sku,
        Double preco,
        boolean estoqueCriado,
        String mensagemEstoque // mensagem amigável, sucesso ou erro
) {}