package madstodolist.controller;


import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.model.Paciente;
import madstodolist.service.EnfermedadService;
import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

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
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        }
        return "enfermedades";
    }

    @GetMapping("/enfermedades/{id}")
    public String enfermedadDetallada(@PathVariable(value="id") Long idEnfermedad, Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        Enfermedad enfermedad = enfermedadeService.findById(idEnfermedad);
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("enfermedad", enfermedad);
        Set<Paciente> pacientes = enfermedad.getPacientes();
        model.addAttribute("pacientes", pacientes);
        Set<Medicamento> medicamentos = enfermedad.getMedicamentos();
        model.addAttribute("medicamentos", medicamentos);

        if (usuarioLogeado) {
            model.addAttribute("usuario", usuarioService.getUsuario(usuarioService.findById(usuarioLogeadoId)));
        }

        return "infoDetalladaEnfermedad";
    }

}