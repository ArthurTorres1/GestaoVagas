package br.com.arthur.gestao_vagas.modules.candidate.controllers;

import br.com.arthur.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.arthur.gestao_vagas.modules.candidate.services.AuthCandidateService;
import br.com.arthur.gestao_vagas.modules.company.services.AuthCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateService authCandidateService;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO requestDTO){
        try{
            var token = this.authCandidateService.execute(requestDTO);
            return ResponseEntity.ok().body(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
