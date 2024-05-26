package com.bodegami.cadastro.entrypoint;

import com.bodegami.cadastro.domain.Endereco;
import com.bodegami.cadastro.gateway.S3BucketGateway;
import com.bodegami.cadastro.gateway.ViacepGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {


    private final ViacepGateway gateway;
    private final S3BucketGateway s3Gateway;


    public EnderecoController(ViacepGateway gateway, S3BucketGateway s3Gateway) {
        this.gateway = gateway;
        this.s3Gateway = s3Gateway;
    }

    @GetMapping
    public ResponseEntity<Endereco> getAddress(@RequestHeader(value = "cep") String cep,
                                               @RequestHeader(value = "format") String format) {

        System.out.printf("%s/%s\n", cep, format);

        Endereco endereco = gateway.getEndereco(cep);
        s3Gateway.send(endereco, "endereco");
        System.out.println(endereco);

        return ResponseEntity.ok(endereco);
    }


}
