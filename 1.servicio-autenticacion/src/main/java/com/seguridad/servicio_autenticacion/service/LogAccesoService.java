package com.seguridad.servicio_autenticacion.service;
import com.seguridad.servicio_autenticacion.model.LogAcceso;
import com.seguridad.servicio_autenticacion.repository.LogAccesoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Estudiante
 */

@Service
public class LogAccesoService {
 
    @Autowired
    private LogAccesoRepository logAccesoRepository;
    
    public List<LogAcceso> obtenerTodosLosLogs(){
        //llamamos el metodo del repositorio para retornar la lista completa de logs.
        return logAccesoRepository.findAll();
    }
}
