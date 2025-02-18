package madstodolist.service;

import madstodolist.dto.PacienteDTO;
import madstodolist.model.Enfermedad;
import madstodolist.model.Paciente;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;
import madstodolist.repository.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Función de crear un nuevo paciente usado en la API y en los test

    @Transactional
    public Paciente nuevoPaciente(String nss, Integer edad, Long objetivo, String nombre, Enfermedad enfermedad, Tarjeta tarjeta, Usuario usuario) {
        Paciente paciente = new Paciente(nss, edad, objetivo, nombre, enfermedad, tarjeta);
        if (usuario != null) {
            paciente.setUsuario(usuario);
        }
        pacienteRepository.save(paciente);
        return paciente;
    }

    // Función alternativa para crear pacientes usado en la inicialización de la bbdd en exclusividad, ya que no se necesitaba el atributo usuario

    @Transactional
    public void nuevoPaciente(String nss, Integer edad, Long objetivo, String nombre, Enfermedad enfermedad, Tarjeta tarjeta) {
        Paciente paciente = new Paciente(nss, edad, objetivo, nombre, enfermedad, tarjeta);
        pacienteRepository.save(paciente);
    }

    // Función que se encarga de borrar un paciente y la relación con la enfermedad

    @Transactional
    public void borrarPaciente(Long idPaciente) {
        Paciente paciente = findById(idPaciente);
        paciente.getEnfermedad().getPacientes().remove(paciente);
        pacienteRepository.save(paciente);
        pacienteRepository.delete(paciente);
    }

    // Función que sirve para actualizar un paciente, su respectiva enfermedad y su tarjeta

    @Transactional
    public void updatePaciente(
            Long idPaciente,
            String nss,
            @Null Integer edad,
            String nombre,
            Enfermedad enfermedadExistente,
            Tarjeta tarjetaExistente) {

        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);

        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }

        paciente.setNss(nss);
        paciente.setEdad(edad);
        paciente.setNombre(nombre);

        if (enfermedadExistente != null) {
            paciente.setEnfermedad(enfermedadExistente);
        } else if (tarjetaExistente != null) {
            paciente.setTarjeta(tarjetaExistente);
        }


        pacienteRepository.save(paciente);
    }

    // Función que devuelve un paciente con la id indicada

    @Transactional
    public Paciente findById(Long idPaciente) {
        return pacienteRepository.findById(idPaciente).orElse(null);
    }

    // Función que devuelve una lista de todos los pacientes de la bbdd usado en los controllers y en la API

    @Transactional(readOnly = true)
    public List<Paciente> allPacientes() {
        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Función que devuelve una lista de todos los pacientes (usado en la función getMasCercaDeObjetivo)

    @Transactional(readOnly = true)
    public List<PacienteDTO> allPacientesData() {
        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
                .map(p -> modelMapper.map(p, PacienteDTO.class))
                .collect(Collectors.toList());
    }

    // Función que devuelve una lista de los pacientes que no tengan un usuario

    @Transactional(readOnly = true)
    public List<Paciente> allPacientesWithoutUsers() {
        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
                .filter(paciente -> paciente.getUsuario() == null)
                .collect(Collectors.toList());
    }

    // Función que sirve para controlar los porcentajes de las barras de carga de las donaciones de los pacientes

    @Transactional(readOnly = true)
    public List<PacienteDTO> getMasCercaDeObjetivo() {
        return allPacientesData().stream()
                .filter(p -> p.getTarjeta().getProgreso() < 100)
                .sorted((p1, p2) -> Integer.compare(p2.getTarjeta().getProgreso(), p1.getTarjeta().getProgreso()))
                .limit(5)
                .collect(Collectors.toList());
    }

    // Función simple para guardar un paciente

    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }
}