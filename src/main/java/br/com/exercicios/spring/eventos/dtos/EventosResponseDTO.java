package br.com.exercicios.spring.eventos.dtos;

import br.com.exercicios.spring.eventos.Entity.Evento;
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
    private String emailContato;
    private Integer cargaHoraria;
    private Integer quantidadeVagas;
    private LocalDate dataEvento;
    private BigDecimal valorInscricao;
    private Status status;
    private LocalDate dataCadastro;


    public static EventosResponseDTO fromEntity(Evento entity) {
        return EventosResponseDTO.builder()
                .titulo(entity.getTitulo())
                .descricao(entity.getDescricao())
                .palestrante(entity.getPalestrante())
                .emailContato(entity.getEmailContato())
                .cargaHoraria(entity.getCargaHoraria())
                .dataEvento(entity.getDataEvento())
                .quantidadeVagas(entity.getQuantidadeVagas())
                .valorInscricao(entity.getValorInscricao())
                .status(entity.getStatus())
                .id(entity.getId())
                .dataCadastro(entity.getDataCadastro())
                .build();


    }
}
