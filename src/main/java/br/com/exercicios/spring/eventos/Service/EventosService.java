package br.com.exercicios.spring.eventos.Service;

import br.com.exercicios.spring.eventos.Entity.Evento;
import br.com.exercicios.spring.eventos.Repository.EventosRepository;
import br.com.exercicios.spring.eventos.dtos.EventosRequestDTO;
import br.com.exercicios.spring.eventos.dtos.EventosResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosService {
    @Autowired
    private EventosRepository eventosRepository;

    public EventosResponseDTO save(EventosRequestDTO dto){
        Evento evento = eventosRepository.save(EventosRequestDTO.dtoToEntity(dto));
        return EventosResponseDTO.entityToDto(evento);
    }
}
