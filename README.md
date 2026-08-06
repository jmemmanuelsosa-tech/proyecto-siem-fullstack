# Sistema de Autenticación, Auditoría y Motor SIEM (SOC)

Este proyecto es una solución integral **Full Stack y de Ciberseguridad** dividida en tres módulos interconectados: un **Backend seguro en Spring Boot** con autenticación JWT y auditoría de eventos, un **Frontend/Dashboard SOC** interactivo y un **Motor de Análisis de Seguridad en Python** con Pandas.

---

## Arquitectura del Sistema

* **Backend (Spring Boot 3 + MySQL):** Gestión de usuarios, autenticación mediante JWT, encriptación y persistencia de logs de auditoría.
* **Frontend (Vanilla JS + CSS3):** Interfaz SPA con vista de Login/Registro y un Dashboard SOC para monitoreo de eventos en tiempo real.
* **SIEM Core (Python 3 + Pandas):** Script de análisis automático de datos que consume la API REST, audita métricas de accesos y calcula niveles de amenaza.

---

## Tecnologías Utilizadas

* **Java 17 / 21** & **Spring Boot 3.x**
* **Spring Security 6.x** (JWT & Role Management)
* **Spring Data JPA / Hibernate** & **MySQL**
* **Python 3.x** & **Pandas** (Análisis de datos / SIEM)
* **HTML5, CSS3, JavaScript ES6+**
* **BCryptPasswordEncoder**
* **Maven** & **Postman**

---

## Módulos y Funcionalidades

### 1. Autenticación y Auditoría (Backend)
* **Registro y Login Seguro:** Encriptación de contraseñas con BCrypt y emisión de Tokens JWT.
* **Auditoría de Seguridad (SOC Logs):** Registro automático de eventos de acceso (éxito/fallo, IP de origen, fecha/hora y usuario).
* **Controlador Global de Excepciones:** Respuestas de error estandarizadas (`ErrorResponseDTO`) evitando fugas de información.

### 2. Dashboard SOC (Frontend)
* **Single Page Application (SPA):** Navegación fluida entre login, registro y la vista principal de monitoreo.
* **Tarjetas de Métricas:** Visualización centralizada de Total de Eventos, Logins Fallidos y Nivel de Amenaza.
* **Manejo de Sesión:** Persistencia del Token JWT en `localStorage`.

### 3. Motor de Detección SIEM (Python)
* **Consumo de API REST:** Petición HTTP autenticada con Bearer Token al endpoint `/api/logs`.
* **Procesamiento de Datos:** Uso de DataFrames en Pandas para consolidar métricas de auditoría.
* **Regla de Detección de Amenazas:** Cálculo automatizado para clasificar la amenaza en **BAJA, MEDIA o ALTA** según la cantidad de eventos fallidos detectados.

---

## Endpoints de la API

| Método | Endpoint | Descripción | Requiere Auth (JWT) |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/registro` | Registra un nuevo usuario en la base de datos | No |
| `POST` | `/api/auth/login` | Autentica credenciales y genera el Token JWT | No |
| `GET` | `/api/logs` | Devuelve el historial de logs de auditoría para el SIEM | Sí |

---

## Requisitos y Configuración Previa

### 1. Base de Datos (MySQL)
Crear la base de datos e insertar el rol por defecto en MySQL / phpMyAdmin:
```sql
CREATE DATABASE db_autenticacion;
USE db_autenticacion;
INSERT INTO roles (id, nombre_rol) VALUES (1, 'ROLE_USER');