package com.bodegami.cadastro.gateway.impl;

import com.bodegami.cadastro.domain.Endereco;
import com.bodegami.cadastro.gateway.ViacepGateway;
import com.bodegami.cadastro.gateway.client.ViacepClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ViacepGatewayImpl implements ViacepGateway {

    @Autowired
    private ViacepClient client;

    @Override
    public Endereco getEndereco(String cep) {

        try {

            ResponseEntity<Endereco> response = client.getEnderecoByCep(cep);

            if (response.getBody() == null) {
                throw new IllegalArgumentException("Cep inválido!");
            }

            return response.getBody();
        }
        catch (Exception e) {
            throw new InternalError(e.getMessage());
        }

    }
}
