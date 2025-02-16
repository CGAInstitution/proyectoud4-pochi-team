package madstodolist.repository;

import madstodolist.model.Enfermedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EnfermedadRepository extends JpaRepository<Enfermedad, Long> {
}