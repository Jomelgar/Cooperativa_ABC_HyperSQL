CREATE PROCEDURE COOPERATIVA.crear_usuario(codigo_empleado VARCHAR(8),
	id_usuario VARCHAR(100),
	contrasena VARCHAR(1000),
	rol BOOLEAN,
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
	creador VARCHAR(8))
MODIFIES SQL DATA
BEGIN ATOMIC
	INSERT INTO COOPERATIVA.usuario_cliente(codigo_empleado, id_usuario, contrasena, rol, fecha_nacimiento, 
        primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, referencia, 
        ciudad, avenida, casa, departamento, calle, 
        correo_primario, correo_secundario,usuario_creador) VALUES
	(codigo_empleado, id_usuario, contrasena, rol, fecha_nacimiento, 
        primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, referencia, 
        ciudad, avenida, casa, departamento, calle, 
        correo_primario, correo_secundario, creador);
END;

CREATE PROCEDURE COOPERATIVA.actualizar_usuario(
	codigo_emp VARCHAR(8),
    usuario VARCHAR(100),
    contra VARCHAR(1000),
    rol_ BOOLEAN,
    fecha_n DATE,
    primer_n VARCHAR(100),
    segundo_n VARCHAR(100),
    primer_a VARCHAR(100),
    segundo_a VARCHAR(100),
    refe VARCHAR(100),
    ciudad_ VARCHAR(100),
    ave VARCHAR(100),
    casa_ VARCHAR(100),
    depa VARCHAR(100),
    calle_ VARCHAR(100),
    correo_p VARCHAR(100),
    correo_s VARCHAR(100),
    modificador VARCHAR(8)
)
MODIFIES SQL DATA
BEGIN ATOMIC
    UPDATE COOPERATIVA.usuario_cliente
    SET 
        contrasena = contra,
        rol = rol_,
        fecha_nacimiento = fecha_n,
        primer_nombre = primer_n,
        segundo_nombre = segundo_n,
        primer_apellido = primer_a,
        segundo_apellido = segundo_a,
        referencia = refe,
        ciudad = ciudad_,
        avenida = ave,
        casa = casa_,
        departamento = depa,
        calle = calle_,
        correo_primario = correo_p,
        correo_secundario = correo_s,
        usuario_modificador = modificador,
		fecha_ultima_actualizacion = CURRENT_TIMESTAMP
        WHERE codigo_empleado = codigo_emp;

END;

CREATE PROCEDURE COOPERATIVA.crear_usuario_telefono(codigo_emp VARCHAR(8), telef VARCHAR(8))
MODIFIES SQL DATA
BEGIN ATOMIC
	INSERT INTO COOPERATIVA.usuario_telefono(codigo_empleado,telefonos) VALUES (codigo_emp, telef);
END;

CREATE PROCEDURE COOPERATIVA.crear_transaccion(
    num_cuenta VARCHAR(12),
    sald DECIMAL(10,2),
    descrip VARCHAR(256)
)
MODIFIES SQL DATA
BEGIN ATOMIC
    DECLARE func VARCHAR(100) DEFAULT '';
    DECLARE cierre INT;
    DECLARE pend DECIMAL(10,2);
    DECLARE saldo_actual DECIMAL(10,2);

    INSERT INTO COOPERATIVA.transaccion(monto, comentario, numero_cuenta) 
    VALUES (sald, descrip, num_cuenta);

    SELECT pendiente, saldo INTO pend, saldo_actual
    FROM COOPERATIVA.cuenta 
    WHERE numero_cuenta = num_cuenta;
    
    IF sald >= 0 THEN
        IF pend > 0 THEN
            IF sald >= pend THEN
                UPDATE COOPERATIVA.cuenta 
                SET pendiente = 0, saldo = saldo + (sald - pend)
                WHERE numero_cuenta = num_cuenta;
            ELSE
                UPDATE COOPERATIVA.cuenta 
                SET pendiente = pendiente - sald
                WHERE numero_cuenta = num_cuenta;
            END IF;
        ELSE
            UPDATE COOPERATIVA.cuenta 
            SET saldo = saldo + sald
            WHERE numero_cuenta = num_cuenta;
        END IF;
        
    ELSE
        IF saldo_actual > 0 THEN
            IF -sald >= saldo_actual THEN
                UPDATE COOPERATIVA.cuenta 
                SET saldo = 0, pendiente = pendiente - (sald - saldo_actual)
                WHERE numero_cuenta = num_cuenta;
            ELSE
                UPDATE COOPERATIVA.cuenta 
                SET saldo = saldo + sald
                WHERE numero_cuenta = num_cuenta;
            END IF;
        ELSE
            UPDATE COOPERATIVA.cuenta 
            SET pendiente = pendiente + sald
            WHERE numero_cuenta = num_cuenta;
        END IF;
    END IF;

    SET func = (
        SELECT codigo_transaccion
        FROM COOPERATIVA.transaccion 
        WHERE numero_cuenta = num_cuenta 
        ORDER BY fecha DESC 
        LIMIT 1
    );

    SET cierre = (
        SELECT id_cierre 
        FROM COOPERATIVA.cierre 
        ORDER BY fecha DESC 
        LIMIT 1
    );

    INSERT INTO COOPERATIVA.cierre_transaccion(codigo_transaccion, id_cierre) 
    VALUES (func, cierre);
    
END;


CREATE PROCEDURE COOPERATIVA.modificar_cuenta(num_cuenta VARCHAR(12), sald DECIMAL(10,2), cod_emp VARCHAR(8))
MODIFIES SQL DATA
BEGIN ATOMIC
	UPDATE COOPERATIVA.cuenta c 
	SET c.saldo = sald, c.fecha_ultima_actualizacion = CURRENT_TIMESTAMP, c.usuario_modificador = cod_emp 
	WHERE c.numero_cuenta = num_cuenta;
END;

CREATE PROCEDURE COOPERATIVA.crear_prestamo(mont DECIMAL(10,2),per INT, tip VARCHAR(10), cod_emp VARCHAR(8))
MODIFIES SQL DATA 
BEGIN ATOMIC
	INSERT INTO COOPERATIVA.prestamo(monto,saldo,periodos,tipo,codigo_empleado,usuario_creador) VALUES(mont,mont,per,tip,cod_emp,cod_emp);
END;

CREATE PROCEDURE COOPERATIVA.crear_pago(
    num_prest VARCHAR(16),
    monto DECIMAL(10,2),
    saldo DECIMAL(10,2),
    tipo VARCHAR(10),
    meses INT,
    usuario VARCHAR(8)
)
MODIFIES SQL DATA
BEGIN ATOMIC
    DECLARE pmt DECIMAL(10,2);
    DECLARE ipmt DECIMAL(10,2);
    DECLARE ppmt DECIMAL(10,2);
	DECLARE codigo VARCHAR(8);
	 DECLARE num VARCHAR(16);
IF meses - 1 > (SELECT COUNT(*) FROM COOPERATIVA.pagos WHERE num_prest = numero_prestamos) THEN
    
	SET pmt = COOPERATIVA.calcular_pmt(monto, tipo, meses);
    SET ipmt = COOPERATIVA.calcular_ipmt(saldo, tipo, meses);
    SET ppmt = COOPERATIVA.calcular_ppmt(monto,saldo, tipo, meses);

    INSERT INTO COOPERATIVA.pagos(numero_prestamos, capital, interes)
    VALUES (num_prest, ppmt, ipmt);
    UPDATE COOPERATIVA.prestamo
    SET saldo = saldo - ppmt,  
        fecha_ultima_actualizacion = CURRENT_TIMESTAMP,
        usuario_modificador = usuario
    WHERE numero_prestamos = num_prest;
    
ELSE

	SET pmt = COOPERATIVA.calcular_pmt(monto, tipo, meses);
    SET ipmt = COOPERATIVA.calcular_ipmt(saldo, tipo, meses);
    SET ppmt = COOPERATIVA.calcular_ppmt(monto,saldo, tipo, meses);
    INSERT INTO COOPERATIVA.pagos(numero_prestamos, capital, interes)
    VALUES (num_prest, ppmt, ipmt);
    UPDATE COOPERATIVA.prestamo
    SET saldo = saldo - ppmt,  
        fecha_ultima_actualizacion = CURRENT_TIMESTAMP,
        usuario_modificador = usuario
    WHERE numero_prestamos = num_prest;
    UPDATE COOPERATIVA.prestamo
       SET estado = FALSE,
            fecha_ultima_actualizacion = CURRENT_TIMESTAMP,
            usuario_modificador = CURRENT_USER
       WHERE numero_prestamos = num_prest;
    	
END IF;
    SELECT codigo_empleado into codigo FROM COOPERATIVA.prestamo where numero_prestamos = num_prest;
    SELECT numero_cuenta into num FROM COOPERATIVA.cuenta where codigo_empleado = codigo and tipo = 'Aportaciones';
    CALL COOPERATIVA.crear_transaccion(num, -pmt,'Pago de Prestamo ' || num_prest);
END;

CREATE PROCEDURE COOPERATIVA.crear_cierre_pagos(usuario VARCHAR(8))
MODIFIES SQL DATA
BEGIN ATOMIC
	FOR SELECT numero_prestamos,monto,saldo,tipo,periodos FROM COOPERATIVA.prestamo where estado = true DO
		CALL COOPERATIVA.crear_pago(numero_prestamos,monto,saldo,tipo,periodos,usuario);
    END FOR;
END;

CREATE PROCEDURE COOPERATIVA.crear_cierre_transaccion(cierre INT)
MODIFIES SQL DATA
BEGIN ATOMIC
		DECLARE ingresos_totales DECIMAL(10,2);
    FOR SELECT numero_cuenta AS num FROM COOPERATIVA.cuenta WHERE estado = true DO
		SELECT COALESCE(SUM(t.monto), 0) INTO ingresos_totales
		FROM COOPERATIVA.transaccion t INNER JOIN COOPERATIVA.cierre_transaccion c 
		ON c.codigo_transaccion = t.codigo_transaccion
        WHERE t.numero_cuenta = num
        AND c.id_cierre = cierre;
        IF ingresos_totales < 200 THEN 
                CALL COOPERATIVA.crear_transaccion(num, 200 - ingresos_totales, 'Transacción para cumplir condiciones de cuenta.');
        END IF;
    END FOR;
END;

CREATE PROCEDURE COOPERATIVA.hacer_cierre(usuario VARCHAR(8))
MODIFIES SQL DATA
BEGIN ATOMIC
	DECLARE cierre INT;
	DECLARE total DECIMAL(10,2);
	DECLARE suma DECIMAL(10,2);
	DECLARE porcentaje DOUBLE;
	SET cierre = SELECT id_cierre FROM cooperativa.cierre ORDER BY fecha LIMIT 1;
	
	CALL COOPERATIVA.crear_cierre_transaccion(cierre);
    CALL COOPERATIVA.crear_cierre_pagos(usuario);
	
	SET total = SELECT sum(ingresos_totales) FROM TABLE(COOPERATIVA.ingresos_por_cierre(cierre));
	SET suma = SELECT sum(interes) FROM COOPERATIVA.pagos p INNER JOIN COOPERATIVA.cierre_pagos c 
	ON p.numero_de_pago = c.numero_de_pago AND p.numero_prestamos = c.numero_prestamos WHERE id_cierre = cierre;
	
	FOR	 SELECT empleado,nombre,ingresos_totales FROM TABLE(COOPERATIVA.ingresos_por_cierre(cierre)) DO
		SET porcentaje = ingresos_totales / total;
		INSERT INTO COOPERATIVA.dividendos(id_cierre, codigo_empleado, nombre,ganancias, porcentaje,saldo) 
        VALUES (cierre, empleado, nombre, porcentaje*suma,porcentaje,ingresos_totales);
	END FOR;
	
	INSERT INTO COOPERATIVA.cierre(fecha) VALUES (CURRENT_DATE);
END;
	    
CREATE PROCEDURE COOPERATIVA.crear_liquidacion_parcial(
    IN cod_emp VARCHAR(8),
    IN retiro DECIMAL(10,2)
)
MODIFIES SQL DATA
BEGIN ATOMIC
    DECLARE func VARCHAR(100);
    DECLARE liq VARCHAR(9);
    DECLARE num_cuenta VARCHAR(12);
    DECLARE fech DATE;

    SET fech = (SELECT c.fecha FROM COOPERATIVA.cierre c ORDER BY c.fecha DESC LIMIT 1);

    SELECT numero_cuenta INTO num_cuenta 
    FROM COOPERATIVA.cuenta 
    WHERE codigo_empleado = cod_emp AND tipo = 'Retirable';

    IF num_cuenta IS NOT NULL AND MONTH(fech) = 12 AND 
       (SELECT saldo FROM COOPERATIVA.cuenta WHERE numero_cuenta = num_cuenta) - retiro >= 0 THEN

        INSERT INTO COOPERATIVA.liquidacion(monto, tipo, codigo_empleado) 
        VALUES (retiro, 'Parcial', cod_emp);

        SET liq = SELECT numero_liquidacion 
        FROM COOPERATIVA.liquidacion 
        WHERE codigo_empleado = cod_emp 
        ORDER BY fecha_de_retiro DESC LIMIT 1;

        CALL COOPERATIVA.crear_transaccion(num_cuenta, retiro * -1, 'Retiro Parcial');

        IF EXISTS (SELECT 1 FROM COOPERATIVA.transaccion WHERE numero_cuenta = num_cuenta) THEN
            SET func = SELECT codigo_transaccion 
            FROM COOPERATIVA.transaccion 
            WHERE numero_cuenta = num_cuenta 
            ORDER BY fecha DESC LIMIT 1;

            INSERT INTO COOPERATIVA.transaccion_liquidacion(codigo_transaccion, numero_liquidacion) 
            VALUES (func, liq);
        END IF;
	UPDATE COOPERATIVA.DIVIDENDOS SET estado = FALSE WHERE estado = TRUE AND codigo_empleado = cod_emp;
    END IF;
END;

CREATE PROCEDURE COOPERATIVA.crear_liquidacion_total(
    IN usuario VARCHAR(8),
    IN modificador VARCHAR(8),
    OUT total DECIMAL(10,2)
)
MODIFIES SQL DATA
BEGIN ATOMIC
    DECLARE v_numero_prestamos VARCHAR(16);
    DECLARE v_monto DECIMAL(10,2);
    DECLARE v_saldo DECIMAL(10,2);
    DECLARE v_tipo VARCHAR(10);
    DECLARE v_periodos INT;
	DECLARE v_estado BOOLEAN;
    DECLARE s_apo DECIMAL(10,2);
	DECLARE s_re DECIMAL(10,2);
    DECLARE c_apo VARCHAR(12);
    DECLARE c_re VARCHAR(12);
    DECLARE v_liq VARCHAR(9);
    DECLARE v_pago VARCHAR(5);
	DECLARE func VARCHAR(100);
    DECLARE v_dividendos DECIMAL(10,2) DEFAULT 0;
	DECLARE l_total DECIMAL(10,2);
    SELECT numero_cuenta, saldo INTO c_apo,s_apo FROM COOPERATIVA.cuenta WHERE codigo_empleado = usuario AND tipo = 'Aportaciones';
    SELECT numero_prestamos,monto,saldo,tipo,periodos,estado INTO v_numero_prestamos,v_monto,v_saldo,v_tipo,v_periodos,v_estado
    FROM COOPERATIVA.PRESTAMO p WHERE p.ESTADO = TRUE AND p.CODIGO_EMPLEADO = usuario;

IF  (SELECT COUNT(*) FROM COOPERATIVA.cuenta 
        WHERE codigo_empleado = usuario AND pendiente = 0) = 2 AND s_apo >= COOPERATIVA.calcular_pmt(v_monto,v_tipo,v_periodos)*v_periodos THEN
    --INICIALIZAR TODO
	SELECT numero_cuenta, saldo INTO c_re,s_re FROM COOPERATIVA.cuenta WHERE codigo_empleado = usuario AND tipo = 'Retirable'; 
	SET l_total = s_re + s_apo + (SELECT SUM(ganancias) FROM COOPERATIVA.dividendos WHERE codigo_empleado = usuario AND estado = TRUE);
    INSERt INTO COOPERATIVA.liquidacion(monto,tipo,codigo_empleado) VALUES (L_total,'Total',modificador);
	SET v_liq = SELECT numero_liquidacion FROM COOPERATIVA.liquidacion WHERE codigo_empleado = modificador ORDER BY fecha_de_retiro LIMIT 1; 
        
    while v_estado = TRUE DO
		CALL COOPERATIVA.crear_pago(v_numero_prestamos,v_monto,v_saldo,v_tipo,v_periodos, usuario);
    	SET v_pago = SELECT numero_de_pago FROM COOPERATIVA.PAGOS WHERE v_numero_prestamos = numero_prestamos ORDER BY interes asc LIMIT 1;
    	INSERT INTO COOPERATIVA.liquidacion_pagos(numero_de_pago,numero_prestamos,numero_liquidacion) VALUES (v_pago,v_numero_prestamos,v_liq);
    	SELECT estado INTO v_estado
    	FROM COOPERATIVA.PRESTAMO p WHERE p.NUMERO_PRESTAMOS = v_numero_prestamos AND p.CODIGO_EMPLEADO = usuario;
    END WHILE;
    
    --Sacar dinero de las cuentas
 	CALL COOPERATIVA.crear_transaccion(c_re,-s_re,'Liquidacion Total');
 	SET func = SELECT codigo_transaccion FROM COOPERATIVA.transaccion WHERE numero_cuenta = c_re ORDER BY fecha DESC LIMIT 1;
	INSERT INTO COOPERATIVA.transaccion_liquidacion(codigo_transaccion, numero_liquidacion) VALUES (func, v_liq);
    CALL COOPERATIVA.crear_transaccion(c_apo,-s_apo,'Liquidacion Total');
 	SET func = SELECT codigo_transaccion FROM COOPERATIVA.transaccion WHERE numero_cuenta = c_apo ORDER BY fecha DESC LIMIT 1;
	INSERT INTO COOPERATIVA.transaccion_liquidacion(codigo_transaccion, numero_liquidacion) VALUES (func, v_liq);
            
	UPDATE COOPERATIVA.usuario_cliente SET estado = FALSE WHERE codigo_empleado = usuario;
    UPDATE COOPERATIVA.cuenta SET estado = FALSE WHERE codigo_empleado = usuario;
    UPDATE COOPERATIVA.dividendos SET estado = FALSE WHERE codigo_empleado = usuario;
	SET total = l_total;
END IF;
END;
