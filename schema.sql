

CREATE TABLE enfermedad (
                            id bigint NOT NULL PRIMARY KEY,
                            nombre varchar(500) NOT NULL,
                            descripcion varchar(500),
                            peligrosidad smallint,
                            contagiable boolean NOT NULL
);


CREATE TABLE medicamento (
                             id bigint NOT NULL PRIMARY KEY,
                             nombre varchar(500) NOT NULL,
                             descripcion varchar(500),
                             precio int,
                             recetable boolean NOT NULL
);


CREATE TABLE enfermedad_medicamento (
                                        id bigint NOT NULL PRIMARY KEY,
                                        id_enfermedad bigint NOT NULL,
                                        id_medicamento bigint NOT NULL
);


CREATE TABLE paciente (
                          id bigint NOT NULL PRIMARY KEY,
                          NSS tinyint NOT NULL,
                          edad int,
                          nombre varchar(500) NOT NULL,
                          tarjeta bigint NOT NULL,
                          enfermedad bigint NOT NULL
);


CREATE TABLE tarjeta (
                         id bigint NOT NULL PRIMARY KEY,
                         tarjeta_banco varchar(500) NOT NULL,
                         recaudado bigint NOT NULL
);


CREATE TABLE usuario_data (
                              id bigint NOT NULL PRIMARY KEY,
                              email varchar(500) NOT NULL,
                              nombre varchar(500) NOT NULL,
                              password varchar(500) NOT NULL,
                              fecha_nacimiento date,
                              donado bigint NOT NULL
);


ALTER TABLE enfermedad ADD CONSTRAINT enfermedad_id_fk FOREIGN KEY (id) REFERENCES enfermedad_medicamento (id_enfermedad);
ALTER TABLE enfermedad_medicamento ADD CONSTRAINT enfermedad_medicamento_id_medicamento_fk FOREIGN KEY (id_medicamento) REFERENCES medicamento (id);
ALTER TABLE paciente ADD CONSTRAINT paciente_tarjeta_fk FOREIGN KEY (tarjeta) REFERENCES tarjeta (id);
ALTER TABLE paciente ADD CONSTRAINT paciente_enfermedad_fk FOREIGN KEY (enfermedad) REFERENCES enfermedad (id);
