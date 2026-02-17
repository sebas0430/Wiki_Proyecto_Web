package co.javeriana.dw.thymeleaf.controller;

import co.javeriana.dw.thymeleaf.model.Persona;
import co.javeriana.dw.thymeleaf.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * Endpoint: GET /plantillas/personas-solo-texto
     * Retorna una vista simple sin datos (solo texto estático)
     * Ejemplo de retornar un String con el nombre de la plantilla
     */
    @GetMapping("/personas-solo-texto")
    public String personasSoloTexto() {
        return "personas-solo-texto";
    }

    /**
     * Endpoint: GET /plantillas/personas-model-and-view
     * Retorna una vista usando ModelAndView
     */
    @GetMapping("/personas-model-and-view")
    public ModelAndView personasModelAndView() {
        return new ModelAndView("personas-model-and-view");
    }

    /**
     * Endpoint: GET /plantillas/personas-paso-parametros
     * Pasa múltiples parámetros individuales a la vista
     */
    @GetMapping("/personas-paso-parametros")
    public ModelAndView personasPasoParametros() {
        ModelAndView modelAndView = new ModelAndView("personas-paso-parametros");

        modelAndView.addObject("persona1", "Juan");
        modelAndView.addObject("persona2", "María");
        modelAndView.addObject("persona3", "Pedro");
        modelAndView.addObject("persona4", "Ana");
        modelAndView.addObject("persona5", "Carlos");

        return modelAndView;
    }
}
