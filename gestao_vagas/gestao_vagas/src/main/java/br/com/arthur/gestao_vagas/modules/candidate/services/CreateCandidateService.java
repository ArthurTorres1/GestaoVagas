package br.com.arthur.gestao_vagas.modules.candidate.services;

import br.com.arthur.gestao_vagas.exceptions.UserFoundException;
import br.com.arthur.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.arthur.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidate){
        this.candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);
        return this.candidateRepository.save(candidate);
    }
}
