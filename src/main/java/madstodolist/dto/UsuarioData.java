package madstodolist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}