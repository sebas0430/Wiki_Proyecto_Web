package co.javeriana.dw.thymeleaf.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "status = 0") // Solo selecciona a las personas activas (status = 0)
@SQLDelete(sql = "UPDATE personas SET status = 1 WHERE id=?")

public class Persona {
    // Aquí van los atributos y anotaciones JPA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 150)
    private String rol;
    
    @Column(columnDefinition = "TEXT")
    private String biografia;
    
    @Column(name = "url_foto", length = 255)
    private String urlFoto;
    
    @Column(length = 100)
    private String email;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;
    
    // Constructor sin ID y sin status para poder crear los nuevos registros que esten activos
    public Persona(String nombre, String rol, String biografia, String urlFoto, String email) {
        this.nombre = nombre;
        this.rol = rol;
        this.biografia = biografia;
        this.urlFoto = urlFoto;
        this.email = email;
        this.status = Status.ACTIVE;
    }
    
    // Enum para el estado de la persona
    public enum Status {
        ACTIVE(0),
        DELETED(1);
        
        private final int value;
        //Setter y Getter para el valor del enum
        Status(int value) { 
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
}