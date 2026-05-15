package br.com.exercicios.spring.eventos.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String palestrante;
    private String descricao;
    private String emailContato;
    private Integer cargaHoraria;
    private LocalDate dataEvento;
    private Integer quantidadeVagas;
    private BigDecimal valorInscricao;
    private Enum Status;

}
