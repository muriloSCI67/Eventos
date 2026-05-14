package br.com.exercicios.spring.eventos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    }
