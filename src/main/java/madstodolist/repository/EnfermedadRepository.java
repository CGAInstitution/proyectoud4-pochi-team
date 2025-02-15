package madstodolist.repository;

import madstodolist.model.Enfermedad;
import org.springframework.data.repository.CrudRepository;

public interface EnfermedadRepository extends CrudRepository<Enfermedad, Long> {
}