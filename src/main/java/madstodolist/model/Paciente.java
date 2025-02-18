package madstodolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "pacientes", schema = "health_database")
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "NSS", nullable = false)
    private String nss;

    @Column(name = "edad", nullable = true)
    private Integer edad;

    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enfermedad", nullable = false)
    private Enfermedad enfermedad;

    @Column(name = "Objetivo", nullable = false)
    private Long objetivo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tarjeta", nullable = true)
    private Tarjeta tarjeta;

    @Lob
    @Column(name = "profilePicture", nullable = true)
    @JsonIgnore
    private byte[] profilePicture;

    @OneToOne(mappedBy = "paciente")
    @Nullable
    private Usuario usuario;

    public Paciente(String nss, @Null Integer edad, String nombre, Long objetivo) {
        this.nss = nss;
        this.edad = edad;
        this.objetivo = objetivo;
        this.nombre = nombre;
    }

    public Paciente(Long id, String nss, @Null Integer edad, Long objetivo, String nombre) {
        this.id = id;
        this.nss = nss;
        this.edad = edad;
        this.objetivo = objetivo;
        this.nombre = nombre;
    }

    public Paciente(Long id, String nss, @Null Integer edad, Long objetivo, String nombre, Enfermedad enfermedad, Tarjeta tarjeta) {
        this.id = id;
        this.nss = nss;
        this.edad = edad;
        this.objetivo = objetivo;
        this.nombre = nombre;
        this.enfermedad = enfermedad;
        this.tarjeta = tarjeta;
    }

    public Paciente(String nss, @Null Integer edad, Long objetivo, String nombre, Enfermedad enfermedad, Tarjeta tarjeta) {
        this.nss = nss;
        this.edad = edad;
        this.objetivo = objetivo;
        this.nombre = nombre;
        this.enfermedad = enfermedad;
        this.tarjeta = tarjeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente paciente = (Paciente) o;
        if(this.id != null && Objects.equals(paciente.getId(), this.id)){
            return true;
        }
        return Objects.equals(id, paciente.id) && Objects.equals(nss, paciente.nss) && Objects.equals(edad, paciente.edad) && Objects.equals(nombre, paciente.nombre) && Objects.equals(enfermedad, paciente.enfermedad) && Objects.equals(objetivo, paciente.objetivo) && Objects.equals(tarjeta, paciente.tarjeta) && Arrays.equals(profilePicture, paciente.profilePicture) && Objects.equals(usuario, paciente.usuario);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nss, edad, nombre, enfermedad, objetivo, tarjeta, usuario);
        result = 31 * result + Arrays.hashCode(profilePicture);
        return result;
    }
}