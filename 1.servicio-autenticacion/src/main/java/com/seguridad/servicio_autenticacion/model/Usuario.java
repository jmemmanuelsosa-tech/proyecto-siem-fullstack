package com.seguridad.servicio_autenticacion.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Estudiante
 */
@Entity
@Data
@Table(name="usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    //no puede haber usuarios duplicados ni vacíos
    @Column(unique=true, nullable=false)
    private String nombreUsuario;
    
    @Column(nullable=false)
    private String contrasena;
    
    private Boolean activo = true;
    private String nombre;
    private String apellido;
    private String email;
    
    // Muchos usuarios pueden tener muchos roles. EAGER carga los roles inmediatamente al buscar al usuario.
    @ManyToMany(fetch = FetchType.EAGER)
    //Crea la tabla intermedia 'usuario_roles' para unir la PK de usuarios con la PK de roles.
    @JoinTable (
    name = "usuario_roles",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;
    
}
