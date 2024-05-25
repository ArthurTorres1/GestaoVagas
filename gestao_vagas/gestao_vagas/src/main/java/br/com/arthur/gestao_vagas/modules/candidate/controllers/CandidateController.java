package br.com.arthur.gestao_vagas.modules.candidate.controllers;

import br.com.arthur.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.arthur.gestao_vagas.modules.candidate.services.CreateCandidateService;
import br.com.arthur.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    CreateCandidateService createCandidateService;

    @Autowired
    ProfileCandidateService profileCandidateService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate){
        try{
            var result = this.createCandidateService.execute(candidate);
            return ResponseEntity.ok().body(result);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> get(HttpServletRequest requestHttp){
        var idCandidate = requestHttp.getAttribute("candidate_id");
        try{
            var profile = this.profileCandidateService.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
