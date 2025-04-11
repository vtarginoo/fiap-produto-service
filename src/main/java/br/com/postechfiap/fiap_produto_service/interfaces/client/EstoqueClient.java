package br.com.postechfiap.fiap_produto_service.interfaces.client;

import br.com.postechfiap.fiap_produto_service.dto.estoque.EstoqueRequest;
import br.com.postechfiap.fiap_produto_service.dto.estoque.EstoqueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "estoqueClient", url = "http://localhost:8082")
public interface EstoqueClient {

    @PostMapping("/estoque")
    EstoqueResponse cadastrarEstoque(@RequestBody EstoqueRequest request);

    }


