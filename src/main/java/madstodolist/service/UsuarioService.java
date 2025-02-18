package madstodolist.service;

import madstodolist.dto.UsuarioData;
import madstodolist.model.Usuario;
import madstodolist.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Función que sirve para logearse introduciendo una contraseña y un email
    @Transactional(readOnly = true)
    public LoginStatus login(String eMail, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(eMail);
        if (!usuario.isPresent()) {
            return LoginStatus.USER_NOT_FOUND;
        } else if (!usuario.get().getPassword().equals(password)) {
            return LoginStatus.ERROR_PASSWORD;
        } else if (usuario.get().isBloqueado()) {
            return LoginStatus.USER_BLOCKED;
        } else {
            return LoginStatus.LOGIN_OK;
        }
    }

    // Función que convierte un objeto UsuarioData en un objeto Usuario
    @Transactional(readOnly = true)
    public Usuario getUsuario(UsuarioData usuarioData) {
        return modelMapper.map(usuarioData, Usuario.class);
    }

    // Función que obtiene un Usuario a partir de un email
    @Transactional(readOnly = true)
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Función que permite registrar un nuevo usuario en el sistema
    @Transactional
    public UsuarioData registrar(UsuarioData usuario) {
        Optional<Usuario> usuarioBD = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioBD.isPresent())
            throw new UsuarioServiceException("El usuario " + usuario.getEmail() + " ya está registrado");
        else if (usuario.getEmail() == null)
            throw new UsuarioServiceException("El usuario no tiene email");
        else if (usuario.getPassword() == null)
            throw new UsuarioServiceException("El usuario no tiene password");
        else {
            Usuario usuarioNuevo = modelMapper.map(usuario, Usuario.class);
            usuarioNuevo = usuarioRepository.save(usuarioNuevo);
            return modelMapper.map(usuarioNuevo, UsuarioData.class);
        }
    }

    // Función alternativa para obtener un usuario a partir de un email (creada por la profesora)
    @Transactional(readOnly = true)
    public UsuarioData findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    // Función que obtiene un usuario a partir de su ID
    @Transactional(readOnly = true)
    public UsuarioData findById(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return null;
        else {
            return modelMapper.map(usuario, UsuarioData.class);
        }
    }

    // Función que obtiene una lista con todos los usuarios
    @Transactional(readOnly = true)
    public List<Usuario> getAllUsers() {
        return StreamSupport.stream(usuarioRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Función que obtiene una lista de todos los usuarios pero en formato UsuarioData
    @Transactional(readOnly = true)
    public List<UsuarioData> getAllUsersData() {
        return StreamSupport.stream(usuarioRepository.findAll().spliterator(), false)
                .map(u -> modelMapper.map(u, UsuarioData.class))
                .collect(Collectors.toList());
    }

    // Función que actualiza el estado de administrador de un usuario
    public void updateAdminStatus(Long id, boolean admin) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setAdmin(admin);
        usuarioRepository.save(usuario);
    }

    // Función que actualiza el estado de bloqueo de un usuario
    public void updateBlockStatus(Long id, boolean blocked) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setBloqueado(blocked);
        usuarioRepository.save(usuario);
    }

    // Función que devuelve una lista con los 5 usuarios que más han donado
    @Transactional(readOnly = true)
    public List<UsuarioData> mayoresDonantes() {
        return getAllUsersData().stream()
                .sorted(Comparator.comparingLong(UsuarioData::getDonado).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    // Función que guarda un usuario en la base de datos
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    // Enumeración que representa los diferentes estados de inicio de sesión
    public enum LoginStatus {LOGIN_OK, USER_NOT_FOUND, ERROR_PASSWORD, USER_BLOCKED}
}