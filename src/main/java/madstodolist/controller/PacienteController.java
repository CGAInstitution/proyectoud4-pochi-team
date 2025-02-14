package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.model.*;
import madstodolist.service.DonacionService;
import madstodolist.model.Paciente;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PacienteController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

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
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "pacientes";
    }

    @GetMapping("/pacientes/{id}")
    public String pacienteDetallado(@PathVariable(value="id") Long idPaciente, Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        Paciente paciente = pacienteService.findById(idPaciente);
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("paciente", paciente);
        Enfermedad enfermedad = paciente.getEnfermedad();
        model.addAttribute("enfermedad", enfermedad);

        if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }

        return "infoDetalladaPaciente";
    }

    @PostMapping("/pacientes/donacion/{idPaciente}")
    public String donar(@PathVariable Long idPaciente, @RequestParam int donativo, Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            UsuarioData usuario = (usuarioService.findById(usuarioLogeadoId));
            model.addAttribute("usuario", usuario);
            Paciente paciente = pacienteService.findById(idPaciente);
            Tarjeta tarjeta = paciente.getTarjeta();
            donacionService.nuevaDonacion(tarjeta, usuarioService.getUsuario(usuario), donativo);
        }
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "Pacientes";
    }

    @PostMapping("/pacientes/{id}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {


        Paciente paciente = pacienteService.findById(id);

        // Guardar la imagen como byte[] en la base de datos
        paciente.setProfilePicture(file.getBytes());
        pacienteService.guardarPaciente(paciente);

        return ResponseEntity.ok("Imagen subida correctamente");
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

}