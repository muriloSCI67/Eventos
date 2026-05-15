package br.com.exercicios.spring.eventos.Repository;

import br.com.exercicios.spring.eventos.Entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepository extends JpaRepository<Evento, Long > {

}
