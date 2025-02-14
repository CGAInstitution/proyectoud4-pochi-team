package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.model.Usuario;
import madstodolist.service.UsuarioService;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class UsuariosController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/administrarUsuarios")
    public String administrarUsuarios(Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();

        if (usuarioLogeadoId != null && usuarioService.findById(usuarioLogeadoId).isAdmin()) {
            List<Usuario> usuarios = usuarioService.getAllUsers();
            model.addAttribute("usuarios", usuarios);
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
}