package com.seguridad.servicio_autenticacion.repository;
import com.seguridad.servicio_autenticacion.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Estudiante
 */

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    //Necesitamos buscar al usuario por su nombre de usuario cuando intente loguearse.
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
    // Necesitamos verificar si ya existe un usuario con ese nombre al registrarse
    boolean existsByNombreUsuario(String nombreUsuario);
}
