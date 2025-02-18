package madstodolist.controller;

import madstodolist.model.Enfermedad;
import madstodolist.model.Paciente;
import madstodolist.model.Tarjeta;
import madstodolist.service.EnfermedadService;
import madstodolist.service.PacienteService;
import madstodolist.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PacienteWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private EnfermedadService enfermedadService;

    @Test
    public void visualizarListaPacientes() throws Exception {
        // WHEN, THEN
        this.mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pacientes")));
    }

    @Test
    public void visualizarDetallePaciente() throws Exception {
        // GIVEN
        Paciente paciente = new Paciente(1L, "123456789", 30, 5000L, "Juan Pérez");
        Enfermedad enfermedad = new Enfermedad("nombre","123", Short.valueOf("1"),true);
        Tarjeta tarjeta = new Tarjeta("tarjeta",paciente);
        paciente.setTarjeta(tarjeta);
        paciente.setEnfermedad(enfermedad);
        when(pacienteService.findById(1L)).thenReturn(paciente);

        // WHEN, THEN
        this.mockMvc.perform(get("/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Juan Pérez")));
    }

    @Test
    public void crearNuevoPaciente() throws Exception {
        // WHEN, THEN
        this.mockMvc.perform(post("/pacientes/guardar")
                        .param("nss", "987654321")
                        .param("edad", "45")
                        .param("nombre", "María López")
                        .param("objetivo", "10000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pacientes"));
    }

    @Test
    public void gestionarPaciente() throws Exception {
        // GIVEN
        Paciente paciente = new Paciente(1L, "123456789", 30, 5000L, "Juan Pérez");
        when(pacienteService.findById(1L)).thenReturn(paciente);

        // WHEN, THEN
        this.mockMvc.perform(get("/pacientes/gestionarPaciente/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Gestionar Paciente")));
    }
}