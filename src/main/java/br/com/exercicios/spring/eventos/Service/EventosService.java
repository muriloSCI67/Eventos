package br.com.exercicios.spring.eventos.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosService {
    @Autowired
    private EventosRepository eventosRepository;

    public EventosResponseDTO save(EventosRequestDTO dto){
        Eventos eventos = eventosRepository.save(EventosRequestDTO.dtoToEntity(dto));
        return EventosResponseDTO.entityToDto(eventos);
    }
}
