package madstodolist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

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

    public Medicamento(String nombre, @Null String descripcion, @Null int precio, boolean receta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.receta = receta;
    }

    public Medicamento(Long id, String nombre, @Null String descripcion, @Null int precio, boolean receta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.receta = receta;
    }
}