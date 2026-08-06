package com.seguridad.servicio_autenticacion.service;
import com.seguridad.servicio_autenticacion.model.Usuario;
import com.seguridad.servicio_autenticacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Estudiante
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     // Buscamos el usuario en la base de datos por su nombre
     Usuario usuario = usuarioRepository.findByNombreUsuario(username).orElse(null);
     
     // Si no existe, lanzamos un error que Spring Security entiende
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    
     // Convertimos sus roles a un arreglo de Strings
    String[] nombresRoles = usuario.getRoles().stream()
            .map(rol -> rol.getNombreRol())
            .toArray(String[]::new);

    // Armamos el User con el builder de Spring Security (como en tu foto)
    return User.withUsername(usuario.getNombreUsuario())
            .password(usuario.getContrasena())
            .disabled(!usuario.getActivo())
            .authorities(nombresRoles)
            .build();
    
    }
    
}
