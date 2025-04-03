package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.dto.AtualizarProdutoDTO;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.exception.produto.ProdutoNotFoundException;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.AtualizarProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizarProdutoUseCaseImpl implements AtualizarProdutoUseCase {

    private final ProdutoRepository produtoRepository;


    @Override
    public ProdutoResponse execute(AtualizarProdutoDTO entry) {

        // Verifica se existe o produto no id procurado
        var produto = produtoRepository.findById(entry.id())
                .orElseThrow(ProdutoNotFoundException::new);

        produto.setNome(entry.produtoRequest().nome());
        produto.setPreco(entry.produtoRequest().preco());

        produtoRepository.save(produto);

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getPreco());
    }
}
