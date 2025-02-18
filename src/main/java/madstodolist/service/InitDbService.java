package madstodolist.service;

import madstodolist.dto.UsuarioData;
import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.model.Paciente;
import madstodolist.model.Tarjeta;
import madstodolist.repository.TareaRepository;
import madstodolist.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
@Profile("dev")
public class InitDbService {

    // Para ejecutarse esto se tiene que borrar la bbdd y volver a crearla vacía, por que si no no funciona la parte de usuario_data

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnfermedadService enfermedadService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private TarjetaService tarjetaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DonacionService donacionService;

    @Transactional
    @PostConstruct
    public void initDatabase() {

        // PARTE DE LOS MEDICAMENTOS SIN RELACIONAR

        Medicamento medicamento1 = medicamentoService.addMedicamento("Paracetamol", "Analgésico y antipirético", 5, false, new HashSet<>());
        Medicamento medicamento2 = medicamentoService.addMedicamento("Insulina", "Regulador de glucosa en sangre", 50, true, new HashSet<>());
        Medicamento medicamento3 = medicamentoService.addMedicamento("Amoxicilina", "Antibiótico de amplio espectro", 10, true, new HashSet<>());
        Medicamento medicamento4 = medicamentoService.addMedicamento("Ibuprofeno", "Antiinflamatorio y analgésico", 7, false, new HashSet<>());
        Medicamento medicamento5 = medicamentoService.addMedicamento("Remdesivir", "Antiviral para infecciones graves", 300, true, new HashSet<>());

        // PARTE DE LAS ENFERMEDADES RELACIONADAS CON MEDICAMENTOS

        Enfermedad enfermedad1 = enfermedadService.nuevaEnfermedad("Gripe", "Infección viral que afecta las vías respiratorias", (short) 3, true, new ArrayList<>());
        Enfermedad enfermedad2 = enfermedadService.nuevaEnfermedad("Diabetes", "Enfermedad crónica que afecta los niveles de azúcar en sangre", (short) 5, false, new ArrayList<>());
        Enfermedad enfermedad3 = enfermedadService.nuevaEnfermedad("COVID-19", "Infección viral que afecta las vías respiratorias y otros órganos", (short) 5, true, new ArrayList<>());
        Enfermedad enfermedad4 = enfermedadService.nuevaEnfermedad("Hipertensión", "Presión arterial alta", (short) 4, false, new ArrayList<>());
        Enfermedad enfermedad5 = enfermedadService.nuevaEnfermedad("Varicela", "Infección viral que causa erupciones cutáneas y fiebre", (short) 4, true, new ArrayList<>());

        // PARTE DE LAS RELACIONES

        enfermedadService.modificarEnfermedad(enfermedad1.getId(), "Gripe", "Infección viral que afecta las vías respiratorias", (short) 3, true, Arrays.asList(medicamento1.getId()));
        enfermedadService.modificarEnfermedad(enfermedad2.getId(), "Diabetes", "Enfermedad crónica que afecta los niveles de azúcar en sangre", (short) 5, false, Arrays.asList(medicamento2.getId()));
        enfermedadService.modificarEnfermedad(enfermedad3.getId(), "COVID-19", "Infección viral que afecta las vías respiratorias y otros órganos", (short) 5, true, Arrays.asList(medicamento5.getId()));
        enfermedadService.modificarEnfermedad(enfermedad4.getId(), "Hipertensión", "Presión arterial alta", (short) 4, false, Arrays.asList(medicamento4.getId()));
        enfermedadService.modificarEnfermedad(enfermedad5.getId(), "Varicela", "Infección viral que causa erupciones cutáneas y fiebre", (short) 4, true, Arrays.asList(medicamento1.getId(), medicamento3.getId()));

        // PARTE DE LOS USUARIO_DATA

        UsuarioData usuario1 = new UsuarioData();
        usuario1.setNombre("Usuario1");
        usuario1.setEmail("user@ua");
        usuario1.setPassword("123");
        usuario1.setAdmin(true);
        usuario1.setBloqueado(false);
        usuario1.setDonado(100L);

        UsuarioData usuario2 = new UsuarioData();
        usuario2.setNombre("Usuario2");
        usuario2.setEmail("usuario2@example.com");
        usuario2.setPassword("password456");
        usuario2.setAdmin(false);
        usuario2.setBloqueado(true);
        usuario2.setDonado(50L);

        UsuarioData usuario3 = new UsuarioData();
        usuario3.setNombre("Usuario3");
        usuario3.setEmail("user@ua1");
        usuario3.setPassword("123");
        usuario3.setAdmin(false);
        usuario3.setBloqueado(false);
        usuario3.setDonado(200L);

        usuarioService.registrar(usuario1);
        usuarioService.registrar(usuario2);
        usuarioService.registrar(usuario3);

        // PARTE DE LAS TARJETAS

        Tarjeta tarjeta1 = new Tarjeta("BBVA 1234 5678 9012 3456");
        Tarjeta tarjeta2 = new Tarjeta("Santander 9876 5432 1098 7654");
        Tarjeta tarjeta3 = new Tarjeta("HSBC 4567 8901 2345 6789");
        Tarjeta tarjeta4 = new Tarjeta("Banorte 3210 6543 9876 5432");
        Tarjeta tarjeta5 = new Tarjeta("Citibanamex 8765 4321 0987 6543");
        Tarjeta tarjeta6 = new Tarjeta("ABANCA 8765 4321 0987 6543");

        Paciente paciente1 = pacienteService.nuevoPaciente("123", 45, 2000L, "Juan Pérez", enfermedad1, tarjeta1);
        Paciente paciente2 = pacienteService.nuevoPaciente("124", 60, 38000L, "Ana Gómez", enfermedad2, tarjeta2);
        Paciente paciente3 = pacienteService.nuevoPaciente("125", 35, 34L, "Carlos Sánchez", enfermedad3, tarjeta3);
        Paciente paciente4 = pacienteService.nuevoPaciente("126", 50, 45000L, "María López", enfermedad4, tarjeta4);
        Paciente paciente5 = pacienteService.nuevoPaciente("127", 10, 200000L, "Luis Martínez", enfermedad5, tarjeta5);
        Paciente paciente6 = pacienteService.nuevoPaciente("117", 19, 300000L, "JUAN BARRIO", enfermedad5, tarjeta6);

        tarjetaService.nuevaTarjeta(tarjeta1);
        tarjetaService.nuevaTarjeta(tarjeta2);
        tarjetaService.nuevaTarjeta(tarjeta3);
        tarjetaService.nuevaTarjeta(tarjeta4);
        tarjetaService.nuevaTarjeta(tarjeta5);
        tarjetaService.nuevaTarjeta(tarjeta6);

        // PARTE DE LAS DONACIONES

        donacionService.nuevaDonacion(tarjeta1, usuarioService.getUsuarioByEmail(usuario1.getEmail()), 1000L);
        donacionService.nuevaDonacion(tarjeta2, usuarioService.getUsuarioByEmail(usuario2.getEmail()), 1500L);
        donacionService.nuevaDonacion(tarjeta3, usuarioService.getUsuarioByEmail(usuario3.getEmail()), 2000L);
    }
}