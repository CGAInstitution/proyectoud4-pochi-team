package madstodolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Objects;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "enfermedades", schema = "health_database")
@Getter
@Setter
@NoArgsConstructor
public class Enfermedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 500)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "peligrosidad")
    private Short peligrosidad;

    @Column(name = "contagiable", nullable = false)
    private Boolean contagiable = false;

    @OneToMany(mappedBy = "enfermedad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Paciente> pacientes = new HashSet<>();

    @ManyToMany(mappedBy = "enfermedades", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Medicamento> medicamentos = new HashSet<>();

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

    public void setMedicamentos(Set<Medicamento> medicamentos) {
        this.medicamentos.clear();
        for (Medicamento medicamento : medicamentos) {
            this.addMedicamento(medicamento);
        }
    }

    public void addMedicamento(Medicamento medicamento) {
        medicamentos.add(medicamento);
        medicamento.getEnfermedades().add(this);
    }

    @Override
    public String toString() {
        return "Enfermedad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", peligrosidad=" + peligrosidad +
                ", contagiable=" + contagiable +
                ", pacientes=" + pacientes +
                ", medicamentos=" + medicamentos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enfermedad that = (Enfermedad) o;
        return Objects.equals(id, that.id);  // Compara por id
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // Usa el id para el hash
    }

    public boolean isContagiable() {
        return contagiable;
    }
}