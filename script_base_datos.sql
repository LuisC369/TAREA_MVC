DROP DATABASE IF EXISTS tienda;
CREATE DATABASE tienda CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tienda;

-- 1. PUESTOS
CREATE TABLE puestos (
    id_puesto    INT         NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(50) NOT NULL,
    salario_base FLOAT       NOT NULL,
    PRIMARY KEY (id_puesto)
);

-- 2. MARCAS
CREATE TABLE marcas (
    id_marca INT         NOT NULL AUTO_INCREMENT,
    nombre   VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_marca)
);


-- 3. CLIENTES
CREATE TABLE clientes (
    id_cliente INT          NOT NULL AUTO_INCREMENT,
    nit        VARCHAR(20)  NOT NULL,
    nombre     VARCHAR(100) NOT NULL,
    apellidos  VARCHAR(100) NOT NULL,
    email      VARCHAR(100),
    PRIMARY KEY (id_cliente),
    UNIQUE KEY uk_clientes_nit (nit)
);

-- 4. EMPLEADOS jala el id de la tabal puestos
CREATE TABLE empleados (
    id_empleado        INT          NOT NULL AUTO_INCREMENT,
    id_puesto          INT          NOT NULL,
    nombre             VARCHAR(100) NOT NULL,
    apellidos          VARCHAR(100) NOT NULL,
    fecha_contratacion DATE         NOT NULL,
    PRIMARY KEY (id_empleado),
    CONSTRAINT fk_empleados_puestos
        FOREIGN KEY (id_puesto) REFERENCES puestos (id_puesto)
);

-- 5. PRODUCTOS  jala id de las marcas
CREATE TABLE productos (
    id_producto INT          NOT NULL AUTO_INCREMENT,
    id_marca    INT          NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    precio      FLOAT        NOT NULL,
    stock       INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id_producto),
    CONSTRAINT fk_productos_marcas
        FOREIGN KEY (id_marca) REFERENCES marcas (id_marca)
);


-- 6. FACTURAS  necestia un cliente y un empleado
CREATE TABLE facturas (
    id_factura  INT      NOT NULL AUTO_INCREMENT,
    id_cliente  INT      NOT NULL,
    id_empleado INT      NOT NULL,
    fecha       DATETIME NOT NULL,
    total       FLOAT    NOT NULL,
    PRIMARY KEY (id_factura),
    CONSTRAINT fk_facturas_clientes
        FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente),
    CONSTRAINT fk_facturas_empleados
        FOREIGN KEY (id_empleado) REFERENCES empleados (id_empleado)
);


-- 7. DETALLE_FACTURAS va a tener el id de factura y del producto
CREATE TABLE detalle_facturas (
    id_detalle      INT   NOT NULL AUTO_INCREMENT,
    id_factura      INT   NOT NULL,
    id_producto     INT   NOT NULL,
    cantidad        INT   NOT NULL,
    precio_unitario FLOAT NOT NULL,
    subtotal        FLOAT NOT NULL,
    PRIMARY KEY (id_detalle),
    CONSTRAINT fk_detalle_facturas
        FOREIGN KEY (id_factura) REFERENCES facturas (id_factura),
    CONSTRAINT fk_detalle_productos
        FOREIGN KEY (id_producto) REFERENCES productos (id_producto)
);

--  DATOS DE PRUEBA

INSERT INTO puestos (nombre, salario_base) VALUES
    ('Gerente',  8500.00),
    ('Vendedor', 4200.00),
    ('Cajero',   3800.00);

INSERT INTO marcas (nombre) VALUES
    ('Samsung'),
    ('HP'),
    ('Logitech');

INSERT INTO clientes (nit, nombre, apellidos, email) VALUES
    ('1234567-8', 'Juan',   'Pérez López',    'juan.perez@gmail.com'),
    ('98765321', 'María',  'García Ramírez', 'maria.garcia@outlook.com'),
    ('5555555-9', 'Carlos', 'Morales Díaz',   'carlos.morales@gmail.com');

INSERT INTO empleados (id_puesto, nombre, apellidos, fecha_contratacion) VALUES
    (1, 'Luis',  'López Hernández', '2022-01-15'),
    (2, 'Ana',   'Castillo Ruiz',   '2023-03-01'),
    (3, 'Pedro', 'Ramírez Soto',    '2024-06-10'),
    (2, 'Sofía', 'Mendoza Ortiz',   '2020-02-20');

INSERT INTO productos (id_marca, nombre, precio, stock) VALUES
    (1, 'Monitor Samsung 27 pulgadas FULL HD', 3250.00, 15),
    (2, 'Laptop HP Pavilion 15',       11500.00,  8),
    (3, 'Mouse Logitech M185',          950.00, 40),
    (3, 'Teclado Logitech K120',        1800.00, 25);

INSERT INTO facturas (id_cliente, id_empleado, fecha, total) VALUES
    (1, 2, '2026-08-01 10:30:00',  5150.00),
    (2, 4, '2026-08-05 15:45:00', 13300.00),
    (3, 2, '2026-08-10 09:15:00',  6450.00);

INSERT INTO detalle_facturas (id_factura, id_producto, cantidad, precio_unitario, subtotal) VALUES
    (1, 1, 1,  3250.00,  3250.00),
    (1, 3, 2,   950.00,  1900.00),
    (2, 2, 1, 11500.00, 11500.00),
    (2, 4, 1,  1800.00,  1800.00),
    (3, 3, 3,   950.00,  2850.00),
    (3, 4, 2,  1800.00,  3600.00);


