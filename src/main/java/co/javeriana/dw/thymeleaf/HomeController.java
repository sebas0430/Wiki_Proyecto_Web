package co.javeriana.dw.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

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

    @org.springframework.web.bind.annotation.PostMapping("/contactenos")
    public String procesarContacto() {
        return "redirect:/contactenos?exito";
    }
}
