package br.com.exercicios.spring.eventos.dtos;

import br.com.exercicios.spring.eventos.Entity.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import br.com.exercicios.spring.eventos.Entity.Evento;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor

public class EventosRequestDTO {
    @NotBlank(message = "Precisa ter um título")
    private String titulo;

    @NotBlank(message = "Precisa ter descrição")
    private String descricao;

    @NotBlank(message = "O palestrante deve ser mencionado")
    private String palestrante;

    @Email(message = "O email deve ser válido")
    private String emailContato;

    @Min(value = 1, message = "A carga horária mínima é 1")
    private Integer cargaHoraria;

    @FutureOrPresent(message = "Deve ser uma data válida")
    private LocalDate dataEvento;

    @Positive(message = "O número deve ser positivo")
    private Integer quantidadeVagas;

    @DecimalMin(value = "0.0", message = "Valor da inscrição precisa ser maior que 0")
    private BigDecimal valorInscricao;

    @NotNull(message = "O status não pode ser nulo")
    private Status status;

    public static Evento dtoToEntity(EventosRequestDTO eventosRequestDTO) {
        return Evento.builder()
                .titulo(eventosRequestDTO.titulo)
                .descricao(eventosRequestDTO.descricao)
                .palestrante(eventosRequestDTO.palestrante)
                .emailContato(eventosRequestDTO.emailContato)
                .cargaHoraria(eventosRequestDTO.cargaHoraria)
                .dataEvento(eventosRequestDTO.dataEvento)
                .quantidadeVagas(eventosRequestDTO.quantidadeVagas)
                .valorInscricao(eventosRequestDTO.valorInscricao)
                .dataCadastro(LocalDate.now())
                .status(eventosRequestDTO.status)
                .codigoInterno(java.util.UUID.randomUUID().toString()).build();

    }
    }
