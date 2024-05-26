package com.example.demo.gateway;

import com.example.demo.domain.Endereco;

public interface ViacepGateway {

    Endereco getEndereco(String cep);

}
