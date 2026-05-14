package br.com.exercicios.spring.eventos.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventosResponseDTO {
    private String nome;
    private String email;

    public static EventosResponseDTO entityToDto(Eventos eventos) {
        return EventosResponseDTO.builder()
                .nome(eventos.getNome())
                .email(eventos.getEmail())
                .build();
    }
}
