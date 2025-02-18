package madstodolist.repository;

import madstodolist.model.Paciente;
import madstodolist.model.Enfermedad;
import madstodolist.model.Tarjeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/clean-db.sql")
public class PacienteRepositoryTest {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    EnfermedadRepository enfermedadRepository;

    @Test
    public void crearPaciente() {
        // GIVEN
        Paciente paciente = new Paciente("123456789", 30, "Juan Pérez", 100L);

        // THEN
        assertThat(paciente.getNss()).isEqualTo("123456789");
        assertThat(paciente.getEdad()).isEqualTo(30);
        assertThat(paciente.getNombre()).isEqualTo("Juan Pérez");
        assertThat(paciente.getObjetivo()).isEqualTo(100L);
    }

    @Test
    @Transactional
    public void guardarPacienteEnBaseDatos() {
        // GIVEN
        Paciente paciente = new Paciente("987654321", 25, "Ana López", 120L);

        // WHEN
        pacienteRepository.save(paciente);

        // THEN
        assertThat(paciente.getId()).isNotNull();
        Paciente pacienteBD = pacienteRepository.findById(paciente.getId()).orElse(null);
        assertThat(pacienteBD).isNotNull();
        assertThat(pacienteBD.getNombre()).isEqualTo("Ana López");
    }

    @Test
    @Transactional
    public void comprobarIgualdadPacientesSinId() {
        // GIVEN
        Paciente paciente1 = new Paciente("111222333", 40, "Carlos Gómez", 80L);
        Paciente paciente2 = new Paciente("111222333", 40, "Carlos Gómez", 80L);
        Paciente paciente3 = new Paciente("444555666", 50, "Lucía Méndez", 90L);

        // THEN
        assertThat(paciente1).isEqualTo(paciente2);
        assertThat(paciente1).isNotEqualTo(paciente3);
    }

    @Test
    @Transactional
    public void comprobarIgualdadPacientesConId() {
        // GIVEN
        Paciente paciente1 = new Paciente("777888999", 35, "David Ruiz", 110L);
        Paciente paciente2 = new Paciente("666555444", 28, "Sofía Martín", 95L);
        Paciente paciente3 = new Paciente("777888999", 35, "David Ruiz", 110L);
        paciente1.setId(1L);
        paciente2.setId(2L);
        paciente3.setId(1L);

        // THEN
        assertThat(paciente1).isEqualTo(paciente3);
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    @Transactional
    public void asignarEnfermedadAPacienteYGuardar() {
        // GIVEN
        Enfermedad enfermedad = new Enfermedad("Diabetes","texto",Short.valueOf("1"),true);
        Paciente paciente = new Paciente("999888777", 45, "Elena Fernández", 130L);
        paciente.setEnfermedad(enfermedad);

        // WHEN
        enfermedadRepository.save(enfermedad);
        pacienteRepository.save(paciente);
        Paciente pacienteBD = pacienteRepository.findById(paciente.getId()).orElse(null);

        // THEN
        assertThat(pacienteBD).isNotNull();
        assertThat(pacienteBD.getEnfermedad()).isEqualTo(enfermedad);
    }
}