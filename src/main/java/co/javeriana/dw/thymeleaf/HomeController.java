package co.javeriana.dw.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.javeriana.dw.thymeleaf.model.Persona;
import co.javeriana.dw.thymeleaf.repository.PersonaRepository;

@Controller
public class HomeController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/contactenos")
    public String contactenos() {
        return "contactenos";
    }

    @PostMapping("/contactenos")
    public String procesarContacto(@RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String asunto,
            @RequestParam String mensaje) {

        personaRepository.save(new Persona(nombre, email, telefono, asunto, mensaje));
        return "redirect:/plantillas/lista-personas";
    }
}
