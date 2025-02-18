-- src/test/resources/clean-db.sql

-- Create schema if it does not exist
CREATE SCHEMA IF NOT EXISTS health_database;

-- Create table if it does not exist
CREATE TABLE IF NOT EXISTS health_database.usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Delete from table
DELETE FROM health_database.usuarios;

-- Drop tables if they exist
DROP TABLE IF EXISTS health_database.enfermedades CASCADE;
DROP TABLE IF EXISTS health_database.medicamentos CASCADE;
DROP TABLE IF EXISTS health_database.pacientes CASCADE;
DROP TABLE IF EXISTS health_database.usuario_data CASCADE;
DROP TABLE IF EXISTS health_database.tareas CASCADE;
-- Add other table drop statements as needed


CREATE TABLE health_database.pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    edad INT,
    enfermedad VARCHAR(255),
    nombre VARCHAR(255),
    nss VARCHAR(255),
    objetivo VARCHAR(255),
    profile_picture BLOB,
    tarjeta VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS health_database.enfermedades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contagiable BOOLEAN NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    nombre VARCHAR(500) NOT NULL,
    peligrosidad SMALLINT NOT NULL
);