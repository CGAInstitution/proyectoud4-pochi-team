package madstodolist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @Null
    private String descripcion;

    @Null
    private int precio;

    @NotNull
    private boolean receta;

    @ManyToMany
    @JoinTable(
            name = "paciente_enfermedad",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "enfermedad_id")
    )
    private Set<Enfermedad> enfermedades = new HashSet<>();

    public Medicamento(Long id, String nombre, @Null String descripcion, @Null int precio, boolean receta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.receta = receta;
    }

    public Medicamento(String nombre, @Null String descripcion, @Null int precio, boolean receta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.receta = receta;
    }
}