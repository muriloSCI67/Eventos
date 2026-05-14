package br.com.exercicios.spring.eventos.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("api/eventos")
public class EventosController {
    @Autowired
    private CorretorService corretorService;
}
