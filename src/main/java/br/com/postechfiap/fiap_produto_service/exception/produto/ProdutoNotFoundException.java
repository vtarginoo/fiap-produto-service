package br.com.postechfiap.fiap_produto_service.exception.produto;

import br.com.postechfiap.fiap_produto_service.exception.EntityNotFoundException;

public class ProdutoNotFoundException extends EntityNotFoundException {

    public ProdutoNotFoundException() {super("Produto", "o");}

}
