CREATE FUNCTION COOPERATIVA.emp_generador()
RETURNS VARCHAR(8)
CONTAINS SQL
BEGIN ATOMIC
	RETURN CONCAT('AF-', LPAD(NEXT VALUE FOR COOPERATIVA.codigo_empleado_seq, 5, '0'));
END;

CREATE FUNCTION COOPERATIVA.conseguir_num_abono(numero VARCHAR(12))
RETURNS BIGINT
READS SQL DATA
BEGIN ATOMIC
	DECLARE
	num_abono BIGINT;
	SET num_abono = SELECT count(*)
	FROM COOPERATIVA.transaccion
	WHERE numero_cuenta = numero;
	RETURN num_abono + 1;
END;

CREATE FUNCTION COOPERATIVA.conseguir_num_pago(numero VARCHAR(16))
RETURNS BIGINT
READS SQL DATA
BEGIN ATOMIC
	DECLARE
	num_pago BIGINT;
	SET num_pago = SELECT count(*)
	FROM COOPERATIVA.pagos
	WHERE numero_prestamos = numero;
	RETURN num_pago + 1;
END;

CREATE FUNCTION COOPERATIVA.conseguir_num_prestamo(numero VARCHAR(8))
RETURNS BIGINT
READS SQL DATA
BEGIN ATOMIC
	DECLARE
	num_prestamo BIGINT;
	SET num_prestamo = SELECT count(*)
	FROM COOPERATIVA.prestamo
	WHERE codigo_empleado = numero;
	RETURN num_prestamo + 1;
END;

CREATE FUNCTION COOPERATIVA.contar_prestamos(codigo_emp VARCHAR(8))
RETURNS int
READS SQL DATA
BEGIN ATOMIC
DECLARE counts int;
SELECT COALESCE(COUNT(*), 0) INTO counts
FROM (COOPERATIVA.usuario_cliente c INNER JOIN COOPERATIVA.prestamo  p 
ON p.codigo_empleado = c.codigo_empleado)  WHERE c.codigo_empleado = codigo_emp and p.estado = true;
	RETURN counts;
END;

CREATE FUNCTION COOPERATIVA.calcular_pmt(
    capital DECIMAL(10,2), 
    tipo VARCHAR(10), 
    meses DECIMAL(10,6)
) RETURNS DECIMAL(10,2)
BEGIN ATOMIC
    DECLARE pmt DECIMAL(20,6);
	
    SET pmt = CASE 
        WHEN tipo = 'Fiduciario' THEN 
            TRUNC((capital * (0.1500 / meses)) / 
                 (1.00000 - POWER(1 + (0.1500 / meses), -meses)), 2)
        WHEN tipo = 'Automatico' THEN 
            TRUNC((capital * (0.1000 / meses)) / 
                 (1.00000 - POWER(1 + (0.1000 / meses), -meses)), 2)
        ELSE 0
    END CASE;
    
    RETURN pmt;
END;

CREATE FUNCTION COOPERATIVA.calcular_ipmt(
    pendiente DECIMAL(10,2), 
    tipo VARCHAR(10), 
    meses INT
) RETURNS DECIMAL(10,2)
BEGIN ATOMIC
    DECLARE tasa_mensual DOUBLE;
    DECLARE ipmt DECIMAL(10,2);

    SET tasa_mensual = CASE 
        WHEN tipo = 'Fiduciario' THEN 0.15000/meses
        WHEN tipo = 'Automatico' THEN  0.1000/meses  
        ELSE 0
    END;

    SET ipmt = pendiente * tasa_mensual;

    RETURN ipmt;
END;

CREATE FUNCTION COOPERATIVA.calcular_ppmt(capital DECIMAL(10,2), saldo DECIMAL(10,2),tipo VARCHAR(10), meses INT) 
RETURNS DECIMAL(10,2)
BEGIN ATOMIC
    DECLARE pmt DECIMAL(10,2);
    DECLARE ipmt DECIMAL(10,2);
    DECLARE ppmt DECIMAL(10,2);

    SET pmt = COOPERATIVA.calcular_pmt(capital, tipo, meses);
    SET ipmt = COOPERATIVA.calcular_ipmt(saldo, tipo, meses);
    SET ppmt = pmt - ipmt;
    RETURN ppmt;
END;



CREATE FUNCTION COOPERATIVA.conseguir_prestamo(code_emp VARCHAR(8))
RETURNS TABLE
(
	monto decimal(10,2),
	saldo decimal(10,2),
	fecha date,
	numero_prestamos VARCHAR(16),
	periodos int,
	tipo VARCHAR(10)
)
READS SQL DATA
BEGIN ATOMIC
	RETURN TABLE(SELECT monto,saldo,fecha_de_creacion,numero_prestamos,periodos,tipo
FROM COOPERATIVA.usuario_cliente c INNER JOIN COOPERATIVA.prestamo  p 
ON p.codigo_empleado = c.codigo_empleado WHERE c.codigo_empleado = code_emp AND p.estado = true);
END;

CREATE FUNCTION COOPERATIVA.obtener_usuario_por_id(usuario VARCHAR(100))
RETURNS TABLE (
    codigo_empleado VARCHAR(8),
    id_usuario VARCHAR(100),
    contrasena VARCHAR(1000),
    rol BOOLEAN,
    estado BOOLEAN,
    fecha_nacimiento DATE,
    primer_nombre VARCHAR(100),
    segundo_nombre VARCHAR(100),
    primer_apellido VARCHAR(100),
    segundo_apellido VARCHAR(100),
    referencia VARCHAR(100),
    ciudad VARCHAR(100),
    avenida VARCHAR(100),
    casa VARCHAR(100),
    departamento VARCHAR(100),
    calle VARCHAR(100),
    correo_primario VARCHAR(100),
    correo_secundario VARCHAR(100),
    fecha_de_creacion TIMESTAMP,
    fecha_ultima_actualizacion TIMESTAMP,
    usuario_creador VARCHAR(8),
    usuario_modificador VARCHAR(8)
)
READS SQL DATA
BEGIN ATOMIC
    RETURN TABLE
        (SELECT 
           *
        FROM COOPERATIVA.usuario_cliente
        WHERE id_usuario = usuario);
    
END;

CREATE FUNCTION COOPERATIVA.obtener_usuario_telefono(codigo_emp VARCHAR(8))
RETURNS TABLE
(
	codigo_empleado VARCHAR(8), telefonos VARCHAR(8)
)
READS SQL DATA
BEGIN ATOMIC 
	RETURN TABLE (SELECT * FROM COOPERATIVA.usuario_telefono WHERE codigo_empleado = codigo_emp);
END;

CREATE FUNCTION COOPERATIVA.obtener_cuenta(codigo_emp VARCHAR(8))
RETURNS TABLE
(
	numero_cuenta VARCHAR(12),
	saldo DECIMAL(10,2)
)
READS SQL DATA
BEGIN ATOMIC
	RETURN TABLE (SELECT numero_cuenta, saldo-pendiente FROM COOPERATIVA.cuenta WHERE codigo_empleado = codigo_emp);
END;

CREATE FUNCTION COOPERATIVA.ingresos_por_cierre(cierre int)
RETURNS TABLE
(
	empleado VARCHAR(8),
	nombre VARCHAR(201),
	ingresos_totales decimal(10,2)
)
READS SQL DATA
BEGIN ATOMIC
 RETURN TABLE (SELECT u.codigo_empleado, 
           COALESCE(u.primer_nombre || ' ', '') ||COALESCE(u.primer_apellido,''), 
           SUM(c.saldo)
    FROM COOPERATIVA.cuenta c INNER JOIN COOPERATIVA.usuario_cliente u ON u.codigo_empleado = c.codigo_empleado 
    WHERE u.estado = TRUE AND c.tipo = 'Aportaciones'
    GROUP BY u.codigo_empleado, u.primer_nombre, u.primer_apellido);
END;

CREATE FUNCTION COOPERATIVA.dividendos_anio(anio INT)
RETURNS TABLE
(
	codigo_empleado VARCHAR(8),
	fecha date,
	nombre VARCHAR(201),
	saldo DECIMAL(10,2),
	porcentaje DECIMAL(10,2),
	ganancia DECIMAL(10,2)
)
READS SQL DATA
BEGIN ATOMIC
	RETURN TABLE(SELECT d.codigo_empleado,c.fecha,d.nombre,d.saldo,
	CASE WHEN sum(d.ganancias) = 0 THEN 0 ELSE 100 * d.ganancias / SUM(d.ganancias) 
    END AS porcentaje,sum(d.ganancias) AS ganancias FROM COOPERATIVA.dividendos d INNER JOIN COOPERATIVA.cierre c ON 
    c.id_cierre = d.id_cierre WHERE YEAR(c.fecha) = anio
	GROUP BY d.codigo_empleado, c.fecha, d.nombre, d.saldo, d.ganancias);
END;

CREATE FUNCTION COOPERATIVA.nuevas_afiliaciones(anio int)
RETURNS TABLE
(
	codigo_empleado VARChar(8),
	nombre VARCHAR(201),
	fecha_afiliacion DATE,
	inversion DECIMAL(10,2),
	ahorro DECIMAL(10,2),
	total DECIMAL(10,2)
)
READS SQL DATA
BEGIN ATOMIC
	RETURN TABLE
	(SELECT u.codigo_empleado,u.primer_nombre || ' ' || u.primer_apellido, u.fecha_de_creacion, 
	d.SALDO_1, d.SALDO_2, d.SALDO_1 + d.SALDO_2
	FROM (SELECT
	c1.CODIGO_EMPLEADO,c1.SALDO AS SALDO_1,c2.SALDO AS SALDO_2 
	FROM COOPERATIVA.CUENTA c1 JOIN COOPERATIVA.CUENTA c2 
    ON c1.CODIGO_EMPLEADO = c2.CODIGO_EMPLEADO AND c1.NUMERO_CUENTA < c2.NUMERO_CUENTA) d 
	INNER JOIN COOPERATIVA.usuario_cliente u ON d.codigo_empleado = u.codigo_empleado 
	WHERE YEAR(u.fecha_de_creacion) = anio);
END;

CREATE FUNCTION COOPERATIVA.estado_cuenta(anio int, cuenta VARCHAR(12))
RETURNS TABLE
(
    codigo_transaccion VARCHAR(100),
    monto DECIMAL(10,2),
    comentario TIMESTAMP,
    descripcion VARCHAR(256)
)
READS SQL DATA
BEGIN ATOMIC
DECLARE usuario VARCHAR(8);
SELECT codigo_empleado INTO usuario FROM COOPERATIVA.cuenta WHERE numero_cuenta = cuenta;

RETURN TABLE
(
	SELECT * FROM
    (SELECT codigo_transaccion,monto,fecha,comentario FROM COOPERATIVA.transaccion WHERE cuenta = numero_cuenta AND anio = EXTRACT(YEAR FROM fecha) 
    UNION SELECT 'Cierre '|| c.id_cierre, d.ganancias, c.fecha, 'Ganancias del usuario.' FROM 
    COOPERATIVA.dividendos d INNER JOIN COOPERATIVA.cierre c ON d.id_cierre = c.id_cierre WHERE anio = EXTRACT(YEAR FROM c.fecha)
    AND usuario = codigo_empleado UNION 
    SELECT p.numero_prestamos || p.numero_de_pago,capital + interes, fecha, 'Pago para el prestamo '|| p.numero_prestamos
    FROM COOPERATIVA.prestamo pr INNER JOIN COOPERATIVA.pagos p ON p.numero_prestamos = pr.numero_prestamos INNER JOIN 
    COOPERATIVA.cierre_pagos cr ON p.numero_de_pago = cr.numero_de_pago INNER JOIN COOPERATIVA.cierre c ON cr.id_cierre = c.id_cierre
    WHERE anio = EXTRACT(YEAR FROM c.fecha) AND usuario = pr.codigo_empleado
    ) ORDER BY fecha
);
END;


