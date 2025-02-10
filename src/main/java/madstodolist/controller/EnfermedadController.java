package madstodolist.controller;


import madstodolist.service.EnfermedadService;
import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnfermedadController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EnfermedadService enfermedadeService;

    @GetMapping("/enfermedades")
    public String enfermedades(Model model) {
      
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("enfermedades", enfermedadeService.allEnfermedades());

        if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }
        return "enfermedades";
    }


}

