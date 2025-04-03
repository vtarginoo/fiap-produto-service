package br.com.postechfiap.fiap_produto_service.usecase;

import br.com.postechfiap.fiap_produto_service.interfaces.ProdutoRepository;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.DeletarProdutoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarProdutoUseCaseImpl implements DeletarProdutoUseCase {

    private final ProdutoRepository produtoRepository;


    @Override
    public String execute(Long id) {
        var produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoRepository.delete(produto);
        return "Produto com ID " + id + " foi deletado com sucesso!";
    }
}
