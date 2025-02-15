package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.service.PacienteService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PacienteService pacienteService;

    @GetMapping("/")
    public String home(Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        }

        model.addAttribute("mayoresDonantes", usuarioService.mayoresDonantes());
        model.addAttribute("masCercaDeObjetivo", pacienteService.getMasCercaDeObjetivo());
        return "MenuPrincipal"; // Nombre del archivo HTML sin la extensión
    }
}