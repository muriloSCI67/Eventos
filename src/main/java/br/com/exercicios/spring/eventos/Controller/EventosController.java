package br.com.exercicios.spring.eventos.Controller;

import br.com.exercicios.spring.eventos.Service.EventosService;
import com.example.eventos.entity.Eventos;
import com.example.eventos.handlers.MinhaException;
import com.example.eventos.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")

public class EventosController {
    @Autowired
    private EventosService eventosService;
    //
    @PostMapping
    public Evento cadastrarEvento(@RequestBody Evento evento) {

//        if(evento.getPalestrante() > 0) {

//           throw new MinhaException( "O  precisa ter um preço maior que R$0");
//        }

//        if (evento.getEmailContato() < 0) {

//            throw new MinhaException("A eventomoeda precisa ter um volume negociado maior que R$0");
//        }
        return eventosService.salvarEvento(evento);
    }

    @GetMapping
    public List<Evento> buscarTodosOsEventos() {
        return eventosService.listarTodosOsEventos();
    }

    @DeleteMapping("/id/{id}")
    public String deletarEvento(@PathVariable Long id) {
        return eventosService.deletarEventoPorId(id);
    }

    @PutMapping("/id/{id}")
    public Evento editarEventoPorID(@PathVariable Long id, @RequestBody Eventos eventoAtualizada) {
        return eventosService.editarEventoPorID(id, eventoAtualizada);

    }

    @PutMapping("/descricao/{descricao}")
    public Evento editarEventoPorDescricao(@PathVariable String descricao, @RequestBody Evento eventoAtualizada) {
        return eventosService.editarEventoPorDescricao(descricao, eventoAtualizada);
    }
}

}