package br.com.postechfiap.fiap_produto_service.interfaces;

import br.com.postechfiap.fiap_produto_service.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    List<Produto> findByNomeContainingIgnoreCaseOrSkuIgnoreCase(String nome, String sku);
}

