package madstodolist.service;

import madstodolist.dto.TareaData;
import madstodolist.model.Enfermedad;
import madstodolist.model.Tarea;
import madstodolist.model.Usuario;
import madstodolist.repository.EnfermedadRepository;
import madstodolist.repository.TareaRepository;
import madstodolist.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EnfermedadService {

    @Autowired
    private EnfermedadRepository enfermedadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Enfermedad nuevaEnfermedad(String nombre, @Null String descripcion, @Null Short peligrosidad, boolean contagiable) {
        Enfermedad enfermedad = new Enfermedad(nombre,descripcion,peligrosidad,contagiable);
        enfermedadRepository.save(enfermedad);
        return enfermedad;
    }

    @Transactional(readOnly = true)
    public List<Enfermedad> allEnfermedades() {
        return StreamSupport.stream(enfermedadRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Enfermedad findById(Long enfermedadId) {
        return enfermedadRepository.findById(enfermedadId).orElse(null);
    }

    @Transactional
    public Enfermedad modificarEnfermedad(Long id, String nuevoNombre, String nuevaDescripcion, Short nuevaPeligrosidad, Boolean nuevoContagiable) {
        Enfermedad enfermedad = enfermedadRepository.findById(id).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada"));
        enfermedad.setNombre(nuevoNombre);
        enfermedad.setDescripcion(nuevaDescripcion);
        enfermedad.setPeligrosidad(nuevaPeligrosidad);
        enfermedad.setContagiable(nuevoContagiable);
        return enfermedadRepository.save(enfermedad);
    }

    @Transactional
    public void borraTarea(Long idEnfermedad) {
        enfermedadRepository.delete(enfermedadRepository.findById(idEnfermedad).orElseThrow(() -> new RuntimeException("Enfermedad no encontrada")));
    }
}