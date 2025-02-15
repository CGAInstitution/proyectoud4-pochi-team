package madstodolist.rest;

import madstodolist.dto.PacienteDTO;
import madstodolist.model.Enfermedad;
import madstodolist.model.Paciente;
import madstodolist.service.EnfermedadService;
import madstodolist.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        // Verificamos si la enfermedad ya existe
        Enfermedad enfermedadExistente = enfermedadService.findById(paciente.getEnfermedad().getId());

        // Si la enfermedad no existe, la creamos
        if (enfermedadExistente == null) {
            // Usamos el método nuevaEnfermedad para crearla
            enfermedadExistente = enfermedadService.nuevaEnfermedad(
                    paciente.getEnfermedad().getNombre(),
                    paciente.getEnfermedad().getDescripcion(),
                    paciente.getEnfermedad().getPeligrosidad(),
                    paciente.getEnfermedad().isContagiable()
            );
        }

        // Luego, creamos el paciente con la enfermedad
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



}
