package madstodolist.controller;


import madstodolist.model.Enfermedad;
import madstodolist.model.Medicamento;
import madstodolist.model.Paciente;
import madstodolist.service.EnfermedadService;
import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.service.MedicamentoService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
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
    @Autowired
    private MedicamentoService medicamentoService;

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
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }

        return "infoDetalladaEnfermedad";
    }

    /* PARTE DEL CRUD */

    @GetMapping("/enfermedades/editar/{id}")
    public String abrirEditarEnfermedad(@PathVariable(value="id") Long idEnfermedad, Model model) {

        /*Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;*/
        Enfermedad enfermedad = enfermedadeService.findById(idEnfermedad);
        //model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("enfermedad", enfermedad);
        List<Medicamento> medicamentos = medicamentoService.getAllMedicamentos();
        model.addAttribute("medicamentos", medicamentos);

        /*if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }*/

        return "modificarEnfermedad";
    }

    @GetMapping("/enfermedades/crear")
    public String abrirEnfermedadCrear(Model model) {

        // Todo falta añadir medicamentos

        /*Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;*/
        //model.addAttribute("usuarioLogeado", usuarioLogeado);

        /*if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }*/

        Enfermedad enfermedad = new Enfermedad();
        List<Medicamento> medicamentos = medicamentoService.getAllMedicamentos();
        model.addAttribute("enfermedad", enfermedad);
        model.addAttribute("medicamentos", medicamentos);

        return "crearEnfermedad";
    }

    @PostMapping("/enfermedades/editar/{id}")
    public String actualizarEnfermedad(@PathVariable(value="id") Long idEnfermedad, String nombre, String descripcion, short peligrosidad, boolean contagiable, @RequestParam(required = false) List<Long> medicamentos) {
        enfermedadeService.modificarEnfermedad(idEnfermedad, nombre, descripcion, peligrosidad, contagiable, medicamentos);
        return "redirect:/enfermedades/" + idEnfermedad;
    }

    @PostMapping("/enfermedades/crear")
    public String crearEnfermedad(String nombre, String descripcion, short peligrosidad, boolean contagiable, @RequestParam(required = false) List<Long> medicamentos) {
        enfermedadeService.nuevaEnfermedad(nombre, descripcion, peligrosidad, contagiable, medicamentos);
        return "redirect:/enfermedades";
    }

    @PostMapping("/enfermedades/eliminar/{id}")
    public String borrarEnfermedad(@PathVariable(value="id") Long idEnfermedad) {
        enfermedadeService.borrarEnfermedad(idEnfermedad);
        return "redirect:/enfermedades";
    }

}

