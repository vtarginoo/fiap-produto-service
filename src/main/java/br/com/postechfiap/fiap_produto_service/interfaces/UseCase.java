package br.com.postechfiap.fiap_produto_service.interfaces;

public interface UseCase<Input, Output> {

    Output execute(Input entry);
}
