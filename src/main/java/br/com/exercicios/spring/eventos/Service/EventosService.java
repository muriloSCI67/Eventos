package br.com.exercicios.spring.eventos.Service;

import br.com.exercicios.spring.eventos.Entity.Evento;
import br.com.exercicios.spring.eventos.Repository.EventosRepository;
import br.com.exercicios.spring.eventos.dtos.EventosRequestDTO;
import br.com.exercicios.spring.eventos.dtos.EventosResponseDTO;
import br.com.exercicios.spring.eventos.handlers.MinhaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventosService {
    @Autowired
    private EventosRepository eventosRepository;


    public List<Evento> listarTodosEventos() {
        return eventosRepository.findAll();
    }

    public EventosResponseDTO findById(Long id){
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

    public List <EventosResponseDTO> findAll(){
        return eventosRepository.findAll().stream()
                .map(EventosResponseDTO::fromEntity)
                .toList();
    }

    public EventosResponseDTO update(Long id, EventosRequestDTO dto) {
        Evento evento = eventosRepository.findById(id)
                .orElseThrow(() -> new MinhaException("Evento não encontrado para o Id: " + id));

        evento.setTitulo(dto.getTitulo());
        evento.setDataEvento(dto.getDataEvento());
        evento.setStatus(dto.getStatus());
        evento.setEmailContato(dto.getEmailContato());
        evento.setPalestrante(dto.getPalestrante());
        evento.setDescricao(dto.getDescricao());
        evento.setQuantidadeVagas(dto.getQuantidadeVagas());
        evento.setValorInscricao(dto.getValorInscricao());
        evento.setCargaHoraria(dto.getCargaHoraria());

        return EventosResponseDTO.fromEntity(eventosRepository.save(evento));
    }

    public void delete (Long id) {
        if (!eventosRepository.existsById(id)){
            throw new MinhaException("Evento não encontrado para o id: " + id);
        }
        eventosRepository.deleteById(id);
    }

}
