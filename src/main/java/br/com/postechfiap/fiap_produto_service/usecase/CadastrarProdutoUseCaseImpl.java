package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.CadastrarProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarProdutoUseCaseImpl implements CadastrarProdutoUseCase {

    private final ProdutoRepository produtoRepository;


    @Override
    public ProdutoResponse execute(ProdutoRequest entry) {

        // Cria a Entidade Produto
        var produto = Produto.builder()
                .nome(entry.nome())
                .preco(entry.preco())
                .build();

        // Persiste a Entidade no BD
        produto = produtoRepository.save(produto);

        // Devolve um Produto Response
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getPreco());
    }
}
