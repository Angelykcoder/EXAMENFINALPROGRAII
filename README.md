# API de Administración de Profesores

API REST desarrollada con **Java 17 + Spring Boot 3** para administrar profesores.
Arquitectura por capas (controller → service → repository), almacenamiento **en memoria**
(sin base de datos), validaciones con Bean Validation, manejo global de excepciones,
protección por **API Key** y pruebas unitarias con JUnit 5 + Mockito.

## Cómo ejecutar

Requisitos: JDK 17 y Maven 3.8+.

```bash
mvn spring-boot:run      # inicia la API en http://localhost:8080
mvn test                 # ejecuta las pruebas unitarias
```

## API Key

Todo request a `/api/**` debe incluir el header:

```
X-API-KEY: examen-api-key-2026
```

El valor válido se configura en `src/main/resources/application.properties` (`api.key`).
Si el header falta o es incorrecto, la API responde **401 UNAUTHORIZED** con un cuerpo JSON de error.

## Endpoints

| Método | Ruta | Descripción | Éxito |
|---|---|---|---|
| POST | `/api/profesores` | Crear profesor | 201 CREATED |
| GET | `/api/profesores` | Listar todos | 200 OK |
| GET | `/api/profesores?especialidad={valor}` | Filtrar por especialidad (sin distinguir mayúsculas) | 200 OK |
| GET | `/api/profesores/{id}` | Consultar por id | 200 OK |

Errores: 400 (validación), 401 (API Key), 404 (profesor no encontrado), 500 (error interno).

## Ejemplos

Crear profesor (201):

```bash
curl -X POST http://localhost:8080/api/profesores \
  -H "X-API-KEY: examen-api-key-2026" -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Pérez","correo":"juan.perez@correo.com","especialidad":"Matemáticas"}'
```

```json
{ "id": 1, "nombre": "Juan", "apellido": "Pérez", "correo": "juan.perez@correo.com",
  "especialidad": "Matemáticas", "estado": "ACTIVO" }
```

Filtrar por especialidad y consultar por id:

```bash
curl -H "X-API-KEY: examen-api-key-2026" "http://localhost:8080/api/profesores?especialidad=matemáticas"
curl -H "X-API-KEY: examen-api-key-2026" http://localhost:8080/api/profesores/1
```

Error 404 (`/api/profesores/99`):

```json
{ "timestamp": "2026-11-07T10:00:00", "status": 404, "error": "Not Found",
  "mensaje": "Profesor con id 99 no encontrado", "path": "/api/profesores/99", "detalles": [] }
```

Error 400 de validación: `detalles` lista cada campo, p. ej. `"nombre: El nombre es obligatorio"`.
Sin header `X-API-KEY` (o con uno incorrecto): **401** con el mismo formato de error.

## Colección Postman

Importar `postman/pruebaspostman.json` (Collection v2.1). Variables: `baseUrl`, `apiKey`, `profesorId`.
