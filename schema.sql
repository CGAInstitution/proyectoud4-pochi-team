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
                              id bigint NOT NULL PRIMARY KEY,
                              email varchar(500) NOT NULL,
                              nombre varchar(500) NOT NULL,
                              password varchar(500) NOT NULL,
                              fecha_nacimiento date,
                              donado bigint NOT NULL DEFAULT 0
);
