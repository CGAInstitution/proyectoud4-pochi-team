package madstodolist.service;

import madstodolist.dto.TarjetaDTO;
import madstodolist.model.Donacion;
import madstodolist.model.Enfermedad;
import madstodolist.model.Tarjeta;
import madstodolist.repository.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Transactional
    public TarjetaDTO getTarjetaDTO(Long id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarjeta not found"));
        Long recaudado = tarjeta.getDonaciones().stream()
                .map(Donacion::getCantidad)
                .reduce(0L, Long::sum);
        int progreso = (int) ((recaudado * 100.0) / tarjeta.getPaciente().getObjetivo());

        return new TarjetaDTO(tarjeta.getId(), tarjeta.getPaciente().getObjetivo(), tarjeta.getTarjeta_banco(), recaudado, progreso);
    }

    @Transactional
    public Tarjeta getTarjetaWithDonaciones(Long id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarjeta not found"));
        tarjeta.getDonaciones().size(); // Initialize the collection
        return tarjeta;
    }

    @Transactional(readOnly = true)
    public Tarjeta findById(Long tarjetaId) {
        return tarjetaRepository.findById(tarjetaId)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));
    }
}