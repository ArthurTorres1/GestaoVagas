package br.com.arthur.gestao_vagas.modules.candidate.dto;

import lombok.Builder;


@Builder
public record AuthCandidateResponseDTO (String token, Long expiresToken){
}
