# User API

REST API para gestión de usuarios construida con Spring Boot siguiendo la **Arquitectura Hexagonal (Ports & Adapters)**.

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Data JPA | - |
| MySQL | 8.0 |
| SpringDoc OpenAPI | 3.0.2 |
| Lombok | - |
| Maven | - |
| Docker | - |

---

## Arquitectura

El proyecto implementa Arquitectura Hexagonal con tres capas bien definidas:

```
src/main/java/com/empresa/api/
├── domain/
│   ├── model/          # Entidad de dominio puro
│   └── service/        # Lógica de negocio y validaciones
├── application/
│   ├── port/
│   │   ├── in/         # Puerto de entrada (interfaz de casos de uso)
│   │   └── out/        # Puerto de salida (interfaz del repositorio)
│   └── usecase/        # Implementación de casos de uso
└── infrastructure/
    ├── config/         # Configuración de Spring beans y OpenAPI
    ├── persistence/    # Adaptador JPA, entidad y repositorio
    └── web/rest/       # Controlador REST, DTOs y mapper
```

---

## Requisitos previos

**Con Docker (recomendado)**
- Docker Desktop

**Sin Docker**
- Java 17+
- Maven 3.6+
- MySQL corriendo en `localhost:3307`

---

## Ejecución con Docker

Requiere MySQL corriendo en el host en el puerto `3307`. El contenedor se conecta a él via `host.docker.internal`.

```bash
docker compose up --build
```

Para detener:

```bash
docker compose down
```

### Construir solo la imagen

```bash
docker build -t user-api .
```

---

## Ejecución local (sin Docker)

Editar [src/main/resources/application.properties](src/main/resources/application.properties) con las credenciales de tu base de datos:
el password consultelo con el Lider Tecnico.
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/demo_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=xxxxxxxx
server.port=8099
```

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

> La base de datos se crea automáticamente al iniciar la aplicación.

La API queda disponible en `http://localhost:8099`.

---

## Endpoints

Base URL: `/api/users`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/new` | Crear un nuevo usuario |
| `GET` | `/list` | Listar todos los usuarios |
| `GET` | `/email/{email}` | Buscar usuario por email exacto |
| `GET` | `/buscar/nombre?q=` | Buscar usuarios por nombre (parcial) |
| `GET` | `/buscar/apellido?q=` | Buscar usuarios por apellido |
| `GET` | `/buscar/edad?q=` | Buscar usuarios por edad |

### Ejemplo — Crear usuario

**Request**
```http
POST /api/users/new
Content-Type: application/json

{
  "nombres": "Carlos José",
  "apellidos": "Cáceres Ochoa",
  "email": "usuario@empresa.com",
  "edad": "25"
}
```

**Response exitoso**
```json
{
  "status": "SUCCESS",
  "message": "Usuario creado correctamente",
  "data": { ... },
  "error": null,
  "timestamp": "2026-05-24T10:00:00"
}
```

**Response con error de validación (HTTP 400)**
```json
{
  "status": "ERROR",
  "message": "Error de validación",
  "data": null,
  "error": "El email es requerido",
  "timestamp": "2026-05-24T10:00:00"
}
```

---

## Documentación interactiva

Con la aplicación corriendo, la documentación Swagger UI está disponible en:

```
http://localhost:8099/swagger-ui.html
```

---

## Esquema de base de datos

```sql
CREATE TABLE users (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    nombres  VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email    VARCHAR(150) NOT NULL UNIQUE,
    edad     VARCHAR(10)  NOT NULL,
    PRIMARY KEY (id)
);
```

---

## Tests

```bash
./mvnw test
```

El proyecto incluye 13 pruebas unitarias sobre la lógica de validación del dominio (`UserServiceTest`).

---

## Autor

**Carlos José Cáceres Ochoa** — car.caceres.ochoa@gmail.com

Licencia MIT
