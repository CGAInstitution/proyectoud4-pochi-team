package madstodolist.service;

import madstodolist.model.Paciente;
import madstodolist.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAllPacientes() {
        Paciente paciente1 = new Paciente();
        Paciente paciente2 = new Paciente();
        List<Paciente> pacientes = Arrays.asList(paciente1, paciente2);

        when(pacienteRepository.findAll()).thenReturn(pacientes);

        List<Paciente> result = pacienteService.allPacientes();
        assertEquals(2, result.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Paciente result = pacienteService.findById(1L);
        assertNotNull(result);
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    void testNuevoPaciente() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente result = pacienteService.nuevoPaciente("12345", 30, 70L, "John Doe", null, null, null);
        assertNotNull(result);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }
}