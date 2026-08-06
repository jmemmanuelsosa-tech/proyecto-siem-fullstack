package com.seguridad.servicio_autenticacion.controller;
import com.seguridad.servicio_autenticacion.model.LogAcceso;
import com.seguridad.servicio_autenticacion.service.LogAccesoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Estudiante
 */
@RestController
@RequestMapping("/api/logs")
/*Porque  el front corre en otra ruta/puerto*/
@CrossOrigin(origins = "*")
public class LogAccesoController {
    @Autowired
    private LogAccesoService logAccesoService;
    
   @GetMapping
   public ResponseEntity<List<LogAcceso>> logAcceso(){
       return ResponseEntity.ok(logAccesoService.obtenerTodosLosLogs());
   }
    
}
