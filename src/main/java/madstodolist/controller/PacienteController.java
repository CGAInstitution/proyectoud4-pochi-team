package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.service.PacienteService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PacienteController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    private PacienteService pacienteService;

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
        return "Pacientes";
    }

}