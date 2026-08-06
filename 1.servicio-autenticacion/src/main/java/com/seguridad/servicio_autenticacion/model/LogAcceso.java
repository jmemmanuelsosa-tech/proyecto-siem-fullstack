package com.seguridad.servicio_autenticacion.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Estudiante
 */
@Entity
@Data
@Table(name="logs_acceso")
@NoArgsConstructor
@AllArgsConstructor
public class LogAcceso {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    //Guarda el usuario que intentó entrar
    private String nombreUsuario;
    //Para saber desde qué IP enviaron la petición
    private String ipOrigen;
    private Boolean exito;
    private String detalleError;
    //Para saber exactamente cuándo ocurrió
    private LocalDateTime fechaHora;
    
    //Un usuario puede tener muchos logs de acceso, pero cada log pertenece a un solo usuario
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    
}
