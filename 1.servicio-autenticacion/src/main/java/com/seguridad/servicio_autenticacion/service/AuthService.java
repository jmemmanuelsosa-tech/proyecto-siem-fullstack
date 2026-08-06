package com.seguridad.servicio_autenticacion.service;
import com.seguridad.servicio_autenticacion.config.JwtUtil;
import com.seguridad.servicio_autenticacion.dto.LoginRequest;
import com.seguridad.servicio_autenticacion.dto.RegistroRequest;
import com.seguridad.servicio_autenticacion.dto.UsuarioResponseDTO;
import com.seguridad.servicio_autenticacion.model.LogAcceso;
import com.seguridad.servicio_autenticacion.model.Rol;
import com.seguridad.servicio_autenticacion.model.Usuario;
import com.seguridad.servicio_autenticacion.repository.LogAccesoRepository;
import com.seguridad.servicio_autenticacion.repository.RolRepository;
import com.seguridad.servicio_autenticacion.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Estudiante
 */

@Service
public class AuthService {
    
    @Autowired
    /*romper el ciclo pidiéndole a Spring que inyecte el AuthenticationManager de forma "perezosa"*/
    @Lazy
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private LogAccesoRepository logAccesoRepository;
    
    public UsuarioResponseDTO registrar(RegistroRequest request){
        //Usamos tu usuarioRepository para verificar si el username ya está registrado
         if (usuarioRepository.existsByNombreUsuario(request.getUsername())){
         throw new RuntimeException("El nombre del usuario ya está en uso");
         }
         
         // Si no mandan rolId, asignamos por defecto el ID 1L (o ajustalo al ID de tu tabla roles)
    Long idRolABuscar = (request.getRolId() != null) ? request.getRolId() : 1L;

    Rol rol = rolRepository.findById(idRolABuscar)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + idRolABuscar));
         
         //Instanciamos el objeto Usuario y le seteamos los datos
         Usuario nuevoUsuario = new Usuario();
                 nuevoUsuario.setNombreUsuario(request.getUsername());
                 nuevoUsuario.setContrasena(passwordEncoder.encode(request.getPassword()));
                 nuevoUsuario.setNombre(request.getNombre());
                 nuevoUsuario.setApellido(request.getApellido());
                 nuevoUsuario.setEmail(request.getEmail());
                 nuevoUsuario.setActivo(true);
                 // Seteás el rol dentro de una lista
                 nuevoUsuario.setRoles(Collections.singletonList(rol));
        
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        
        String token = jwtUtil.generarToken(usuarioGuardado.getNombreUsuario());
            return convertirADto(usuarioGuardado, token);
    } 
    
    public UsuarioResponseDTO login (LoginRequest request){
      //Delegamos a spring security la verificación de la clave encriptada
      authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
        
      );
      //Buscamos la entidad completa para devolverla o registrar acceso
    Usuario usuario = usuarioRepository.findByNombreUsuario(request.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
      //Genera el token
    String token = jwtUtil.generarToken(usuario.getNombreUsuario());
    
    //Instanciamos un nuevo objeto log
    LogAcceso log = new LogAcceso();
    log.setNombreUsuario(usuario.getNombreUsuario());
    log.setIpOrigen("127.0.0.1");
    log.setFechaHora(LocalDateTime.now());
    log.setExito(true);
    log.setDetalleError(null);
    log.setUsuario(usuario);
    
    logAccesoRepository.save(log);
    
        return convertirADto(usuario, token);
    }
    
     //método auxiliar para convertir Usuario a UsuarioResponseDTO
    private UsuarioResponseDTO convertirADto(Usuario usuario, String token){
  
    // 1. Extraemos los nombres de los roles usando getNombreRol()
    Set<String> rolesNombres = new HashSet<>();
    if (usuario.getRoles() != null) {
        for (Rol rol : usuario.getRoles()) {
            rolesNombres.add(rol.getNombreRol()); 
        }
    }

    // 2. Creamos y llenamos el DTO 
    UsuarioResponseDTO dto = new UsuarioResponseDTO();
    dto.setId(usuario.getId());
    dto.setNombreUsuario(usuario.getNombreUsuario());
    dto.setApellido(usuario.getApellido());
    dto.setEmail(usuario.getEmail());
    dto.setActivo(usuario.getActivo()); 
    dto.setRoles(rolesNombres);
    dto.setToken(token);

    return dto;
}
    
    
}
