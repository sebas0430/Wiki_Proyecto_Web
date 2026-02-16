package co.javeriana.dw.thymeleaf.repository;

import co.javeriana.dw.thymeleaf.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
