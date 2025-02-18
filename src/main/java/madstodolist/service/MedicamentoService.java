package madstodolist.service;

import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.repository.EnfermedadRepository;
import madstodolist.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private EnfermedadRepository enfermedadRepository;

    // Función que sirve para crear un nuevo medicamento, la lista dde enfermedaddes puede estar vacía o llena, ambos casos están controlados

    @Transactional
    public Medicamento addMedicamento(String nombre, String descripcion, int precio, boolean receta, Set<Enfermedad> enfermedades) {
        Set<Enfermedad> enfermedadesGestionadas = enfermedades.stream()
                .map(enfermedad -> enfermedadRepository.findById(enfermedad.getId()).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada")))
                .collect(Collectors.toSet());

        Medicamento medicamento = new Medicamento(nombre, descripcion, precio, receta, enfermedadesGestionadas);
        medicamentoRepository.save(medicamento);
        return medicamento;
    }

    // Función encargada de borrar un medicamento junto a las relaciones de las enfermedades que usen ese medicamento

    @Transactional
    public void deleteMedicamento(Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        for (Enfermedad enfermedad : new HashSet<>(medicamento.getEnfermedades())) {
            enfermedad.getMedicamentos().remove(medicamento);
        }
        medicamento.getEnfermedades().clear();

        medicamentoRepository.save(medicamento);

        medicamentoRepository.delete(medicamento);
    }

    // Función para actualizar un medicamento (más corto que el de enfermedad, ya que al no tener el mappedby la relación es más manejable),
    // al igual que en enfermedades están contemplados todos los estados de la lista de enfermedades

    @Transactional
    public void updateMedicamento(Long id, String nombre, String descripcion, int precio, boolean receta, Set<Enfermedad> enfermedades) {
        Optional<Medicamento> medicamentoOpt = medicamentoRepository.findById(id);
        if (medicamentoOpt.isPresent()) {
            Medicamento med = medicamentoOpt.get();

            med.setNombre(nombre);
            med.setDescripcion(descripcion);
            med.setPrecio(precio);
            med.setReceta(receta);

            Set<Enfermedad> enfermedadesPersistentes = new HashSet<>();
            for (Enfermedad enf : enfermedades) {
                Optional<Enfermedad> enfermedadPersistente = enfermedadRepository.findById(enf.getId());
                enfermedadesPersistentes.add(enfermedadPersistente.get());
            }

            med.getEnfermedades().clear();
            med.getEnfermedades().addAll(enfermedadesPersistentes);

            medicamentoRepository.save(med);
        }
    }

    // Función que devuelve una lista de todos los medicamentos existentes en la bbdd

    @Transactional
    public List<Medicamento> getAllMedicamentos() {
        return StreamSupport.stream(medicamentoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Función que devuelve un medicamento a partir de una id

    @Transactional
    public Medicamento getMedicamentoById(Long id) {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(id);
        return medicamento.orElse(null);
    }
}