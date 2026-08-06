package com.seguridad.servicio_autenticacion.dto;
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
public class RegistroRequest {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String email;
    private Long rolId;
}
