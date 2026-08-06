package com.seguridad.servicio_autenticacion.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Estudiante
 */
@Entity
@Data
@Table(name="roles")
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    //no puede haber roles duplicados ni vacíos
    @Column(nullable = false, unique = true)
    private String nombreRol; // Ejemplo: "ROLE_ADMIN", "ROLE_USER
}
