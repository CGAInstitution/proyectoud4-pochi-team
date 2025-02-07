package madstodolist.repository;

import madstodolist.model.Enfermedad;
import madstodolist.model.Tarea;
import org.springframework.data.repository.CrudRepository;

public interface EnfermedadRepository  extends CrudRepository<Enfermedad, Long> {
}