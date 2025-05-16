package br.com.postechfiap.fiap_produto_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProdutoResponse(
        Long id,
        String nome,
        String sku,
        BigDecimal preco
) {}