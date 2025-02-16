package madstodolist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "medicamentos", schema = "health_database")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 500, nullable = false)
    private Long id;

    @Column(name = "nombre", length = 500, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "precio")
    private int precio = 0;

    @Column(name = "recetable", nullable = false)
    private boolean receta;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "enfermedades_medicamentos",
            joinColumns = @JoinColumn(name = "id_medicamento"),
            inverseJoinColumns = @JoinColumn(name = "id_enfermedad")
    )
    private Set<Enfermedad> enfermedades = new HashSet<>();


    public Medicamento(Long id, String nombre, @Null String descripcion, @Null int precio, boolean receta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.receta = receta;
    }

    public Medicamento(String nombre, @Null String descripcion, @Null int precio, boolean receta, Set<Enfermedad> enfermedades) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.receta = receta;
        this.enfermedades = enfermedades;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", receta=" + receta +
                ", enfermedades=" + enfermedades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}