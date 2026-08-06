package com.seguridad.servicio_autenticacion.exception;
import com.seguridad.servicio_autenticacion.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Estudiante
 */
//Esto nos permite interceptar cualquier error que ocurra en los controladores y devolver una respuesta clara
@RestControllerAdvice
public class GlobalExceptionHandler {
    
  // 1. Cuando la contraseña o usuario no coinciden (401 Unauthorized)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> manejarBadCredentials(HttpServletRequest request) {
        
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                401,
                "Unauthorized",
                "Nombre de usuario o contraseña incorrectos",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // 2. Cuando ocurre un error común o no encuentra un registro (400 Bad Request)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> manejarRuntimeException(RuntimeException ex, HttpServletRequest request) {
        
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                400,
                "Bad Request",
                ex.getMessage(), // El mensaje exacto que tiró tu código
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 3. Cuando fallan las validaciones de datos (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidaciones(HttpServletRequest request) {
        
        ErrorResponseDTO error = new ErrorResponseDTO(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Los datos enviados no son válidos.",
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
