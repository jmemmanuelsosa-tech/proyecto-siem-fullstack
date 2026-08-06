const URL_API = 'http://localhost:8081/api/auth/login';

//Seleccionar los elementos del DOM
const formLogin = document.getElementById('form-login');
const mensajeAlerta = document.getElementById('mensaje-alerta');

// Mantener sesión activa si el usuario recarga la página (F5)
document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    if (token) {
        // Ocultamos la tarjeta completa (.card) para que no entorpezca la vista
        const card = document.querySelector('.card');
        if (card) card.style.display = 'none';
        
        const elPanel = document.getElementById('panel-logs');
        if (elPanel) elPanel.style.display = 'block';
        cargarLogs();
    }
});

//Escuchar el evento del envío(submit)
formLogin.addEventListener('submit', async (e) => {
    e.preventDefault();

    //Capturamos las variables  de los inputs y guardamos ambos valores en variables  
    const username = document.getElementById('input-usuario').value;
    const password = document.getElementById('input-pass').value;

    let loginExitoso = false;

    // --- BLOQUE 1: Petición al servidor ---
    try {
        const respuesta = await fetch(URL_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password }) //para mandar los datos en formato JSON a Java
        });

        if (respuesta.ok) {
            //para leer el cuerpo de la respuesta que nos manda Spring Boot.
            const textoRespuesta = await respuesta.text();
            let data;
            try { 
                data = JSON.parse(textoRespuesta); 
            } catch (p) { 
                data = { token: textoRespuesta }; 
            }

            // Extracción defensiva del token JWT
            const tokenRecibido = typeof data === 'string' ? data : (data.token || data.jwt || data.accessToken);
            
            if (tokenRecibido) {
                //guarda el token en el navegador
                localStorage.setItem('token', tokenRecibido);
            }

            loginExitoso = true; // Marcar como exitoso
        } else {
            let mensajeError = 'Credenciales incorrectas';
            try {
                const dataError = await respuesta.json();
                // Extrae únicamente el texto de la propiedad 'mensaje'
                mensajeError = dataError.mensaje || dataError.error || mensajeError;
            } catch (e) {
                // Si la respuesta no era un JSON válido, usa el texto plano
                const textoPlano = await respuesta.text();
                if (textoPlano) mensajeError = textoPlano;
            }

            mensajeAlerta.textContent = mensajeError;
            mensajeAlerta.className = 'error';
        }
    } catch (error) {
        console.error('Error en la red o servidor:', error);
        mensajeAlerta.textContent = 'No se pudo conectar con el servidor';
        mensajeAlerta.className = 'error';
        return; // Cortar ejecución si falla la red
    }

    // --- BLOQUE 2: Cambios en la interfaz (Fuera del try/catch) ---
    if (loginExitoso) {
        //inyectar texto en el div de alerta
        mensajeAlerta.textContent = '¡Inicio de sesión exitoso!';
        mensajeAlerta.className = 'exito';

        // Ocultamos la tarjeta (.card) completa para desplegar el panel a ancho completo
        const card = document.querySelector('.card');
        const elPanel = document.getElementById('panel-logs');

        if (card) card.style.display = 'none';
        if (elPanel) elPanel.style.display = 'block';

        cargarLogs();
    }
});

//Evento para ir registro
document.getElementById('irRegistro').addEventListener('click', (e) => {

    e.preventDefault();

    //seleccionamos el form, accedemos a su propiedad de estilo y le asignamos 'none'
    document.getElementById('form-login').style.display = 'none';

    //Seleccionamos el form
    document.getElementById('registroForm').style.display = 'block';
});

//Evento para volver al login
document.getElementById('irLogin').addEventListener('click', (e) => {
    //Frenamos la recarga de la pagina
    e.preventDefault();

    //Mostramos el formulario de login
    document.getElementById('form-login').style.display = 'block';

    //Ocultamos registro
    document.getElementById('registroForm').style.display = 'none';

});

//Evento del envío del formulario
document.getElementById('registroForm').addEventListener('submit', async (e) => {

    e.preventDefault();

    // 1. Guardamos los datos en el objeto
    const datosRegistro = {
        username: document.getElementById('reg-usuario').value,
        nombre: document.getElementById('reg-nombre').value,
        apellido: document.getElementById('reg-apellido').value,
        email: document.getElementById('reg-email').value,
        password: document.getElementById('reg-pass').value
    };

    try {
        // 2. Enviamos la petición POST al backend
        const respuesta = await fetch('http://localhost:8081/api/auth/registro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datosRegistro)
        });

        // 3. Evaluamos la respuesta
        if (respuesta.ok) {
            alert('¡Registro exitoso! Ya podés iniciar sesión.');
            document.getElementById('registroForm').reset();
            
            // Volvemos a mostrar el login
            document.getElementById('registroForm').style.display = 'none';
            document.getElementById('form-login').style.display = 'block';
        } else {
            alert('Error al registrar usuario. Revisa los datos.');
        }

    } catch (error) {
        console.error('Error de conexión:', error);
        alert('No se pudo conectar con el servidor.');
    }

});

// Función para pedir los logs a la API
async function cargarLogs() {
    const token = localStorage.getItem('token'); // 1. Recupera el JWT guardado

    try {
        const respuesta = await fetch('http://localhost:8081/api/logs', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // 2. Adjunta el token de seguridad
            }
        });

        if (respuesta.ok) {
            const logs = await respuesta.json(); // 3. Parsea la lista de objetos de MySQL
            renderizarTabla(logs); // 4. Pasa los datos a la función que los dibuja
        } else {
            console.error('Error al obtener logs:', respuesta.status, respuesta.statusText);
        }
    } catch (error) {
        console.error('Error en la petición de logs:', error);
    }
}

// Función para renderizar la tabla en el HTML
function renderizarTabla(logs) {
    const tbody = document.getElementById('tabla-logs-body');
    tbody.innerHTML = ''; 

    let contadorFallidos = 0;

    logs.forEach(log => {
        const fila = document.createElement('tr');

        const usuarioNombre = log.usuario ? log.usuario.username : (log.username || 'N/A');
        const esExito = log.exito;
        if (!esExito) contadorFallidos++;

        // Creamos la etiqueta con color
        const badgeEstado = esExito 
            ? '<span class="badge badge-exito">Éxito</span>' 
            : '<span class="badge badge-fallo">Fallo</span>';

        const detalle = log.detalleError || '-';
        const fecha = log.fechaHora ? new Date(log.fechaHora).toLocaleString() : 'N/A';

        fila.innerHTML = `
            <td>${log.id}</td>
            <td><strong>${usuarioNombre}</strong></td>
            <td><code>${log.ipOrigen}</code></td>
            <td>${badgeEstado}</td>
            <td>${detalle}</td>
            <td>${fecha}</td>
        `;

        tbody.appendChild(fila);
    });

    // Actualizar métricas dinámicamente
    document.getElementById('kpi-total').textContent = logs.length;
    document.getElementById('kpi-fallidos').textContent = contadorFallidos;
    
    // Regla simple: Si hay más de 3 fallos, marcamos alerta de amenaza
    const kpiAmenazas = document.getElementById('kpi-amenazas');
    if (contadorFallidos >= 3) {
        kpiAmenazas.textContent = 'ALTA';
        kpiAmenazas.style.color = '#dc3545';
    } else {
        kpiAmenazas.textContent = 'BAJA';
        kpiAmenazas.style.color = '#28a745';
    }
}

//para darle función al botón "Cerrar Sesión"
document.getElementById('btn-logout').addEventListener('click', () => {
    localStorage.removeItem('token');
    
    // Ocultamos el panel de logs
    document.getElementById('panel-logs').style.display = 'none';
    
    // Volvemos a mostrar la tarjeta (.card) contenedora y el formulario de login
    const card = document.querySelector('.card');
    if (card) card.style.display = 'block';
    
    document.getElementById('form-login').style.display = 'block';
    mensajeAlerta.textContent = '';
    mensajeAlerta.className = '';
});