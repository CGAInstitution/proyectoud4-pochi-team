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





    INSERT INTO enfermedades (nombre, descripcion, peligrosidad, contagiable) VALUES
                                                                                  ('Gripe', 'Infección viral que afecta las vías respiratorias', 3, true),
                                                                                  ('Diabetes', 'Enfermedad crónica que afecta los niveles de azúcar en sangre', 5, false),
                                                                                  ('COVID-19', 'Infección viral que afecta las vías respiratorias y otros órganos', 5, true),
                                                                                  ('Hipertensión', 'Presión arterial alta', 4, false),
                                                                                  ('Varicela', 'Infección viral que causa erupciones cutáneas y fiebre', 4, true);

    INSERT INTO medicamentos (nombre, descripcion, precio, recetable) VALUES
                                                                          ('Paracetamol', 'Medicamento para aliviar el dolor y la fiebre', 50, true),
                                                                          ('Insulina', 'Hormona para tratar la diabetes', 300, true),
                                                                          ('Amoxicilina', 'Antibiótico para tratar infecciones bacterianas', 100, true),
                                                                          ('Ibuprofeno', 'Medicamento antiinflamatorio', 75, true),
                                                                          ('Remdesivir', 'Antiviral utilizado en casos graves de COVID-19', 2000, true);

    INSERT INTO enfermedades_medicamentos (id_enfermedad, id_medicamento) VALUES
                                                                              (1, 1), -- Gripe - Paracetamol
                                                                              (2, 2), -- Diabetes - Insulina
                                                                              (3, 5), -- COVID-19 - Remdesivir
                                                                              (4, 4), -- Hipertensión - Ibuprofeno
                                                                              (5, 1), -- Varicela - Paracetamol
                                                                              (5, 3); -- Varicela - Amoxicilina

    INSERT INTO tarjetas (id, tarjeta_banco) VALUES
                                                 (1, 'BBVA 1234 5678 9012 3456'),
                                                 (2, 'Santander 9876 5432 1098 7654'),
                                                 (3, 'HSBC 4567 8901 2345 6789'),
                                                 (4, 'Banorte 3210 6543 9876 5432'),
                                                 (5, 'Citibanamex 8765 4321 0987 6543'),
                                                 (6, 'ABANCA 8765 4321 0987 6543');

    INSERT INTO pacientes (id, NSS, edad, nombre, tarjeta, enfermedad, imagen,objetivo) VALUES
                                                                                            (1, 123, 45, 'Juan Pérez', 1, 1, NULL,2000), -- Tiene Gripe
                                                                                            (2, 124, 60, 'Ana Gómez', 2, 2, NULL,38000), -- Tiene Diabetes
                                                                                            (3, 125, 35, 'Carlos Sánchez', 3, 3, NULL,34), -- Tiene COVID-19
                                                                                            (4, 126, 50, 'María López', 4, 4, NULL,45000), -- Tiene Hipertensión
                                                                                            (5, 127, 10, 'Luis Martínez', 5, 5, NULL,200000), -- Tiene Hipertensión
                                                                                            (6, 117, 19, 'JUAN BARRIO', 6, 5, NULL,300000); -- Tiene Varicela

    INSERT INTO usuario_data(email, nombre, password,admin) VALUES
                                                                ("user@ua1","usuarioPrueba","123",true),
                                                                ("user@ua","usuarioPrueba","123",true);

    INSERT INTO usuario_tarjeta(usuario_id,tarjeta_id,cantidad) VALUES
                                                                    (1,1,2000),
                                                                    (2,2,3000)