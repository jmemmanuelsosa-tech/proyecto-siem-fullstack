package com.seguridad.servicio_autenticacion.controller;
import com.seguridad.servicio_autenticacion.dto.LoginRequest;
import com.seguridad.servicio_autenticacion.dto.RegistroRequest;
import com.seguridad.servicio_autenticacion.dto.UsuarioResponseDTO;
import com.seguridad.servicio_autenticacion.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Estudiante
 */
@RestController
@RequestMapping("/api/auth")
/*acepte peticiones externas (CORS)*/
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    //Endpoint de registro
 @PostMapping("/registro")
 public ResponseEntity<UsuarioResponseDTO> registroRequest (@RequestBody RegistroRequest registro){
    //para que encripte la clave, valide si el usuario existe y lo guarde en MySQL
    //ResponseEntity.ok(...). Esto le devuelve al cliente 
    //el objeto recién creado junto con un código de estado HTTP 200 OK, confirmando que todo salió perfecto.
    return ResponseEntity.ok(authService.registrar(registro));
 }
    
    //Endpoint de login
 @PostMapping("/login")
 public ResponseEntity<UsuarioResponseDTO> login (@RequestBody LoginRequest loginRequest){
     return ResponseEntity.ok(authService.login(loginRequest));
 }
 
}
