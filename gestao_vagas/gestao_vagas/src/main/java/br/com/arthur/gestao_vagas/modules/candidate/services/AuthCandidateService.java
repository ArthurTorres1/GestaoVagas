package br.com.arthur.gestao_vagas.modules.candidate.services;

import br.com.arthur.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.arthur.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.arthur.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateService {

    @Value("${security.token.secret.candidate}")
    private String secret;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(@RequestBody AuthCandidateRequestDTO requestDTO) throws AuthenticationException{
        var candidate = this.candidateRepository.findByUsername(requestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorretct");
        });

        var passwordMatches = this.passwordEncoder.matches(requestDTO.password(), candidate.getPassword());
        if(!passwordMatches){
            throw new AuthenticationException("");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresToken = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withExpiresAt(expiresToken)
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .token(token)
                .expiresToken(expiresToken.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
