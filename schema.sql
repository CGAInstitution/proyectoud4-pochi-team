DROP DATABASE IF EXISTS HEALTH_DATABASE;
CREATE DATABASE IF NOT EXISTS HEALTH_DATABASE;

use HEALTH_DATABASE;

CREATE TABLE enfermedades (
                            id bigint NOT NULL PRIMARY KEY,
                            nombre varchar(500) NOT NULL,
                            descripcion varchar(500),
                            peligrosidad smallint,
                            contagiable boolean NOT NULL
);


CREATE TABLE medicamentos (
                             id bigint NOT NULL PRIMARY KEY,
                             nombre varchar(500) NOT NULL,
                             descripcion varchar(500),
                             precio int default 0,
                             recetable boolean NOT NULL DEFAULT true
);


CREATE TABLE enfermedades_medicamentos (
                                        id bigint NOT NULL PRIMARY KEY,
                                        id_enfermedad bigint NOT NULL,
                                        id_medicamento bigint NOT NULL,
                                        FOREIGN KEY (id_enfermedad) references enfermedades(id)
											on delete cascade 
											on update cascade,
                                        FOREIGN KEY (id_medicamento) references medicamentos(id) 
											on delete cascade 
											on update cascade
);


CREATE TABLE tarjetas (
                         id bigint NOT NULL PRIMARY KEY,
                         tarjeta_banco varchar(500) NOT NULL,
                         recaudado bigint NOT NULL DEFAULT 0
);


CREATE TABLE pacientes (
                          id bigint NOT NULL PRIMARY KEY,
                          NSS tinyint NOT NULL,
                          edad int,
                          nombre varchar(500) NOT NULL,
                          tarjeta bigint NOT NULL,
                          enfermedad bigint NOT NULL,
                          imagen varchar(500) NULL,
						  FOREIGN KEY (enfermedad) references enfermedades(id) 
							on delete cascade 
							on update cascade,
						  FOREIGN KEY (tarjeta) references tarjetas(id) 
							on delete cascade 
							on update cascade
                          
);


CREATE TABLE usuario_data (
                              id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              email varchar(500) NOT NULL,
                              nombre varchar(500) NOT NULL,
                              password varchar(500) NOT NULL,
                              fecha_nacimiento date,
                              donado bigint NOT NULL DEFAULT 0,
                              admin boolean NOT NULL DEFAULT false,
                              bloqueado boolean NOT NULL DEFAULT false
);

INSERT INTO enfermedades (id, nombre, descripcion, peligrosidad, contagiable) VALUES
(1, 'Gripe', 'Infección viral que afecta las vías respiratorias', 3, true),
(2, 'Diabetes', 'Enfermedad crónica que afecta los niveles de azúcar en sangre', 5, false),
(3, 'COVID-19', 'Infección viral que afecta las vías respiratorias y otros órganos', 5, true),
(4, 'Hipertensión', 'Presión arterial alta', 4, false),
(5, 'Varicela', 'Infección viral que causa erupciones cutáneas y fiebre', 4, true);

INSERT INTO medicamentos (id, nombre, descripcion, precio, recetable) VALUES
(1, 'Paracetamol', 'Medicamento para aliviar el dolor y la fiebre', 50, true),
(2, 'Insulina', 'Hormona para tratar la diabetes', 300, true),
(3, 'Amoxicilina', 'Antibiótico para tratar infecciones bacterianas', 100, true),
(4, 'Ibuprofeno', 'Medicamento antiinflamatorio', 75, true),
(5, 'Remdesivir', 'Antiviral utilizado en casos graves de COVID-19', 2000, true);

INSERT INTO enfermedades_medicamentos (id, id_enfermedad, id_medicamento) VALUES
(1, 1, 1), -- Gripe - Paracetamol
(2, 2, 2), -- Diabetes - Insulina
(3, 3, 5), -- COVID-19 - Remdesivir
(4, 4, 4), -- Hipertensión - Ibuprofeno
(5, 5, 1), -- Varicela - Paracetamol
(6, 5, 3); -- Varicela - Amoxicilina

INSERT INTO tarjetas (id, tarjeta_banco, recaudado) VALUES
(1, 'BBVA 1234 5678 9012 3456', 5000),
(2, 'Santander 9876 5432 1098 7654', 8000),
(3, 'HSBC 4567 8901 2345 6789', 12000),
(4, 'Banorte 3210 6543 9876 5432', 3000),
(5, 'Citibanamex 8765 4321 0987 6543', 10000),
(6, 'ABANCA 8765 4321 0987 6543', 10000);

INSERT INTO pacientes (id, NSS, edad, nombre, tarjeta, enfermedad, imagen) VALUES
(1, 123, 45, 'Juan Pérez', 1, 1, NULL), -- Tiene Gripe
(2, 124, 60, 'Ana Gómez', 2, 2, NULL), -- Tiene Diabetes
(3, 125, 35, 'Carlos Sánchez', 3, 3, NULL), -- Tiene COVID-19
(4, 126, 50, 'María López', 4, 4, NULL), -- Tiene Hipertensión
(5, 127, 10, 'Luis Martínez', 5, 5, NULL), -- Tiene Hipertensión
(6, 117, 19, 'JUAN BARRIO', 6, 5, NULL); -- Tiene Varicela

INSERT INTO usuario_data(email, nombre, password,admin) VALUES
("user@ua","usuarioPrueba","123",true)