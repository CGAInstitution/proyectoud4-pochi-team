package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.model.Paciente;
import madstodolist.service.EnfermedadService;
import madstodolist.service.MedicamentoService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MedicamentoController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private MedicamentoService medicamentoService;
    @Autowired
    private EnfermedadService enfermedadService;

    @GetMapping("/medicamentos")
    public String abrirMedicamentos(Model model) {
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
    public String abrirMedicamentoDetallado(@PathVariable(value="id") Long idMedicamento, Model model) {

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

    @GetMapping("/medicamentos/editar/{id}")
    public String abrirEditarMedicamento(@PathVariable(value="id") Long idMedicamento, Model model) {

        /*Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;*/
        Medicamento medicamento = medicamentoService.getMedicamentoById(idMedicamento);
        //model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("medicamento", medicamento);
        List<Enfermedad> enfermedades = enfermedadService.allEnfermedades();
        model.addAttribute("enfermedades", enfermedades);

        /*if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }*/

        return "modificarMedicamento";
    }

    @PostMapping("/medicamentos/actualizar/{id}")
    public String actualizarMedicamento(@PathVariable(value="id") @ModelAttribute Medicamento medicamento, @RequestParam(required = false) List<Long> enfermedades) {
        Set<Enfermedad> enfermedadesNuevas = new HashSet<>();
        for (Long id : enfermedades) {
            Enfermedad enfermedad = enfermedadService.findById(id);
            enfermedadesNuevas.add(enfermedad);
        }
        medicamento.setEnfermedades(enfermedadesNuevas);
        System.out.println(medicamento.getEnfermedades().size());
        medicamentoService.updateMedicamento(medicamento.getId(), medicamento.getNombre(), medicamento.getDescripcion(),
                medicamento.getPrecio(), medicamento.isReceta()/*, enfermedadesNuevas*/);
        return "redirect:/medicamentos/" + medicamento.getId();
    }

    @PostMapping("/medicamentos/crear/{id}")
    public String crearMedicamento(@PathVariable(value="id") @ModelAttribute Medicamento medicamento) {
        /*System.out.println(medicamento);
        medicamentoService.addMedicamento(nombre, descripcion, precio, receta);*/
        return "redirect:/medicamentos";
    }

    @PostMapping("/medicamentos/borar/{id}")
    public String borrarMedicamento(@PathVariable(value="id") Long idPaciente) {
        medicamentoService.deleteMedicamento(idPaciente);
        return "redirect:/medicamentos";
    }
}