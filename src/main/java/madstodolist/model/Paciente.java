package madstodolist.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "NSS", nullable = false)
    private String nss;

    @Column(name = "edad")
    private Integer edad;

    @Size(max = 500)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;

    public Paciente() {
    }
    public Paciente( String nss, @Null Integer edad, String nombre) {
        this.nss = nss;
        this.edad = edad;
        this.nombre = nombre;
    }
}