package br.com.arthur.gestao_vagas.modules.company.dto;

import lombok.Builder;

@Builder
public record AuthCompanyResponseDTO(String token, Long expiresToken) {
}
