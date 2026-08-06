package com.seguridad.servicio_autenticacion.dto;
import java.time.LocalDateTime;
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
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private int status; //ej: 401, 400
    private String error; //ej:"Unauthorized"
    private String mensaje; //ej: el mensaje claro del error
    private String path; //ej:URL solicitada
    
}
