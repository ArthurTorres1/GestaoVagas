package br.com.arthur.gestao_vagas.modules.candidate.dto;

import lombok.Builder;
import java.util.UUID;

@Builder
public record ProfileCandidateResponseDTO(String description, String email, String username, UUID id, String name){
}
