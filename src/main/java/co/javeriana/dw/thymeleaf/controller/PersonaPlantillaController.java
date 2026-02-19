package co.javeriana.dw.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.javeriana.dw.thymeleaf.model.Persona;
import co.javeriana.dw.thymeleaf.repository.PersonaRepository;

@Controller
@RequestMapping("/plantillas")
public class PersonaPlantillaController {

    @Autowired
    private PersonaRepository personaRepository;

    /**
     * Endpoint: GET /plantillas/lista-personas
     * Obtiene todas las personas de la base de datos y las pasa a la vista
     */
    @GetMapping("/lista-personas")
    public String listaPersonas(Model model) {
        Iterable<Persona> personas = personaRepository.findAll();
        model.addAttribute("personas", personas);
        return "lista-personas";
    }
}
