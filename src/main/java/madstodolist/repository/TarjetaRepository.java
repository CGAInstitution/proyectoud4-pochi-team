package madstodolist.repository;

import madstodolist.model.Tarjeta;
import org.springframework.data.repository.CrudRepository;

public interface TarjetaRepository extends CrudRepository<Tarjeta, Long> {
}