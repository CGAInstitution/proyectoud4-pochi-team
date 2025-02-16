package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.model.Paciente;
import madstodolist.model.Usuario;
import madstodolist.service.PacienteService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsuariosController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PacienteService pacienteService;


    @GetMapping("/administrarUsuarios")
    public String administrarUsuarios(Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();

        if (usuarioLogeadoId != null && usuarioService.findById(usuarioLogeadoId).isAdmin()) {
            List<Usuario> usuarios = usuarioService.getAllUsers();
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("pacientes", pacienteService.allPacientesWithoutUsers());
            return "administrarUsuarios";
        }

        return "redirect:/";
    }

    @PostMapping("/usuarios/{id}/admin")
    public String updateAdminStatus(@PathVariable Long id, @RequestParam(required = false) boolean admin) {
        usuarioService.updateAdminStatus(id, admin);
        return "redirect:/administrarUsuarios";
    }

    @PostMapping("/usuarios/{id}/block")
    public String updateBlockStatus(@PathVariable Long id, @RequestParam(required = false) boolean block) {
        usuarioService.updateBlockStatus(id, block);
        return "redirect:/administrarUsuarios";
    }

    @PostMapping("/usuarios/{id}/asignarPaciente")
    public String assignPaciente(@PathVariable("id") Long usuarioId, @RequestParam("pacienteId") Long pacienteId) {
        Usuario usuario = usuarioService.getUsuario(usuarioService.findById(usuarioId));
        Paciente paciente = pacienteService.findById(pacienteId);
        if (paciente != null && paciente.getUsuario() != null) {
            paciente.getUsuario().setPaciente(null);
            usuarioService.save(paciente.getUsuario());
        }
        usuario.setPaciente(paciente);
        usuarioService.save(usuario);
        return "redirect:/administrarUsuarios";
    }
}