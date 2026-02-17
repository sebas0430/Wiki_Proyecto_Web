package co.javeriana.dw.thymeleaf.controller;

import co.javeriana.dw.thymeleaf.model.Persona;
import co.javeriana.dw.thymeleaf.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/formularios")
public class PersonaFormularioController {

    @Autowired
    private PersonaRepository personaRepository;

    /**
     * Endpoint: GET /formularios/nueva-persona
     * Muestra el formulario para agregar una nueva persona
     */
    @GetMapping("/nueva-persona")
    public String mostrarFormulario() {
        return "formulario-persona";
    }

    /**
     * Endpoint: POST /formularios/guardar-persona
     * Recibe los datos del formulario mediante @RequestParam
     * Guarda la persona en la base de datos y muestra la lista actualizada
     */
    @PostMapping("/guardar-persona")
    public String guardarPersona(
            Model model,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String telefono) {

        // Crear y guardar la nueva persona
        //personaRepository.save(new Persona(null, nombre, apellido, telefono));

        // Obtener todas las personas y pasarlas al modelo
        Iterable<Persona> personas = personaRepository.findAll();
        model.addAttribute("personas", personas);

        // Retornar la vista de lista de personas
        return "lista-personas";
    }
}
