package madstodolist.controller;

import madstodolist.authentication.ManagerUserSession;
import madstodolist.dto.UsuarioData;
import madstodolist.model.*;
import madstodolist.service.DonacionService;
import madstodolist.model.Paciente;
import madstodolist.service.EnfermedadService;
import madstodolist.service.PacienteService;
import madstodolist.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PacienteController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private DonacionService donacionService;
    @Autowired
    private EnfermedadService enfermedadService;

    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "pacientes";
    }

    @GetMapping("/pacientes/{id}")
    public String pacienteDetallado(@PathVariable(value="id") Long idPaciente, Model model) {

        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        Paciente paciente = pacienteService.findById(idPaciente);
        model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("paciente", paciente);
        Enfermedad enfermedad = paciente.getEnfermedad();
        model.addAttribute("enfermedad", enfermedad);

        if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }

        return "infoDetalladaPaciente";
    }

    @PostMapping("/pacientes/donacion/{idPaciente}")
    public String donar(@PathVariable Long idPaciente, @RequestParam int donativo, Model model) {
        Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;
        model.addAttribute("usuarioLogeado", usuarioLogeado);

        if (usuarioLogeado) {
            UsuarioData usuario = (usuarioService.findById(usuarioLogeadoId));
            model.addAttribute("usuario", usuario);
            Paciente paciente = pacienteService.findById(idPaciente);
            Tarjeta tarjeta = paciente.getTarjeta();
            donacionService.nuevaDonacion(tarjeta, usuarioService.getUsuario(usuario), donativo);
        }
        model.addAttribute("pacientes", pacienteService.allPacientes());
        return "Pacientes";
    }

    /* PARTE DEL CRUD */

    @GetMapping("/pacientes/editar/{id}")
    public String abrirEditarPaciente(@PathVariable(value="id") Long idPaciente, Model model) {

        /*Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;*/
        Paciente paciente = pacienteService.findById(idPaciente);
        //model.addAttribute("usuarioLogeado", usuarioLogeado);
        model.addAttribute("paciente", paciente);
        List<Enfermedad> enfermedades = enfermedadService.allEnfermedades();
        model.addAttribute("enfermedades", enfermedades);

        /*if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }*/

        return "modificarPaciente";
    }

    @GetMapping("/pacientes/crear")
    public String abrirCrearPaciente(Model model) {

        /*Long usuarioLogeadoId = managerUserSession.usuarioLogeado();
        boolean usuarioLogeado = usuarioLogeadoId != null;*/
        //model.addAttribute("usuarioLogeado", usuarioLogeado);

        /*if (usuarioLogeado) {
            UsuarioData usuario = usuarioService.findById(usuarioLogeadoId);
            model.addAttribute("usuario", usuario);
        }*/

        Paciente paciente = new Paciente();
        List<Enfermedad> enfermedades = enfermedadService.allEnfermedades();
        model.addAttribute("paciente", paciente);
        model.addAttribute("enfermedades", enfermedades);

        return "crearPaciente";
    }

    @PostMapping("/pacientes/editar/{id}")
    public String actualizarPaciente(@PathVariable(value="id") Long idPaciente, String nss, Integer edad, String nombre, String imagen) {
        pacienteService.updatePaciente(idPaciente, nss, edad, nombre, imagen);
        return "redirect:/pacientes/" + idPaciente;
    }

    /*@PostMapping("/pacientes/crear")
    public String crearPaciente(String nombre, String descripcion, short peligrosidad, boolean contagiable) {
        pacienteService.nuevoPaciente(nombre, descripcion, peligrosidad, contagiable);
        return "redirect:/pacientes";
    }*/

    @PostMapping("/pacientes/eliminar/{id}")
    public String borrarEnfermedad(@PathVariable(value="id") Long idPaciente) {
        pacienteService.borrarPaciente(idPaciente);
        return "redirect:/pacientes";
    }

}