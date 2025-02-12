package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.model.Paciente;
import madstodolist.service.MedicamentoService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class MedicamentoController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/medicamentos")
    public String medicamentos(Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }
        model.addAttribute("medicamentos", medicamentoService.getAllMedicamentos());
        return "medicamentos";
    }

    @GetMapping("/medicamentos/{id}")
    public String medicamentoDetallado(@PathVariable(value="id") Long idMedicamento, Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        Medicamento medicamento = medicamentoService.getMedicamentoById(idMedicamento);
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("medicamento", medicamento);
        Set<Enfermedad> enfermedades = medicamento.getEnfermedades();
        model.addAttribute("enfermedades", enfermedades);

        if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }

        return "infoDetalladaMedicamento";
    }
}