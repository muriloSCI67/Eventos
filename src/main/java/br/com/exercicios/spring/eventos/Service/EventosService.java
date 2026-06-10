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

    public Evento editarEvento(Long id, Evento eventoAtualizado) {
        Optional<Evento> cripto = eventosRepository.findById(id);
        if (cripto.isPresent()) {
            Evento eventoExistente = cripto.get();

            eventoExistente.setTitulo(eventoAtualizado.getTitulo() != null ?
                    eventoAtualizado.getTitulo() : eventoExistente.getTitulo());

            eventoExistente.setPalestrante(eventoAtualizado.getPalestrante() != null ?
                    eventoAtualizado.getPalestrante() : eventoExistente.getPalestrante());

            eventoExistente.setDescricao(eventoAtualizado.getDescricao() != null ?
                    eventoAtualizado.getDescricao() : eventoExistente.getDescricao());

            eventoExistente.setEmailContato(eventoAtualizado.getEmailContato() != null ?
                    eventoAtualizado.getEmailContato() : eventoExistente.getEmailContato());

            eventoExistente.setCargaHoraria(eventoAtualizado.getCargaHoraria() != null ?
                    eventoAtualizado.getCargaHoraria() : eventoExistente.getCargaHoraria());

            eventoExistente.setDataEvento(eventoAtualizado.getDataEvento() != null ?
                    eventoAtualizado.getDataEvento() : eventoExistente.getDataEvento());

            eventoExistente.setQuantidadeVagas(eventoAtualizado.getQuantidadeVagas() != null ?
                    eventoAtualizado.getQuantidadeVagas() : eventoExistente.getQuantidadeVagas());

            eventoExistente.setValorInscricao(eventoAtualizado.getValorInscricao() != null ?
                    eventoAtualizado.getValorInscricao() : eventoExistente.getValorInscricao());

            eventoExistente.setStatus(eventoAtualizado.getStatus() != null ?
                    eventoAtualizado.getStatus() : eventoExistente.getStatus());

            return eventosRepository.save(eventoExistente);
        }
        throw new RuntimeException("Cripto nao encontrada para o id: " + id);
    }

    public void delete(Long id) {
        if (!eventosRepository.existsById(id)) {
            throw new MinhaException("Evento não encontrado para o id: " + id);
        }
        eventosRepository.deleteById(id);
    }

}
