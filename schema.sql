    DROP DATABASE IF EXISTS HEALTH_DATABASE;
    CREATE DATABASE IF NOT EXISTS HEALTH_DATABASE;

    use HEALTH_DATABASE;

    CREATE TABLE enfermedades (
                                  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                  nombre VARCHAR(500) NOT NULL,
                                  descripcion VARCHAR(500),
                                  peligrosidad SMALLINT,
                                  contagiable BOOLEAN NOT NULL
    );

    CREATE TABLE medicamentos (
                                  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                  nombre VARCHAR(500) NOT NULL,
                                  descripcion VARCHAR(500),
                                  precio INT DEFAULT 0,
                                  recetable BOOLEAN NOT NULL DEFAULT TRUE
    );

    CREATE TABLE enfermedades_medicamentos (
                                               id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                                               id_enfermedad BIGINT NOT NULL,
                                               id_medicamento BIGINT NOT NULL,
                                               CONSTRAINT fk_enfermedades_medicamentos_enfermedad FOREIGN KEY (id_enfermedad)
                                                   REFERENCES enfermedades(id)
                                                   ON DELETE CASCADE
                                                   ON UPDATE CASCADE,
                                               CONSTRAINT fk_enfermedades_medicamentos_medicamento FOREIGN KEY (id_medicamento)
                                                   REFERENCES medicamentos(id)
                                                   ON DELETE CASCADE
                                                   ON UPDATE CASCADE
    );


    CREATE TABLE tarjetas (
                              id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              tarjeta_banco varchar(500) NOT NULL
    );


    CREATE TABLE pacientes (
                               id bigint NOT NULL PRIMARY KEY auto_increment,
                               NSS tinyint NOT NULL,
                               edad int,
                               nombre varchar(500) NOT NULL,
                               tarjeta bigint NOT NULL,
                               enfermedad bigint NOT NULL,
                               profilePicture blob NULL,
                               imagen varchar(500) NULL,
                               objetivo int not null,
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

    CREATE TABLE usuario_tarjeta (
                                     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     usuario_id bigint NOT NULL,
                                     tarjeta_id bigint NOT NULL,
                                     fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     cantidad BIGINT NOT NULL DEFAULT 0,
                                     FOREIGN KEY (usuario_id) references usuario_data(id)
                                         on delete cascade
                                         on update cascade,
                                     FOREIGN KEY (tarjeta_id) references tarjetas(id)
                                         on delete cascade
                                         on update cascade);