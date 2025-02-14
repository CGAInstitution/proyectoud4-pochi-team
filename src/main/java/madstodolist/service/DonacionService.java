package madstodolist.service;

import madstodolist.dto.UsuarioData;
import madstodolist.model.Paciente;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;
import madstodolist.repository.PacienteRepository;
import madstodolist.repository.TarjetaRepository;
import madstodolist.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class DonacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Transactional
    public void nuevaDonacion(Tarjeta tarjeta, Usuario usuario, int donativo) {
//        tarjeta.setRecaudado(tarjeta.getRecaudado()+donativo);
//        usuario.setDonado(usuario.getDonado() + donativo);
//        Set<Usuario> usuarios = tarjeta.getUsuarios();
//        usuarios.add(usuario);
//        tarjeta.setUsuarios(usuarios);
//        tarjetaRepository.save(tarjeta);
//        usuarioRepository.save(usuario);
    }

//    @Transactional
//    public void borrarPaciente(Long idPaciente) {
//        pacienteRepository.deleteById(idPaciente);
//    }
//
//    @Transactional
//    public void updatePaciente(Long idPaciente, String nss, @Null Integer edad, String nombre,int objetivo) {
//        Paciente paciente = pacienteRepository.findById(idPaciente).orElse(null);
//        paciente.setNss(nss);
//        paciente.setEdad(edad);
//        paciente.setNombre(nombre);
//        paciente.setObjetivo(objetivo);
//        pacienteRepository.save(paciente);
//    }
//    @Transactional
//    public Paciente findById(Long idPaciente) {
//        return pacienteRepository.findById(idPaciente).orElse(null);
//    }
//
//    @Transactional
//    public List<Paciente> allPacientes() {
//        return StreamSupport.stream(pacienteRepository.findAll().spliterator(), false)
//                .collect(Collectors.toList());
//    }
}