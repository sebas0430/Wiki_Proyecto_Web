# WikiProyecto - Los Aventureros

Aplicación web tipo Wiki desarrollada con **Spring Boot 3.4.1** y **Thymeleaf**, siguiendo la arquitectura **MVC** (Modelo-Vista-Controlador).

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.4.1**
- **Thymeleaf** (motor de plantillas)
- **Spring Data JPA** + **H2** (base de datos en memoria)
- **Lombok**
- **Maven**
- **Docker**

## Estructura del proyecto

```
src/main/
├── java/co/javeriana/dw/thymeleaf/
│   ├── ThymeleafApplication.java          # Punto de entrada
│   ├── HomeController.java                # Controlador principal (/, /nosotros, /contactenos)
│   ├── controller/
│   │   └── PersonaPlantillaController.java # Controlador lista de personas
│   ├── model/
│   │   └── Persona.java                   # Entidad JPA
│   └── repository/
│       └── PersonaRepository.java         # Repositorio JPA
└── resources/
    ├── templates/                         # Vistas Thymeleaf
    │   ├── index.html                     # Página de inicio
    │   ├── nosotros.html                  # Página del equipo
    │   ├── contactenos.html               # Formulario de contacto
    │   ├── lista-personas.html            # Lista de contactos registrados
    │   └── fragments/
    │       └── navbar.html                # Fragmento reutilizable del navbar
    ├── static/
    │   ├── styles.css                     # Hoja de estilos
    │   ├── validation.js                  # Validaciones JavaScript del formulario
    │   └── images/                        # Fotos del equipo
    └── application.properties             # Configuración de Spring Boot
```

## Páginas de la Wiki

| Ruta | Descripción |
|------|-------------|
| `/` | Página de inicio con descripción del proyecto |
| `/nosotros` | Información del equipo "Los Aventureros" |
| `/contactenos` | Formulario de contacto con validaciones JavaScript |
| `/plantillas/lista-personas` | Lista de personas registradas desde el formulario |

## Despliegue con Docker

### Prerrequisitos

- Tener **Docker** instalado y en ejecución.

### Paso 1: Construir la imagen

Desde la raíz del proyecto (donde está el `Dockerfile`), ejecutar:

```bash
docker build -t wikilosaventureros .
```

### Paso 2: Ejecutar el contenedor

```bash
docker run -p 8080:8080 wikilosaventureros
```

### Paso 3: Acceder a la aplicación

Abrir en el navegador:

- **Inicio**: [http://localhost:8080/](http://localhost:8080/)
- **Sobre Nosotros**: [http://localhost:8080/nosotros](http://localhost:8080/nosotros)
- **Contáctenos**: [http://localhost:8080/contactenos](http://localhost:8080/contactenos)
- **Lista de Personas**: [http://localhost:8080/plantillas/lista-personas](http://localhost:8080/plantillas/lista-personas)
- **Consola H2**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### Datos de conexión H2 (consola)

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:testdb` |
| User Name | `sa` |
| Password | *(vacío)* |

## Equipo - Los Aventureros

- Sebastián (Scrum Master)
- Vanessa (UI/UX)
- Santiago (DevOps)
- Angy (QA & Dev)
- Ivan (SecOps)
- Tomas (Cloud Arch)
