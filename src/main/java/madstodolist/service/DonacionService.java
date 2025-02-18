package madstodolist.service;

import madstodolist.model.Donacion;
import madstodolist.model.Tarjeta;
import madstodolist.model.Usuario;
import madstodolist.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class DonacionService {

    @Autowired
    private DonacionRepository donacionRepository;

    // Función que se encarga de realizar una donación insertando un valor en la relación de muchos a muchos (usuario_tarjeta)

    @Transactional
    public void nuevaDonacion(Tarjeta tarjeta, Usuario usuario, Long donativo) {
        Donacion donacion = new Donacion();
        donacion.setCantidad(donativo);
        donacion.setUsuario(usuario);
        donacion.setTarjeta(tarjeta);
        donacion.setFecha(new Date());
        donacionRepository.save(donacion);
    }
}