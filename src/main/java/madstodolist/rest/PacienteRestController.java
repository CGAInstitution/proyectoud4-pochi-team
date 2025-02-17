package madstodolist.rest;

import madstodolist.dto.PacienteDTO;
import madstodolist.dto.UsuarioData;
import madstodolist.model.*;
import madstodolist.service.EnfermedadService;
import madstodolist.service.PacienteService;
import madstodolist.service.TarjetaService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PacienteRestController {
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private EnfermedadService enfermedadService;
    @Autowired
    private TarjetaService tarjetaService;


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
        dto.setEnfermedad(paciente.getEnfermedad());
        dto.setObjetivo(paciente.getObjetivo());
        dto.setTarjeta(paciente.getTarjeta());
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

        Tarjeta nuevaTarjeta = new Tarjeta();
        nuevaTarjeta.setTarjeta_banco(paciente.getTarjeta().getTarjeta_banco());
        paciente.setTarjeta(nuevaTarjeta);
        paciente.setEnfermedad(enfermedadExistente);
        pacienteService.nuevoPaciente(
                paciente.getNss(),
                paciente.getEdad(),
                paciente.getObjetivo(),
                paciente.getNombre(),
                enfermedadExistente,
                paciente.getTarjeta(),
                paciente.getUsuario()
        );
    }

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

    @DeleteMapping("/api/pacientes/{id}")
    public void borrarPaciente(@PathVariable Long id) {
        pacienteService.borrarPaciente(id);
    }
}