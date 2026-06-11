package br.com.exercicios.spring.eventos.Service;

import br.com.exercicios.spring.eventos.Entity.Evento;
import br.com.exercicios.spring.eventos.Repository.EventosRepository;
import br.com.exercicios.spring.eventos.dtos.EventosRequestDTO;
import br.com.exercicios.spring.eventos.dtos.EventosResponseDTO;
import br.com.exercicios.spring.eventos.handlers.MinhaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventosService {
    @Autowired
    private EventosRepository eventosRepository;


    public List<Evento> listarTodosEventos() {
        return eventosRepository.findAll();
    }

    public EventosResponseDTO findById(Long id) {
        Evento evento = eventosRepository.findById(id)
                .orElseThrow(() -> new MinhaException("Evento não encontrado para o Id: " + id));
        return EventosResponseDTO.fromEntity(evento);
    }

    public EventosResponseDTO save(EventosRequestDTO dto) {
        Evento evento = Evento.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .palestrante(dto.getPalestrante())
                .emailContato(dto.getEmailContato())
                .cargaHoraria(dto.getCargaHoraria())
                .dataEvento(dto.getDataEvento())
                .quantidadeVagas(dto.getQuantidadeVagas())
                .valorInscricao(dto.getValorInscricao())
                .status(dto.getStatus())
                .build();
        return EventosResponseDTO.fromEntity(eventosRepository.save(evento));
    }

    public List<EventosResponseDTO> findAll() {
        return eventosRepository.findAll().stream()
                .map(EventosResponseDTO::fromEntity)
                .toList();
    }

    public EventosResponseDTO editarEvento(Long id, EventosRequestDTO dto) {
        Evento eventoExistente = eventosRepository.findById(id)
                .orElseThrow(() -> new MinhaException("Evento não encontrado para o id: " + id));

        eventoExistente.setTitulo(dto.getTitulo() != null ?
                dto.getTitulo() : eventoExistente.getTitulo());

        eventoExistente.setPalestrante(dto.getPalestrante() != null ?
                dto.getPalestrante() : eventoExistente.getPalestrante());

        eventoExistente.setDescricao(dto.getDescricao() != null ?
                dto.getDescricao() : eventoExistente.getDescricao());

        eventoExistente.setEmailContato(dto.getEmailContato() != null ?
                dto.getEmailContato() : eventoExistente.getEmailContato());

        eventoExistente.setCargaHoraria(dto.getCargaHoraria() != null ?
                dto.getCargaHoraria() : eventoExistente.getCargaHoraria());

        eventoExistente.setDataEvento(dto.getDataEvento() != null ?
                dto.getDataEvento() : eventoExistente.getDataEvento());

        eventoExistente.setQuantidadeVagas(dto.getQuantidadeVagas() != null ?
                dto.getQuantidadeVagas() : eventoExistente.getQuantidadeVagas());

        eventoExistente.setValorInscricao(dto.getValorInscricao() != null ?
                dto.getValorInscricao() : eventoExistente.getValorInscricao());

        eventoExistente.setStatus(dto.getStatus() != null ?
                dto.getStatus() : eventoExistente.getStatus());

        return EventosResponseDTO.fromEntity(eventosRepository.save(eventoExistente));
    }

    public void delete(Long id) {
        if (!eventosRepository.existsById(id)) {
            throw new MinhaException("Evento não encontrado para o id: " + id);
        }
        eventosRepository.deleteById(id);
    }

}
