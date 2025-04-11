package br.com.postechfiap.fiap_produto_service.controller;


import br.com.postechfiap.fiap_produto_service.dto.*;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.AtualizarProdutoUseCase;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.BuscarProdutoUseCase;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.CadastrarProdutoUseCase;
import br.com.postechfiap.fiap_produto_service.interfaces.usecases.DeletarProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/produto")
@RequiredArgsConstructor
@Validated
@Tag(name = "Produto", description = "API para gerenciar produtos")
public class ProdutoController {

    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;
    private final BuscarProdutoUseCase buscarProdutoUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;
    private final DeletarProdutoUseCase deletarProdutoUseCase;


    @PostMapping
    @Operation(summary = "Cadastrar Produto", description = "Cadastra novos produtos.")
    public ResponseEntity<CriacaoProdutoResponse> cadastrarNovoProduto (@RequestBody @Valid ProdutoRequest dto) {

        var novoProduto = cadastrarProdutoUseCase.execute(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoProduto.id())
                .toUri();

        return ResponseEntity.created(location).body(novoProduto);
    }

    @GetMapping
    @Operation(summary = "Buscar Produto", description = "Buscar produto por nome ou sku")
    public ResponseEntity<ListaProdutoResponse> buscarProduto (@RequestParam String query) {

        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro de busca não pode estar vazio.");
        }

        var listaProduto = buscarProdutoUseCase.execute(query);

        return ResponseEntity.ok(listaProduto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Produto", description = "Atualizar nome e/ou preço de um produto")
    public ResponseEntity<ProdutoResponse> atualizarProduto (@PathVariable Long id,
                                                                  @Valid @RequestBody ProdutoRequest dto) {

        var produto = atualizarProdutoUseCase.execute(new AtualizarProdutoDTO(id,dto));

        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Produto", description = "Remove um produto pelo ID e retorna uma mensagem de confirmação.")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
        String mensagem = deletarProdutoUseCase.execute(id);
        return ResponseEntity.ok(mensagem);
    }




}
