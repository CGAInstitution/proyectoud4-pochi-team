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
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        for (Long medicamentoId : medicamentoIds) {
            Medicamento managedMedicamento = medicamentoRepository.findById(medicamentoId)
                    .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
            nuevaEnfermedad.getMedicamentos().add(managedMedicamento);
            managedMedicamento.getEnfermedades().add(nuevaEnfermedad);
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

            existingEnfermedad.getMedicamentos().clear();
            for (Long medicamentoId : medicamentoIds) {
                Medicamento managedMedicamento = medicamentoRepository.findById(medicamentoId)
                        .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
                existingEnfermedad.getMedicamentos().add(managedMedicamento);
                managedMedicamento.getEnfermedades().add(existingEnfermedad);
            }

            enfermedadRepository.save(existingEnfermedad);
        }
    }

    @Transactional
    public void borrarEnfermedad(Long idEnfermedad) {
        enfermedadRepository.delete(enfermedadRepository.findById(idEnfermedad).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada")));
    }
}