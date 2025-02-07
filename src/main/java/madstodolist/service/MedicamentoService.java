package madstodolist.service;

import madstodolist.model.Medicamento;
import madstodolist.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Transactional
    public void addMedicamento(String nombre, String descripcion, int precio, boolean receta) {
        medicamentoRepository.save(new Medicamento(nombre, descripcion, precio, receta));
    }

    @Transactional
    public void deleteMedicamento(Long id) {
        medicamentoRepository.deleteById(id);
    }

    @Transactional
    public void updateMedicamento(Long id, String nombre, String descripcion, int precio, boolean receta) {
        Optional<Medicamento> medicamento = medicamentoRepository.findById(id);
        if (medicamento.isPresent()) {
            medicamento.get().setNombre(nombre);
            medicamento.get().setDescripcion(descripcion);
            medicamento.get().setPrecio(precio);
            medicamento.get().setReceta(receta);
            medicamentoRepository.save(medicamento.get());
        }
        System.out.println("Medicamento no encontrado");
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