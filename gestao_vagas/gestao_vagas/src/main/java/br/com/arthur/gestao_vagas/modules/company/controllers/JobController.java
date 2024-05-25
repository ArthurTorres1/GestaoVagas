package br.com.arthur.gestao_vagas.modules.company.controllers;

import br.com.arthur.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.arthur.gestao_vagas.modules.company.entities.JobEntity;
import br.com.arthur.gestao_vagas.modules.company.services.CreateJobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobService jobService;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public JobEntity create(@Valid @RequestBody CreateJobDTO requestDTO, HttpServletRequest request){
        var companyId = request.getAttribute("company_id");

       var jobEntity = JobEntity.builder()
                .companyId(UUID.fromString(companyId.toString()))
                .benefits(requestDTO.benefits())
                .description(requestDTO.description())
                .level(requestDTO.level())
                .build();
                
        return this.jobService.execute(jobEntity);
    }
}
