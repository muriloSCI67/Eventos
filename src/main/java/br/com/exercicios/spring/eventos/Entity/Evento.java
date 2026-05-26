package br.com.exercicios.spring.eventos.Entity;

import br.com.exercicios.spring.eventos.Entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
    private Status status;
    private LocalDate dataCadastro;
    private String codigoInterno;

    @PrePersist
    public void prePersist(){
        if (dataCadastro == null) {
            dataCadastro = LocalDate.now();
        }
        if (codigoInterno == null || codigoInterno.isBlank()) {
            codigoInterno = UUID.randomUUID().toString();
        }
    }

}
