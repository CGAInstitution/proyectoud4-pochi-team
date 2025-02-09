package madstodolist.repository;

import madstodolist.model.Medicamento;
import org.springframework.data.repository.CrudRepository;

public interface MedicamentoRepository extends CrudRepository<Medicamento, Long> {
}