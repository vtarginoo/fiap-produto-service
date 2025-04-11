package br.com.postechfiap.fiap_produto_service.dto.estoque;

public record EstoqueResponse(
        Long id,
        String nome,
        String sku,
        Long quantidade
) {
}
