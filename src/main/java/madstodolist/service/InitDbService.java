package madstodolist.service;

import madstodolist.model.Usuario;
import madstodolist.repository.TareaRepository;
import madstodolist.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("dev")
public class InitDbService {

    @Autowired
    private UsuarioRepository usuarioService;
    @Autowired
    private TareaRepository tareaRepository;

    @PostConstruct
    public void initDatabase() {
    }

}