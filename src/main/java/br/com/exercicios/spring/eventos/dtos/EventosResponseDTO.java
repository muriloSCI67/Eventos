package br.com.exercicios.spring.eventos.dtos;

import br.com.exercicios.spring.eventos.Entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventosResponseDTO {
    private String titulo;
    private String descricao;
    private Long id;
    private String palestrante;
    private Integer cargaMorario;
    private Integer quantidadeVagas;
    private LocalDate dataEvento;
    private BigDecimal valorInscricao;
    private Status status;
    private LocalDate dataCadastro;



    public static EventosResponseDTO entityToDto(Eventos eventos) {
        return EventosResponseDTO.builder()
                .nome(eventos.getNome())
                .email(eventos.getEmail())
                .build();
    }
}
