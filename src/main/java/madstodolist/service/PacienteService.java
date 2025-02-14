package madstodolist.service;

import madstodolist.model.Paciente;
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
    public void nuevoPaciente(String nss, @Null Integer edad, String nombre, Long objetivo) {
        Paciente paciente = new Paciente(nss, edad, nombre, objetivo);
        pacienteRepository.save(paciente);
    }

    @Transactional
    public void borrarPaciente(Long idPaciente) {
        pacienteRepository.deleteById(idPaciente);
    }

    @Transactional
    public void updatePaciente(Long idPaciente, String nss, @Null Integer edad, String nombre) {
        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
        paciente.setNss(nss);
        paciente.setEdad(edad);
        paciente.setNombre(nombre);
        pacienteRepository.save(paciente);
    }

    @Transactional
    public Paciente findById(Long idPaciente) {
        return pacienteRepository.findById(idPaciente).orElse(null);
    }

    @Transactional
    public List<Paciente> allPacientes() {
        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
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


}