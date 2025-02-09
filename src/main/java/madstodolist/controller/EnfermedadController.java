package madstodolist.controller;

import madstodolist.service.EnfermedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnfermedadController {

    @Autowired
    EnfermedadService enfermedadeService;

    @GetMapping("/enfermedades")
    public String enfermedades(Model model) {
        model.addAttribute("enfermedades", enfermedadeService.allEnfermedades());
        return "enfermedades";
    }


}
