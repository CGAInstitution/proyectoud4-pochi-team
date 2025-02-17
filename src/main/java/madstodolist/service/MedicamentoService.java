package madstodolist.service;

import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.repository.EnfermedadRepository;
import madstodolist.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /* METODO ARREGLADO PARA COPIAR EN LA RAMA DE DEVELOP */

    @Transactional
    public void addMedicamento(String nombre, String descripcion, int precio, boolean receta, Set<Enfermedad> enfermedades) {
        Set<Enfermedad> enfermedadesGestionadas = enfermedades.stream()
                .map(enfermedad -> enfermedadRepository.findById(enfermedad.getId()).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada")))
                .collect(Collectors.toSet());

        Medicamento medicamento = new Medicamento(nombre, descripcion, precio, receta, enfermedadesGestionadas);
        medicamentoRepository.save(medicamento);
    }

    @Transactional
    public void deleteMedicamento(Long id) {
        Medicamento medicamento = getMedicamentoById(id);
        medicamento.getEnfermedades().clear();
        medicamentoRepository.delete(medicamento);
    }

    @Transactional
    public void updateMedicamento(Long id, String nombre, String descripcion, int precio, boolean receta, Set<Enfermedad> enfermedades) {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(id);
        if (medicamento.isPresent()) {
            medicamento.get().setNombre(nombre);
            medicamento.get().setDescripcion(descripcion);
            medicamento.get().setPrecio(precio);
            medicamento.get().setReceta(receta);
            medicamento.get().setEnfermedades(enfermedades);
            medicamentoRepository.save(medicamento.get());
        }
    }

    @Transactional
    public List<Medicamento> getAllMedicamentos() {
        return StreamSupport.stream(medicamentoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional
    public Medicamento getMedicamentoById(Long id) {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(id);
        return medicamento.orElse(null);
    }
}