CREATE TRIGGER usuario_cliente_tia
AFTER INSERT ON COOPERATIVA.usuario_cliente
REFERENCING NEW ROW AS oldrow
FOR EACH ROW
BEGIN ATOMIC
  INSERT INTO COOPERATIVA.cuenta(numero_cuenta,codigo_empleado,tipo,usuario_creador)VALUES (CONCAT(oldrow.codigo_empleado,'-CAR'),oldrow.codigo_empleado,'Retirable',oldrow.codigo_empleado);
  INSERT INTO COOPERATIVA.cuenta(numero_cuenta,codigo_empleado,tipo,usuario_creador)  VALUES (CONCAT(oldrow.codigo_empleado,'-CAP'),oldrow.codigo_empleado,'Aportaciones',oldrow.codigo_empleado);
END;

CREATE TRIGGER usuario_cliente_tib
BEFORE INSERT ON COOPERATIVA.usuario_cliente
REFERENCING NEW ROW AS newrow
FOR EACH ROW
BEGIN ATOMIC
  SET newrow.codigo_empleado = CONCAT('AF-', LPAD(NEXT VALUE FOR COOPERATIVA.codigo_empleado_seq, 5, '0'));
END;

CREATE TRIGGER liquidacion_tib
BEFORE INSERT ON COOPERATIVA.liquidacion
REFERENCING NEW ROW AS newrow
FOR EACH ROW
BEGIN ATOMIC
	SET newrow.numero_liquidacion = CONCAT('LIQ-', LPAD(NEXT VALUE FOR COOPERATIVA.liquidacion_seq, 5,'0'));
END;

CREATE TRIGGER  transaccion_tib
BEFORE INSERT ON COOPERATIVA.transaccion
REFERENCING NEW ROW AS newrow
FOR EACH ROW 
BEGIN ATOMIC
	SET  newrow.codigo_transaccion = CONCAT(newrow.numero_cuenta, CONCAT('-',COOPERATIVA.conseguir_num_abono(newrow.numero_cuenta)));
END;

CREATE TRIGGER prestamo_tib
BEFORE INSERT ON COOPERATIVA.prestamo
REFERENCING NEW ROW AS newrow
FOR EACH ROW 
BEGIN ATOMIC
	SET newrow.numero_prestamos = CONCAT(newrow.codigo_empleado, CONCAT('-PT',COOPERATIVA.conseguir_num_prestamo(newrow.codigo_empleado)));
END;

CREATE TRIGGER pagos_tib
BEFORE INSERT ON COOPERATIVA.pagos
REFERENCING NEW ROW AS newrow
FOR EACH ROW 
BEGIN ATOMIC
    SET newrow.numero_de_pago = RIGHT(CONCAT('00000', COOPERATIVA.conseguir_num_pago(newrow.numero_prestamos)),5);
END;

CREATE TRIGGER pagos_tr_ia
AFTER INSERT ON COOPERATIVA.pagos
REFERENCING NEW ROW AS nrow
FOR EACH ROW
BEGIN ATOMIC
	DECLARE cierre INT;
	SET cierre = (SELECT ID_CIERRE FROM COOPERATIVA.CIERRE ORDER BY FECHA DESC LIMIT 1);
	INSERT INTO COOPERATIVA.cierre_pagos(numero_prestamos,numero_de_pago,id_cierre) VALUES(nrow.numero_prestamos, nrow.numero_de_pago,cierre);
END;

CREATE TRIGGER COOPERATIVA.cierre_tr_ib
BEFORE INSERT ON COOPERATIVA.cierre
REFERENCING NEW ROW AS nrow
FOR EACH ROW 
BEGIN ATOMIC 
    SET nrow.fecha = SELECT 
    COALESCE(dateadd('month',1,MAX(fecha)),CURRENT_DATE)
	FROM COOPERATIVA.CIERRE;
	SET nrow.id_cierre = SELECT COALESCE(MAX(id_cierre) + 1,1) FROM cooperativa.cierre;
END;

INSERT INTO COOPERATIVA.CIERRE(fecha) VALUES (CURRENT_DATE);

INSERT INTO COOPERATIVA.usuario_cliente(ID_USUARIO,CONTRASENA,ROL,PRIMER_NOMBRE,PRIMER_APELLIDO,CORREO_PRIMARIO)
VALUES ('Admin','clave123',TRUE,'Super','Admin','SuperAdmin@gmail.com');
CALL COOPERATIVA.crear_prestamo(6000,1,'Fiduciario','AF-00001');


INSERT INTO COOPERATIVA.usuario_cliente(ID_USUARIO, CONTRASENA, ROL, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, CORREO_PRIMARIO, CIUDAD, AVENIDA, CASA, DEPARTAMENTO, CALLE)
VALUES ('mlopez92', 'M@rLo123', TRUE, 'Marcos', 'Antonio', 'López', 'Fernández', 'marcos.lopez92@gmail.com', 'Tegucigalpa', 'Av. Central', 'Casa 23B', 'Edificio Mirador', 'Calle 5');
CALL COOPERATIVA.crear_prestamo(5000,7,'Fiduciario','AF-00002');

INSERT INTO COOPERATIVA.usuario_cliente(ID_USUARIO, CONTRASENA, ROL, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, CORREO_PRIMARIO, CIUDAD, AVENIDA, CASA, DEPARTAMENTO, CALLE)
VALUES ('sandra_gomez', 'SanG@2024!', FALSE, 'Sandra', NULL, 'Gómez', 'Hernández', 'sandra.gomez@outlook.com', 'San Pedro Sula', 'Boulevard Morazán', 'Apto 304', NULL, 'Calle Los Almendros');
CALL COOPERATIVA.crear_prestamo(1000,7,'Fiduciario','AF-00003');

INSERT INTO COOPERATIVA.usuario_cliente(ID_USUARIO, CONTRASENA, ROL, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, CORREO_PRIMARIO, CIUDAD, AVENIDA, CASA, DEPARTAMENTO, CALLE)
VALUES ('roberto_hernandez99', 'RoberH99$', TRUE, 'Roberto', 'José', 'Hernández', 'Pineda', 'roberto.h99@yahoo.com', 'La Ceiba', 'Col. El Naranjal', 'Casa #14', NULL, 'Calle Principal');
CALL COOPERATIVA.crear_prestamo(2000,7,'Fiduciario','AF-00004');

INSERT INTO COOPERATIVA.usuario_cliente(ID_USUARIO, CONTRASENA, ROL, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, CORREO_PRIMARIO, CIUDAD, AVENIDA, CASA, DEPARTAMENTO, CALLE)
VALUES ('karla_mendoza24', 'KMendoza*24', FALSE, 'Karla', 'Isabel', 'Mendoza', 'Castro', 'karla.mendoza24@hotmail.com', 'Choluteca', 'Zona Sur', 'Residencial Las Palmas', NULL, 'Calle 8');
CALL COOPERATIVA.crear_prestamo(10000,7,'Fiduciario','AF-00005');