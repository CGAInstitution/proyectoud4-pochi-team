package madstodolist.service;

import madstodolist.model.Enfermedad;
import madstodolist.model.Paciente;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;
import madstodolist.repository.PacienteRepository;
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

    @Transactional
    public void nuevoPaciente(String nss, Integer edad, Long objetivo, String nombre, Enfermedad enfermedad, Tarjeta tarjeta, Usuario usuario) {
        Paciente paciente = new Paciente(nss, edad, objetivo, nombre, enfermedad, tarjeta);
        if (usuario != null) {
            paciente.setUsuario(usuario);
        }
        pacienteRepository.save(paciente);
    }
    @Transactional
    public Paciente nuevoPaciente(String nss, Integer edad, Long objetivo, String nombre, Enfermedad enfermedad, Tarjeta tarjeta) {
        Paciente paciente = new Paciente(nss, edad, objetivo, nombre, enfermedad, tarjeta);
        pacienteRepository.save(paciente);
        return paciente;
    }


    @Transactional
    public void borrarPaciente(Long idPaciente) {
        Paciente paciente = findById(idPaciente);
        paciente.getEnfermedad().getPacientes().remove(paciente);
        pacienteRepository.save(paciente);
        pacienteRepository.delete(paciente);
    }


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


    @Transactional
    public Paciente findById(Long idPaciente) {
        return pacienteRepository.findById(idPaciente).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Paciente> allPacientes() {
        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Paciente> allPacientesWithoutUsers() {
        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
                .filter(paciente -> paciente.getUsuario() == null)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Paciente> getMasCercaDeObjetivo() {
        return allPacientes().stream()
                .filter(p -> p.getTarjeta().getProgreso() < 100)
                .sorted((p1, p2) -> Integer.compare(p2.getTarjeta().getProgreso(), p1.getTarjeta().getProgreso()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public void guardarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }


}