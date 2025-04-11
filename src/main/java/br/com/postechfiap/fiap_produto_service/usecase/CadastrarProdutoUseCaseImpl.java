package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.dto.CriacaoProdutoResponse;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoRequest;
import br.com.postechfiap.fiap_produto_service.dto.ProdutoResponse;
import br.com.postechfiap.fiap_produto_service.dto.estoque.EstoqueRequest;
import br.com.postechfiap.fiap_produto_service.entities.Produto;
import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import br.com.postechfiap.fiap_produto_service.interfaces.client.EstoqueClient;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.CadastrarProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarProdutoUseCaseImpl implements CadastrarProdutoUseCase {

    private final ProdutoRepository produtoRepository;
    private final EstoqueClient estoqueClient;
    boolean estoqueCriado;
    String mensagemEstoque;


    @Override
    public CriacaoProdutoResponse execute(ProdutoRequest entry) {

        // Cria a Entidade Produto
        var produto = Produto.builder()
                .nome(entry.nome())
                .preco(entry.preco())
                .build();

        // Persiste a Entidade no BD
        produto = produtoRepository.save(produto);

        // 2. Tenta cadastrar o estoque correspondente
        try {
            var estoqueRequest = new EstoqueRequest(produto.getNome(), produto.getSku(), 0L);
            estoqueClient.cadastrarEstoque(estoqueRequest);
            estoqueCriado = true;
            mensagemEstoque = "Estoque cadastrado com sucesso.";
        } catch (Exception e) {
            estoqueCriado = false;
            mensagemEstoque = "Erro ao cadastrar o estoque: " + e.getMessage();
        }



        // Devolve um Criacao Produto Response
        return new CriacaoProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getSku(),
                produto.getPreco(),
                estoqueCriado, mensagemEstoque);
    }
}
