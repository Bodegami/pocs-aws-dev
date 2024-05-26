package com.example.demo.entrypoint;

import com.example.demo.domain.Endereco;
import com.example.demo.gateway.ViacepGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {


    private final ViacepGateway gateway;

    public EnderecoController(ViacepGateway gateway) {
        this.gateway = gateway;
    }

    @GetMapping
    public ResponseEntity<Endereco> getAddress(@RequestHeader(value = "cep") String cep,
                                               @RequestHeader(value = "format") String format) {

        System.out.printf("%s/%s\n", cep, format);

        Endereco endereco = gateway.getEndereco(cep);
        System.out.println(endereco);

        return ResponseEntity.ok(endereco);
    }


}
