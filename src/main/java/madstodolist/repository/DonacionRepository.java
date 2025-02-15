package madstodolist.repository;

import madstodolist.model.Donacion;
import org.springframework.data.repository.CrudRepository;

public interface DonacionRepository extends CrudRepository<Donacion, Long> {
}