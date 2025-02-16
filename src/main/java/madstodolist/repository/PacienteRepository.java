package madstodolist.repository;

import madstodolist.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

