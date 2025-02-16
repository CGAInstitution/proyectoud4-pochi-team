package madstodolist.service;

import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.repository.EnfermedadRepository;
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

    @Transactional
    public void nuevaEnfermedad(String nombre, @Null String descripcion, @Null Short peligrosidad, boolean contagiable, Set<Medicamento> medicamentos) {
        enfermedadRepository.save(new Enfermedad(nombre,descripcion,peligrosidad,contagiable, medicamentos));
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
    public void modificarEnfermedad(Long id, String nuevoNombre, String nuevaDescripcion, Short nuevaPeligrosidad, Boolean nuevoContagiable, Set<Medicamento> medicamentos) {
        Optional<Enfermedad> enfermedad = enfermedadRepository.findById(id);
        if (enfermedad.isPresent()) {
            enfermedad.get().setNombre(nuevoNombre);
            enfermedad.get().setDescripcion(nuevaDescripcion);
            enfermedad.get().setPeligrosidad(nuevaPeligrosidad);
            enfermedad.get().setContagiable(nuevoContagiable);
            enfermedad.get().setMedicamentos(medicamentos);
            enfermedadRepository.save(enfermedad.get());
        }
    }

    @Transactional
    public void borrarEnfermedad(Long idEnfermedad) {
        enfermedadRepository.delete(enfermedadRepository.findById(idEnfermedad).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada")));
    }
}