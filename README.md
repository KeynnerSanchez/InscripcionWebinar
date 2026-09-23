# Inscripcion Webinar Corporativo - JDBC + MVC

Aplicacion de consola para gestionar las inscripciones a un webinar corporativo.
Permite inscribir participantes (nombre, correo, empresa), listar, buscar por
empresa, contar y eliminar por id. No se permiten dos inscripciones con el
mismo correo.

## Tecnologias

- Java + Maven
- JDBC con PreparedStatement
- MySQL
- Arquitectura MVC (Modelo, Controlador, Vista)

## Estructura del proyecto

```
src/main/java/com/mycompany/inscripcionwebinar/
├── Modelo/
│   ├── Clases/Participante.java
│   └── Persistencia/ConexionBD.java
│               Operaciones.java
├── Controlador/ControladorParticipante.java
└── Vista/Main.java
```

La Vista solo llama al Controlador, no contiene sentencias SQL.

## Base de datos

No se crea una base de datos nueva, se usa la que ya esta configurada
(`my_db` o `campus`) y solo se agrega la tabla `participantes`.

Script para crear la tabla (ajusta el `USE` a tu base de datos):

```sql
USE my_db; -- o: USE campus;

CREATE TABLE IF NOT EXISTS participantes (
    idparticipante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    empresa VARCHAR(100) NOT NULL
);
```

### Configuracion de conexion

En `ConexionBD.java` ajusta la url, el usuario y la contrasena segun tu entorno:

| Entorno | Base de datos | Host : Puerto | Usuario / Contrasena |
|---|---|---|---|
| MySQL Server + Workbench | my_db | localhost : 3306 | root / 123456 |
| Docker + DBeaver | campus | localhost : 3307 | campus / campus123 |

Por defecto el proyecto queda configurado para MySQL Server + Workbench
(`my_db`, `localhost:3306`, `root/123456`). Si usas Docker + DBeaver,
cambia estos tres valores en `ConexionBD.java`.

## Como ejecutar

```bash
mvn clean compile
mvn exec:java
```

## Pruebas realizadas

1. Inscribir 3 participantes con correos distintos, de al menos 2 empresas diferentes.
2. Intentar inscribir con un correo repetido: se rechaza sin detener el programa.
3. Listar todos los participantes.
4. Buscar por una de las empresas registradas.
5. Contar los participantes inscritos.
6. Eliminar uno por su id y contar de nuevo.
7. Intentar eliminar un id que no existe: se informa sin errores no controlados.