package com.seguridad.servicio_autenticacion.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 *
 * @author Estudiante
 */
//Spring la gestione como un Bean e injection de dependencias
@Component
public class JwtUtil {

    // Clave secreta para firmar el token
    private static final Key CLAVE_SECRETA = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tiempo de expiración: 10 horas en milisegundos
    private static final long TIEMPO_EXPIRACION = 10 * 60 * 60 * 1000;

    // 1. GENERAR el token JWT
    public String generarToken(String nombreUsuario) {
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
                .signWith(CLAVE_SECRETA)
                .compact();
    }

    // 2. OBTENER el nombre de usuario desde el token
    public String obtenerUsuario(String token) {
        Claims claims = obtenerTodosLosClaims(token);
        return claims.getSubject(); // Devuelve el usuario guardado
    }

    // 3. OBTENER la fecha de vencimiento del token
    public Date obtenerExpiracion(String token) {
        Claims claims = obtenerTodosLosClaims(token);
        return claims.getExpiration(); // Devuelve la fecha de vencimiento
    }

    // 4. VERIFICAR si el token ya venció
    private boolean esTokenExpirado(String token) {
        return obtenerExpiracion(token).before(new Date());
    }

    // 5. VALIDAR si el token es correcto y no venció
    public boolean validarToken(String token, String nombreUsuario) {
        String usuarioDelToken = obtenerUsuario(token);
        return (usuarioDelToken.equals(nombreUsuario) && !esTokenExpirado(token));
    }

    // Método auxiliar interno para "abrir" el token y leer su contenido
    private Claims obtenerTodosLosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(CLAVE_SECRETA)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}