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
     * Obtiene todas las personas de la base de datos y las pasa a la vista usando
     * Model.
     */
    @GetMapping("/lista-personas")
    public String listaPersonas(Model model) {
        Iterable<Persona> personas = personaRepository.findAll();
        model.addAttribute("personas", personas);
        return "lista-personas";
    }

    /**
     * Endpoint: GET /plantillas/personas-solo-texto
     * Retorna una vista simple sin pasar datos dinámicos.
     */
    @GetMapping("/personas-solo-texto")
    public String personasSoloTexto() {
        return "personas-solo-texto";
    }

    /**
     * Endpoint: GET /plantillas/personas-model-and-view
     * Ejemplo clásico usando la clase ModelAndView para agrupar vista y datos.
     */
    @GetMapping("/personas-model-and-view")
    public ModelAndView personasModelAndView() {
        // Se define la vista lógica
        ModelAndView mav = new ModelAndView("personas-model-and-view");

        // Agregamos datos al modelo
        mav.addObject("mensaje", "Este es un ejemplo usando ModelAndView");
        mav.addObject("totalPersonas", personaRepository.count());

        return mav;
    }

    /**
     * Endpoint: GET /plantillas/personas-paso-parametros
     * Pasa múltiples parámetros individuales a la vista para ser mostrados
     * independientemente.
     */
    @GetMapping("/personas-paso-parametros")
    public ModelAndView personasPasoParametros() {
        ModelAndView modelAndView = new ModelAndView("personas-paso-parametros");

        // Simulamos datos sueltos
        modelAndView.addObject("persona1", "Juan Perez (CEO)");
        modelAndView.addObject("persona2", "María Lopez (CTO)");
        modelAndView.addObject("persona3", "Pedro Diaz (Dev)");
        modelAndView.addObject("persona4", "Ana Gomez (QA)");
        modelAndView.addObject("persona5", "Carlos Ruiz (Ops)");

        return modelAndView;
    }
}