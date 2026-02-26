package co.javeriana.dw.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/actas")
public class ActaController {

    // Ruta del archivo donde se guardan las actas
    private static final String ACTAS_FILE = "actas/actas.txt";
    private static final String SEPARATOR = "===========================";

    /**
     * GET /actas/agregar — Muestra el formulario para agregar un acta
     */
    @GetMapping("/agregar")
    public String mostrarFormulario() {
        return "agregar-acta";
    }

    /**
     * POST /actas/agregar — Procesa el formulario y guarda el acta en el archivo
     */
    @PostMapping("/agregar")
    public String guardarActa(
            @RequestParam String titulo,
            @RequestParam String fecha,
            @RequestParam String lugar,
            @RequestParam String asistentes,
            @RequestParam String temas,
            @RequestParam String acuerdos,
            @RequestParam(required = false, defaultValue = "") String observaciones,
            RedirectAttributes redirectAttributes) {

        try {
            // Crear el directorio si no existe
            Path dirPath = Paths.get("actas");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Preparar el contenido del acta
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            StringBuilder contenido = new StringBuilder();
            contenido.append(SEPARATOR).append("\n");
            contenido.append("ACTA DE REUNIÓN").append("\n");
            contenido.append(SEPARATOR).append("\n");
            contenido.append("Título: ").append(titulo).append("\n");
            contenido.append("Fecha: ").append(fecha).append("\n");
            contenido.append("Lugar: ").append(lugar).append("\n");
            contenido.append("Registrado el: ").append(timestamp).append("\n");
            contenido.append("\n");
            contenido.append("ASISTENTES:\n").append(asistentes).append("\n");
            contenido.append("\n");
            contenido.append("TEMAS TRATADOS:\n").append(temas).append("\n");
            contenido.append("\n");
            contenido.append("ACUERDOS Y COMPROMISOS:\n").append(acuerdos).append("\n");
            if (!observaciones.isBlank()) {
                contenido.append("\n");
                contenido.append("OBSERVACIONES:\n").append(observaciones).append("\n");
            }
            contenido.append(SEPARATOR).append("\n\n");

            // Escribir/append en el archivo
            Files.writeString(
                    Paths.get(ACTAS_FILE),
                    contenido.toString(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            redirectAttributes.addFlashAttribute("exito", "¡Acta guardada correctamente!");
            return "redirect:/actas/lista";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el acta: " + e.getMessage());
            return "redirect:/actas/agregar";
        }
    }

    /**
     * GET /actas/lista — Lee el archivo y muestra todas las actas
     */
    @GetMapping("/lista")
    public String listaActas(Model model) {
        List<Acta> actas = new ArrayList<>();
        Path filePath = Paths.get(ACTAS_FILE);

        if (Files.exists(filePath)) {
            try {
                String contenido = Files.readString(filePath);
                String[] bloques = contenido.split(SEPARATOR + "\n");

                for (String bloque : bloques) {
                    bloque = bloque.trim();
                    if (bloque.isBlank() || bloque.equals("ACTA DE REUNIÓN"))
                        continue;

                    // Saltar el encabezado "ACTA DE REUNIÓN" si es el primer token
                    if (bloque.startsWith("ACTA DE REUNIÓN\n")) {
                        bloque = bloque.substring("ACTA DE REUNIÓN\n".length()).trim();
                    }

                    if (!bloque.isBlank()) {
                        actas.add(parsearActa(bloque));
                    }
                }
            } catch (IOException e) {
                model.addAttribute("errorLectura", "No se pudo leer el archivo de actas.");
            }
        }

        model.addAttribute("actas", actas);
        model.addAttribute("totalActas", actas.size());
        return "lista-actas";
    }

    /**
     * Parsea un bloque de texto y lo convierte en un objeto Acta
     */
    private Acta parsearActa(String bloque) {
        Acta acta = new Acta();
        String[] lineas = bloque.split("\n");

        StringBuilder seccionActual = new StringBuilder();
        String claveActual = null;

        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.isBlank())
                continue;

            if (linea.startsWith("Título: ")) {
                acta.setTitulo(linea.substring("Título: ".length()));
            } else if (linea.startsWith("Fecha: ")) {
                acta.setFecha(linea.substring("Fecha: ".length()));
            } else if (linea.startsWith("Lugar: ")) {
                acta.setLugar(linea.substring("Lugar: ".length()));
            } else if (linea.startsWith("Registrado el: ")) {
                acta.setRegistradoEl(linea.substring("Registrado el: ".length()));
            } else if (linea.equals("ASISTENTES:")) {
                claveActual = "asistentes";
                seccionActual = new StringBuilder();
            } else if (linea.equals("TEMAS TRATADOS:")) {
                if ("asistentes".equals(claveActual))
                    acta.setAsistentes(seccionActual.toString().trim());
                claveActual = "temas";
                seccionActual = new StringBuilder();
            } else if (linea.equals("ACUERDOS Y COMPROMISOS:")) {
                if ("temas".equals(claveActual))
                    acta.setTemas(seccionActual.toString().trim());
                claveActual = "acuerdos";
                seccionActual = new StringBuilder();
            } else if (linea.equals("OBSERVACIONES:")) {
                if ("acuerdos".equals(claveActual))
                    acta.setAcuerdos(seccionActual.toString().trim());
                claveActual = "observaciones";
                seccionActual = new StringBuilder();
            } else if (claveActual != null) {
                if (seccionActual.length() > 0)
                    seccionActual.append("\n");
                seccionActual.append(linea);
            }
        }

        // Guardar la última sección
        if (claveActual != null) {
            String valor = seccionActual.toString().trim();
            switch (claveActual) {
                case "asistentes" -> acta.setAsistentes(valor);
                case "temas" -> acta.setTemas(valor);
                case "acuerdos" -> acta.setAcuerdos(valor);
                case "observaciones" -> acta.setObservaciones(valor);
            }
        }

        return acta;
    }

    /**
     * Clase interna para representar un acta parseada
     */
    public static class Acta {
        private String titulo = "";
        private String fecha = "";
        private String lugar = "";
        private String registradoEl = "";
        private String asistentes = "";
        private String temas = "";
        private String acuerdos = "";
        private String observaciones = "";

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getLugar() {
            return lugar;
        }

        public void setLugar(String lugar) {
            this.lugar = lugar;
        }

        public String getRegistradoEl() {
            return registradoEl;
        }

        public void setRegistradoEl(String registradoEl) {
            this.registradoEl = registradoEl;
        }

        public String getAsistentes() {
            return asistentes;
        }

        public void setAsistentes(String asistentes) {
            this.asistentes = asistentes;
        }

        public String getTemas() {
            return temas;
        }

        public void setTemas(String temas) {
            this.temas = temas;
        }

        public String getAcuerdos() {
            return acuerdos;
        }

        public void setAcuerdos(String acuerdos) {
            this.acuerdos = acuerdos;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
    }
}
