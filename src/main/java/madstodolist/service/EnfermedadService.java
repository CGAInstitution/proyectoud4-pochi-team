package madstodolist.service;

import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.repository.EnfermedadRepository;
import madstodolist.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Enfermedad nuevaEnfermedad(String nombre, String descripcion, Short peligrosidad, Boolean contagiable, List<Long> medicamentoIds) {
        Enfermedad nuevaEnfermedad = new Enfermedad(nombre, descripcion, peligrosidad, contagiable);

        if (medicamentoIds != null && !medicamentoIds.isEmpty()) {
            for (Long medicamentoId : medicamentoIds) {
                Medicamento managedMedicamento = medicamentoRepository.findById(medicamentoId)
                        .orElseThrow(() -> new RuntimeException("Medicamento no encontrado: " + medicamentoId));
                nuevaEnfermedad.getMedicamentos().add(managedMedicamento);
                managedMedicamento.getEnfermedades().add(nuevaEnfermedad);
            }
        }

        enfermedadRepository.save(nuevaEnfermedad);
        return nuevaEnfermedad;
    }
    @Transactional
    public Enfermedad nuevaEnfermedad(String nombre, @Null String descripcion, @Null Short peligrosidad, boolean contagiable) {
        Enfermedad enfermedad = new Enfermedad(nombre,descripcion,peligrosidad,contagiable);
        enfermedadRepository.save(enfermedad);
        return enfermedad;
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

    /* METODO ARREGLADO PARA COPIAR EN LA RAMA DE DEVELOP */

    @Transactional
    public void modificarEnfermedad(Long id, String nuevoNombre, String nuevaDescripcion, Short nuevaPeligrosidad, Boolean nuevoContagiable, List<Long> medicamentoIds) {
        Optional<Enfermedad> enfermedadOpt = enfermedadRepository.findById(id);
        if (enfermedadOpt.isPresent()) {
            Enfermedad enfermedad = enfermedadOpt.get();
            enfermedad.setNombre(nuevoNombre);
            enfermedad.setDescripcion(nuevaDescripcion);
            enfermedad.setPeligrosidad(nuevaPeligrosidad);
            enfermedad.setContagiable(nuevoContagiable);

            for (Medicamento medicamento : new HashSet<>(enfermedad.getMedicamentos())) {
                medicamento.getEnfermedades().remove(enfermedad);
            }
            enfermedad.getMedicamentos().clear();

            if (medicamentoIds != null && !medicamentoIds.isEmpty()) {
                for (Long medicamentoId : medicamentoIds) {
                    Medicamento managedMedicamento = medicamentoRepository.findById(medicamentoId)
                            .orElseThrow(() -> new RuntimeException("Medicamento no encontrado: " + medicamentoId));
                    enfermedad.getMedicamentos().add(managedMedicamento);
                    managedMedicamento.getEnfermedades().add(enfermedad);
                }
            }

            enfermedadRepository.save(enfermedad);
        } else {
            throw new RuntimeException("Enfermedad no encontrada");
        }
    }

    /* METODO ARREGLADO PARA COPIAR EN LA RAMA DE DEVELOP */

    @Transactional
    public void borrarEnfermedad(Long idEnfermedad) {
        Enfermedad enfermedad = enfermedadRepository.findById(idEnfermedad)
                .orElseThrow(() -> new RuntimeException("Enfermedad no encontrada"));

        for (Medicamento medicamento : new HashSet<>(enfermedad.getMedicamentos())) {
            medicamento.getEnfermedades().remove(enfermedad);
        }
        enfermedad.getMedicamentos().clear();

        enfermedadRepository.save(enfermedad);

        enfermedadRepository.delete(enfermedad);
    }
}