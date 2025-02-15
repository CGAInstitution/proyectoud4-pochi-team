package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.model.Enfermedad;
import madstodolist.model.Paciente;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;
import madstodolist.service.DonacionService;
import madstodolist.service.EnfermedadService;
import madstodolist.service.PacienteService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class PacienteController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private EnfermedadService enfermedadService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DonacionService donacionService;

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        }
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "pacientes";
    }

    @GetMapping("/pacientes/{id}")
    public String pacienteDetallado(@PathVariable(value = "id") Long idPaciente, Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        Paciente paciente = pacienteService.findById(idPaciente);
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("paciente", paciente);
        Enfermedad enfermedad = paciente.getEnfermedad();
        model.addAttribute("enfermedad", enfermedad);

        if (usuarioLogeado) {
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        }

        return "infoDetalladaPaciente";
    }

    @GetMapping("pacientes/gestionarPaciente/{id}")
    public String gestionarPaciente(@PathVariable(value = "id") Long idUsuario, Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();

        if (!Objects.equals(usuarioLogeadoId, idUsuario)) {
            return "/";
        }

        model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        model.addAttribute("enfermedades", enfermedadService.allEnfermedades());
        model.addAttribute("paciente", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)).getPaciente());

        return "gestionarPaciente";
    }

    @PostMapping("pacientes/guardar/{id}")
    public String guardarPaciente(@PathVariable(value = "id") Long idPaciente, @ModelAttribute Paciente paciente) {
        Paciente existingPaciente = pacienteService.findById(idPaciente);
        if (existingPaciente != null) {
            existingPaciente.setNss(paciente.getNss());
            existingPaciente.setEdad(paciente.getEdad());
            existingPaciente.setNombre(paciente.getNombre());
            existingPaciente.setObjetivo(paciente.getObjetivo());
            existingPaciente.setEnfermedad(paciente.getEnfermedad());
            existingPaciente.getTarjeta().setTarjeta_banco(paciente.getTarjeta().getTarjeta_banco());
            pacienteService.guardarPaciente(existingPaciente);
        }
        return "redirect:/pacientes";
    }

    @PostMapping("/pacientes/{id}/upload-profile-picture")
    public String uploadProfilePicture(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {


        Paciente paciente = pacienteService.findById(id);

        // Guardar la imagen como byte[] en la base de datos
        paciente.setProfilePicture(file.getBytes());
        pacienteService.guardarPaciente(paciente);

        return "redirect:/pacientes/gestionarPaciente/" + paciente.getUsuario().getId();
    }

    // Obtener la imagen desde la base de datos y devolverla como respuesta HTTP
    @GetMapping("/pacientes/{id}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) throws IOException {

        byte[] image = pacienteService.findById(id).getProfilePicture();

        if (image == null) {
            Resource defaultImage = new ClassPathResource("static/iconos/doctor.jpg");
            image = StreamUtils.copyToByteArray(defaultImage.getInputStream());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("/donar/{id}")
    public String formDonar(@PathVariable("id") Long idPaciente, Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;

        if (usuarioLogeado) {
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        }
        model.addAttribute("paciente", pacienteService.findById(idPaciente));
        return "formDonar";
    }

    @PostMapping("/pacientes/donacion/{idPaciente}")
    public String donar(@PathVariable Long idPaciente, @RequestParam Long donativo, Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            Usuario usuario = usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId));
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
            Paciente paciente = pacienteService.findById(idPaciente);
            Tarjeta tarjeta = paciente.getTarjeta();
            donacionService.nuevaDonacion(tarjeta, usuario, donativo);
        }
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "redirect:/pacientes";
    }

}