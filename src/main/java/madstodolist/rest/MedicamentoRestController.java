package madstodolist.rest;

import madstodolist.dto.MedicamentoDTO;
import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.service.EnfermedadService;
import madstodolist.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MedicamentoRestController {
    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EnfermedadService enfermedadService;

    @GetMapping("/api/medicamentos")
    public List<MedicamentoDTO> medicamentos() {
        List<MedicamentoDTO> medicamentoDTOList = new ArrayList<>();

        for (Medicamento medicamento : medicamentoService.getAllMedicamentos()) {
            MedicamentoDTO dto = new MedicamentoDTO();
            dto.setId(medicamento.getId());
            dto.setNombre(medicamento.getNombre());
            dto.setDescripcion(medicamento.getDescripcion());
            dto.setPrecio(medicamento.getPrecio());
            dto.setReceta(medicamento.isReceta());
            dto.setEnfermedades(medicamento.getEnfermedades());

            medicamentoDTOList.add(dto);
        }

        return medicamentoDTOList;
    }

    @GetMapping("/api/medicamentos/{id}")
    public MedicamentoDTO medicamento(@PathVariable Long id) {
        Medicamento medicamento = medicamentoService.getMedicamentoById(id);
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(medicamento.getId());
        dto.setNombre(medicamento.getNombre());
        dto.setDescripcion(medicamento.getDescripcion());
        dto.setPrecio(medicamento.getPrecio());
        dto.setReceta(medicamento.isReceta());
        dto.setEnfermedades(medicamento.getEnfermedades());
        return dto;
    }
}
