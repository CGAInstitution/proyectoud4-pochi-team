package madstodolist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Entity
@Table(name = "enfermedad", schema = "health_database")
@Getter
@Setter
@NoArgsConstructor
public class Enfermedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 500)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;

    @Size(max = 500)
    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "peligrosidad")
    private Short peligrosidad;

    @NotNull
    @Column(name = "contagiable", nullable = false)
    private Boolean contagiable = false;

    public Enfermedad(Long id, String nombre, @Null String descripcion, @Null Short peligrosidad, Boolean contagiable) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peligrosidad = peligrosidad;
        this.contagiable = contagiable;
    }

    public Enfermedad(String nombre, @Null String descripcion, @Null Short peligrosidad, Boolean contagiable) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.peligrosidad = peligrosidad;
        this.contagiable = contagiable;
    }
}