package madstodolist.service;

import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.repository.EnfermedadRepository;
import madstodolist.repository.MedicamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Null;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EnfermedadService {

    @Autowired
    private EnfermedadRepository enfermedadRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Transactional
    public void nuevaEnfermedad(String nombre, String descripcion, Short peligrosidad, Boolean contagiable, List<Long> medicamentoIds) {
        Enfermedad nuevaEnfermedad = new Enfermedad(nombre, descripcion, peligrosidad, contagiable);

        // Si la lista de medicamentos no está vacía, añadir los medicamentos
        if (medicamentoIds != null && !medicamentoIds.isEmpty()) {
            for (Long medicamentoId : medicamentoIds) {
                Medicamento managedMedicamento = medicamentoRepository.findById(medicamentoId)
                        .orElseThrow(() -> new RuntimeException("Medicamento no encontrado: " + medicamentoId));
                nuevaEnfermedad.getMedicamentos().add(managedMedicamento);
                managedMedicamento.getEnfermedades().add(nuevaEnfermedad);
            }
        }

        enfermedadRepository.save(nuevaEnfermedad);
    }


    @Transactional(readOnly = true)
    public List<Enfermedad> allEnfermedades() {
        return StreamSupport.stream(enfermedadRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Enfermedad findById(Long enfermedadId) {
        return enfermedadRepository.findById(enfermedadId)
                .orElseThrow(() -> new RuntimeException("Enfermedad no encontrada"));
    }

    @Transactional
    public void modificarEnfermedad(Long id, String nuevoNombre, String nuevaDescripcion, Short nuevaPeligrosidad, Boolean nuevoContagiable, List<Long> medicamentoIds) {
        Optional<Enfermedad> enfermedad = enfermedadRepository.findById(id);
        if (enfermedad.isPresent()) {
            Enfermedad existingEnfermedad = enfermedad.get();
            existingEnfermedad.setNombre(nuevoNombre);
            existingEnfermedad.setDescripcion(nuevaDescripcion);
            existingEnfermedad.setPeligrosidad(nuevaPeligrosidad);
            existingEnfermedad.setContagiable(nuevoContagiable);

            if (medicamentoIds == null) {
                medicamentoIds = new ArrayList<>();
            }

            Set<Medicamento> currentMedicamentos = existingEnfermedad.getMedicamentos();

            List<Long> finalMedicamentoIds = medicamentoIds;
            currentMedicamentos.removeIf(medicamento -> {
                if (!finalMedicamentoIds.contains(medicamento.getId())) {
                    medicamento.getEnfermedades().remove(existingEnfermedad);
                    return true;
                }
                return false;
            });

            for (Long medicamentoId : medicamentoIds) {
                if (currentMedicamentos.stream().noneMatch(medicamento -> medicamento.getId().equals(medicamentoId))) {
                    Medicamento managedMedicamento = medicamentoRepository.findById(medicamentoId)
                            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
                    currentMedicamentos.add(managedMedicamento);
                    managedMedicamento.getEnfermedades().add(existingEnfermedad);
                }
            }

            enfermedadRepository.save(existingEnfermedad);
        }
    }

    @Transactional
    public void borrarEnfermedad(Long idEnfermedad) {
        enfermedadRepository.delete(enfermedadRepository.findById(idEnfermedad).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada")));
    }
}