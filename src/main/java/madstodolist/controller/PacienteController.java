package madstodolist.controller;

import madstodolist.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "Pacientes";
    }

}
