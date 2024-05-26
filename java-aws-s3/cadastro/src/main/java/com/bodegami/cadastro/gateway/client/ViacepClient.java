package com.bodegami.cadastro.gateway.client;

import com.bodegami.cadastro.domain.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-viacep", url = "https://viacep.com.br/ws")
public interface ViacepClient {

    @GetMapping("/{cep}/json")
    ResponseEntity<Endereco> getEnderecoByCep(@PathVariable("cep") String cep);

}
