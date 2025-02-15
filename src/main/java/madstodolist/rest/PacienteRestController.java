package madstodolist.rest;

import madstodolist.dto.PacienteDTO;
import madstodolist.model.Enfermedad;
import madstodolist.model.Paciente;
import madstodolist.service.EnfermedadService;
import madstodolist.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PacienteRestController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private EnfermedadService enfermedadService;

    @GetMapping("/api/pacientes")
    public List<PacienteDTO> pacientes() {
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        for (Paciente paciente : pacienteService.allPacientes()) {
            PacienteDTO dto = new PacienteDTO();
            dto.setId(paciente.getId());
            dto.setNombre(paciente.getNombre());
            dto.setEdad(paciente.getEdad());
            dto.setNss(paciente.getNss());
            dto.setEnfermedad(paciente.getEnfermedad());
            dto.setObjetivo(paciente.getObjetivo());
            dto.setTarjeta(paciente.getTarjeta());


            pacienteDTOList.add(dto);
        }

        return pacienteDTOList;
    }

    @GetMapping("/api/pacientes/{id}")
    public PacienteDTO paciente(Long id) {
        Paciente paciente = pacienteService.findById(id);
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setEdad(paciente.getEdad());
        dto.setNss(paciente.getNss());
        return dto;
    }

    @PostMapping("/api/pacientes")
    public void nuevoPaciente(@RequestBody Paciente paciente) {
        Enfermedad enfermedadExistente = enfermedadService.findById(paciente.getEnfermedad().getId());
        if (enfermedadExistente == null) {
            enfermedadExistente = enfermedadService.nuevaEnfermedad(
                    paciente.getEnfermedad().getNombre(),
                    paciente.getEnfermedad().getDescripcion(),
                    paciente.getEnfermedad().getPeligrosidad(),
                    paciente.getEnfermedad().isContagiable()
            );
        }

        pacienteService.nuevoPaciente(
                paciente.getId(),
                paciente.getNss(),
                paciente.getEdad(),
                paciente.getObjetivo(),
                paciente.getNombre(),
                enfermedadExistente,
                paciente.getTarjeta()
        );
    }
    //Este aun no funciona
    @PutMapping("/api/pacientes/{id}")
    public void modificarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        Enfermedad enfermedadExistente = enfermedadService.findById(paciente.getEnfermedad().getId());
        if (enfermedadExistente == null) {
            enfermedadExistente = enfermedadService.nuevaEnfermedad(
                    paciente.getEnfermedad().getNombre(),
                    paciente.getEnfermedad().getDescripcion(),
                    paciente.getEnfermedad().getPeligrosidad(),
                    paciente.getEnfermedad().isContagiable()
            );
        }

        pacienteService.updatePaciente(
                id,
                paciente.getNss(),
                paciente.getEdad(),
                paciente.getNombre(),
                enfermedadExistente,
                paciente.getTarjeta()
        );
    }
}
