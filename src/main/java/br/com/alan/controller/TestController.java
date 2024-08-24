package br.com.alan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public String getMethodName() {
        return new String("Teste de rota autenticada para o membro");
    }

}
