# Tarea MVC + Bootstrap - Programacion II

Aplicacion web hecha con **Spring Boot** siguiendo el patron
**Modelo - Vista - Controlador (MVC)**, con todas las pantallas estilizadas con
**Bootstrap 5**. Se conecta a MySQL con JDBC reutilizando los modelos y los DAO
de la tarea anterior.

**Estudiante:** Luis Pablo de Jesus Lopez Carrera · Carnet 7690-26-6662
**Curso:** Programacion II · Universidad Mariano Galvez

## Video de funcionamiento

**Video (max. 2 minutos):** https://youtu.be/wRp5nH7tjtA

## Modulos (3 entidades)

| Modulo | URL | Tabla | Que se puede hacer |
|---|---|---|---|
| Clientes | `/clientes` | `clientes` | Registrar, ver, editar y eliminar |
| Marcas | `/marcas` | `marcas` | Registrar, ver, editar y eliminar |
| Puestos | `/puestos` | `puestos` | Registrar, ver, editar y eliminar |

## Como esta separado el MVC

| Capa | Donde esta | Que hace |
|---|---|---|
| **Modelo** | `com.umg.edu.modelo` + `com.umg.edu.dao` + `com.umg.edu.config` | Las clases de cada tabla (`Cliente`, `Marca`, `Puesto`), los DAO con todo el SQL y la conexion `ConexionDB` |
| **Vista** | `src/main/resources/templates` | Los HTML con Thymeleaf y Bootstrap (una lista y un formulario por modulo) |
| **Controlador** | `com.umg.edu.controlador` | `ClienteControlador`, `MarcaControlador`, `PuestoControlador` e `InicioControlador`: reciben la peticion, llaman al DAO y eligen la vista |

La vista **nunca** llama al DAO: los datos le llegan solo por
`model.addAttribute(...)` desde el controlador.

### Flujo de una peticion (guardar un cliente)

1. El usuario llena el formulario y presiona **Guardar** (POST `/clientes/guardar`).
2. `ClienteControlador` recibe el `Cliente` ya armado con `@ModelAttribute`.
3. Valida los campos y llama a `ClienteDAO.insertar(...)` o `actualizar(...)`.
4. El DAO ejecuta el `INSERT` / `UPDATE` con `PreparedStatement`.
5. El controlador redirige a `/clientes` con un mensaje, que la vista muestra como alerta de Bootstrap.

## Bootstrap usado

Barra de navegacion (`navbar`), tablas (`table table-striped table-hover`,
`table-responsive`), formularios (`form-control`, `form-label`, `input-group`),
botones (`btn-primary`, `btn-success`, `btn-outline-danger`), alertas
(`alert-success`, `alert-danger`, `alert-info`), tarjetas (`card`) y la
cuadricula (`row`, `col-md-*`) para que se adapte al celular.

## Estructura

```
TiendaMVC/
├── pom.xml
└── src/main/
    ├── java/com/umg/edu/
    │   ├── TiendaMVC.java              (clase principal)
    │   ├── config/ConexionDB.java
    │   ├── modelo/                     (Persona, Cliente, Marca, Puesto)
    │   ├── dao/                        (CrudDAO<T>, ClienteDAO, MarcaDAO, PuestoDAO)
    │   └── controlador/                (Inicio, Cliente, Marca, Puesto)
    └── resources/
        ├── application.properties
        └── templates/
            ├── index.html
            ├── fragments/layout.html   (encabezado, navbar, alertas y pie)
            ├── clientes/  (lista.html, formulario.html)
            ├── marcas/    (lista.html, formulario.html)
            └── puestos/   (lista.html, formulario.html)
```

## Como correrlo

1. Ejecutar `script_base_datos.sql` en MySQL para crear la base `tienda`.
2. Revisar usuario y contrasena en `TiendaMVC/src/main/java/com/umg/edu/config/ConexionDB.java`
   (por defecto `root` / `admin`).
3. Abrir la carpeta `TiendaMVC` en NetBeans y darle **Run** (clase principal `com.umg.edu.TiendaMVC`).
4. Entrar desde el navegador a **http://localhost:8080**
