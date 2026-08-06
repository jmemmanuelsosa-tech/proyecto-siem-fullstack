package com.seguridad.servicio_autenticacion.dto;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Estudiante
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nombreUsuario;
    private String apellido;
    private String email;
    private boolean activo;
    //para no exponer la entidad Rol completa, usamos una lista/conjunto de nombres de roles
    private Set<String> roles;
    //Acá va a viajar el JWT
    private String token;
}
