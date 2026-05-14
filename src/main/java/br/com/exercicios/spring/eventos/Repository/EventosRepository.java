package br.com.exercicios.spring.eventos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EventosRepository extends JpaRepository< Eventos, Long >{

}
