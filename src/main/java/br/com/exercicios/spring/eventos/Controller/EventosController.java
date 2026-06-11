package br.com.exercicios.spring.eventos.Controller;

import br.com.exercicios.spring.eventos.Service.EventosService;
import br.com.exercicios.spring.eventos.dtos.EventosRequestDTO;
import br.com.exercicios.spring.eventos.dtos.EventosResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping({"/api/eventos", "/eventos"})

public class EventosController {
    @Autowired
    private EventosService eventosService;

    @PostMapping
    public EventosResponseDTO save(@RequestBody @Valid EventosRequestDTO eventosRequestDTO) {
        return eventosService.save(eventosRequestDTO);
    }

    @GetMapping("/{id}")
    public EventosResponseDTO findById(@PathVariable Long id) {
        return eventosService.findById(id);
    }

    @GetMapping
    public java.util.List<EventosResponseDTO> findAll() {
        return eventosService.findAll();
    }

    @PutMapping
    public EventosResponseDTO editarEvento(@RequestParam Long id, @RequestBody EventosRequestDTO eventosRequestDTO) {
        return eventosService.editarEvento(id, eventosRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventosService.delete(id);
    }

}

