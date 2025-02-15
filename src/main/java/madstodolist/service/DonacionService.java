package madstodolist.service;

import madstodolist.model.Donacion;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;
import madstodolist.repository.DonacionRepository;
import madstodolist.repository.TarjetaRepository;
import madstodolist.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class DonacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;
    @Autowired
    private DonacionRepository donacionRepository;

    @Transactional
    public void nuevaDonacion(Tarjeta tarjeta, Usuario usuario, Long donativo) {
        Donacion donacion = new Donacion();
        donacion.setCantidad(donativo);
        donacion.setUsuario(usuario);
        donacion.setTarjeta(tarjeta);
        donacion.setFecha(new Date());
        donacionRepository.save(donacion);
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