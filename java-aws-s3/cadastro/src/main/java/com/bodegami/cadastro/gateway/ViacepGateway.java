package com.bodegami.cadastro.gateway;


import com.bodegami.cadastro.domain.Endereco;

public interface ViacepGateway {

    Endereco getEndereco(String cep);

}
