package br.com.arthur.gestao_vagas.modules.company.services;

import br.com.arthur.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.arthur.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.arthur.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyService {

    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDto) throws AuthenticationException {
        var company = companyRepository.findByUsername(authCompanyDto.username()).orElseThrow(
                () -> new UsernameNotFoundException("Username/Password incorrect"));

        var passwordMatches = passwordEncoder.matches(authCompanyDto.password(), company.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException();
        }
//      gerando token JWT
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var expiresToken = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresToken)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .token(token)
                .expiresToken(expiresToken.toEpochMilli())
                .build();
        return authCompanyResponseDTO;
    }
}
