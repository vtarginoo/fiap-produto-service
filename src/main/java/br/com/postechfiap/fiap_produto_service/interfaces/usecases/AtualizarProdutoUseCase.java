package br.com.postechfiap.fiap_produto_service.interfaces.usecases;

import br.com.postechfiap.fiap_produto_service.dto.AtualizarProdutoDTO;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.interfaces.UseCase;

public interface AtualizarProdutoUseCase extends UseCase<AtualizarProdutoDTO, ProdutoResponse> {
}
