package br.com.arthur.gestao_vagas.modules.company.controllers;

import br.com.arthur.gestao_vagas.exceptions.UserFoundException;
import br.com.arthur.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.arthur.gestao_vagas.modules.company.services.CreateCompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CreateCompanyService createCompanyService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity company){
        try{
            var result = this.createCompanyService.execute(company);
            return ResponseEntity.ok().body(result);
        }catch (UserFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
