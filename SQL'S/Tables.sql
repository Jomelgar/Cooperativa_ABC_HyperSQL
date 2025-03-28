CREATE SCHEMA COOPERATIVA;

CREATE TABLE IF NOT EXISTS COOPERATIVA.usuario_cliente(
	codigo_empleado VARCHAR(8) NOT NULL,
	id_usuario VARCHAR(100) UNIQUE NOT NULL,
	contrasena VARCHAR(1000)  NOT NULL,
	rol BOOLEAN NOT NULL,
	estado BOOLEAN DEFAULT TRUE,
	fecha_nacimiento DATE,
	primer_nombre VARCHAR(100) NOT NULL,
	segundo_nombre VARCHAR(100),
	primer_apellido VARCHAR(100) NOT NULL,
	segundo_apellido VARCHAR(100),
	referencia VARCHAR(100),
	ciudad VARCHAR(100),
	avenida VARCHAR(100),
	casa VARCHAR(100),
	departamento VARCHAR(100),
	calle VARCHAR(100),
	correo_primario VARCHAR(100) NOT NULL,
	correo_secundario VARCHAR(100),
	fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	usuario_creador VARCHAR(8) DEFAULT current_user,
	usuario_modificador VARCHAR(8),
	PRIMARY KEY(codigo_empleado)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.usuario_telefono(
	telefonos VARCHAR(8) NOT NULL,
	codigo_empleado VARCHAR(8) NOT NULL,
	PRIMARY KEY(codigo_empleado, telefonos),
	FOREIGN KEY(codigo_empleado) REFERENCES COOPERATIVA.usuario_cliente(codigo_empleado)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.cuenta(
	numero_cuenta VARCHAR(12)  NOT NULL,
	codigo_empleado VARCHAR(8) NOT NULL,
	estado BOOLEAN DEFAULT TRUE,
	saldo DECIMAL(10,2) DEFAULT 0,
	pendiente DECIMAL(10,2) DEFAULT 0,
	tipo VARCHAR(12) NOT NULL,
	fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	fecha_ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	usuario_creador VARCHAR(8),
	usuario_modificador VARCHAR(8),
	PRIMARY KEY(numero_cuenta),
	FOREIGN KEY(codigo_empleado) REFERENCES COOPERATIVA.usuario_cliente(codigo_empleado)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.transaccion(
	codigo_transaccion VARCHAR(100) NOT NULL,
	monto DECIMAL(10,2) DEFAULT 0,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	comentario VARCHAR(256),
	numero_cuenta VARCHAR(12) NOT null,
	PRIMARY KEY(codigo_transaccion),
	FOREIGN KEY(numero_cuenta) REFERENCES COOPERATIVA.cuenta(numero_cuenta)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.liquidacion(
	numero_liquidacion VARCHAR(9) NOT NULL,
	monto DECIMAL(10,2) DEFAULT 0,
	fecha_de_retiro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	tipo VARCHAR(8) NOT NULL,
	codigo_empleado VARCHAR(8) NOT NULL,
	PRIMARY KEY(numero_liquidacion),
	FOREIGN KEY(codigo_empleado) REFERENCES COOPERATIVA.usuario_cliente(codigo_empleado)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.transaccion_liquidacion
(
	codigo_transaccion VARCHAR(100),
	numero_liquidacion VARCHAR(9) NOT NULL,
	PRIMARY KEY(codigo_transaccion),
	FOREIGN KEY(codigo_transaccion) REFERENCES COOPERATIVA.transaccion(codigo_transaccion),
	FOREIGN KEY(numero_liquidacion) REFERENCES COOPERATIVA.liquidacion(numero_liquidacion)
);


CREATE TABLE IF NOT EXISTS COOPERATIVA.prestamo(
	numero_prestamos VARCHAR(16)  not NULL,
	monto DECIMAL(10,2) NOT NULL ,
	tipo VARCHAR(11) NOT NULL,
	fecha_de_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	estado BOOLEAN DEFAULT TRUE,
	saldo DECIMAL(10,2) DEFAULT 0,
	periodos INT DEFAULT 12,
	codigo_empleado VARCHAR(8) NOT NULL,
	fecha_ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	usuario_creador VARCHAR(8),
	usuario_modificador VARCHAR(8),
	PRIMARY KEY(numero_prestamos),
	FOREIGN KEY(codigo_empleado) REFERENCES COOPERATIVA.usuario_cliente(codigo_empleado)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.pagos(
	numero_de_pago VARCHAR(5), 
	capital DECIMAL(10,2),
	interes DOUBLE,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	numero_prestamos VARCHAR(16),
	PRIMARY KEY(numero_de_pago,numero_prestamos),
	FOREIGN KEY(numero_prestamos) REFERENCES COOPERATIVA.prestamo(numero_prestamos)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.liquidacion_pagos
(
	numero_prestamos VARCHAR(16),
	numero_de_pago VARCHAR(5),
	numero_liquidacion VARCHAR(9),
	PRIMARY KEY (numero_prestamos,numero_de_pago),
	FOREIGN KEY(numero_prestamos,numero_de_pago) REFERENCES COOPERATIVA.pagos(numero_prestamos,numero_de_pago),
	FOREIGN KEY(numero_liquidacion) REFERENCES COOPERATIVA.liquidacion(numero_liquidacion)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.cierre
(
	id_cierre INTEGER PRIMARY KEY,
	fecha DATE DEFAULT CURRENT_DATE NOT NULL,
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.cierre_pagos
(
	numero_prestamos VARCHAR(16),
	numero_de_pago VARCHAR(5),
	id_cierre INTEGER,
	PRIMARY KEY (numero_prestamos,numero_de_pago),
	FOREIGN KEY(numero_prestamos,numero_de_pago) REFERENCES COOPERATIVA.pagos(numero_prestamos,numero_de_pago),
	FOREIGN KEY(id_cierre) REFERENCES COOPERATIVA.cierre(id_cierre)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.cierre_transaccion
(
	codigo_transaccion VARCHAR(100),
	id_cierre INTEGER,
	PRIMARY KEY (codigo_transaccion),
	FOREIGN KEY(codigo_transaccion) REFERENCES COOPERATIVA.transaccion(codigo_transaccion),
	FOREIGN KEY(id_cierre) REFERENCES  COOPERATIVA.cierre(id_cierre)
);

CREATE TABLE IF NOT EXISTS COOPERATIVA.dividendos
(
	id_cierre INTEGER,
	codigo_empleado VARCHAR(8),
    nombre VARCHAR(200),
    saldo DECIMAL(100,2),
	ganancias DECIMAL(100,2),
	estado BOOLEAN DEFAULT TRUE,
	porcentaje DOUBLE,
	FOREIGN KEY(codigo_empleado) REFERENCES COOPERATIVA.usuario_cliente(codigo_empleado),
	FOREIGN KEY(id_cierre) REFERENCES  COOPERATIVA.cierre(id_cierre)
);

CREATE SEQUENCE IF NOT EXISTS COOPERATIVA.codigo_empleado_seq
	START WITH 1
	INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS COOPERATIVA.liquidacion_seq
	START WITH 1
	INCREMENT BY 1;
