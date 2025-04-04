package br.com.postechfiap.fiap_produto_service.usecase;


import br.com.postechfiap.fiap_produto_service.dto.ListaProdutoResponse;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.BuscarProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarProdutoUseCaseImpl implements BuscarProdutoUseCase {

    private final ProdutoRepository produtoRepository;

    @Override
    public ListaProdutoResponse execute(String query) {

        List<ProdutoResponse> produtos = produtoRepository.findByNomeContainingIgnoreCaseOrSkuIgnoreCase(query, query)
                .stream()
                .map(produto -> new ProdutoResponse(produto.getId(),
                        produto.getNome(),
                        produto.getSku(),
                        produto.getPreco()))
                .collect(Collectors.toList());

        return new ListaProdutoResponse(produtos);
    }
}




