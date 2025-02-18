package madstodolist.dto;

import lombok.Getter;
import lombok.Setter;
import madstodolist.model.Donacion;
import madstodolist.model.Paciente;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// Data Transfer Object para la clase Usuario
@Getter
@Setter
public class UsuarioData {

    private Long id;
    private String email;
    private String nombre;
    private String password;
    private Date fechaNacimiento;
    private boolean admin = false;
    private boolean bloqueado = false;
    private Long donado = 0L;
    private Set<Donacion> donaciones = new HashSet<>();
    private Paciente paciente;

    // Getters y setters
    // Sobreescribimos equals y hashCode para que dos usuarios sean iguales
    // si tienen el mismo ID (ignoramos el resto de atributos)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioData)) return false;
        UsuarioData that = (UsuarioData) o;
        return Objects.equals(getId(), that.getId());
    }

    public Long getDonado() {
        return donaciones.stream()
                .map(Donacion::getCantidad)
                .reduce(0L, Long::sum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean isPaciente() {
        return this.paciente != null;
    }
}