package madstodolist.controller;

import madstodolist.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/medicamentos")
    public String medicamentos(Model model) {
        model.addAttribute("medicamentos", medicamentoService.getAllMedicamentos());
        return "medicamentos";
    }
}
