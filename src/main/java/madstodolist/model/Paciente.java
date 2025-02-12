package madstodolist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;


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

    @Column(name = "imagen", nullable = true)
    private String imagen;


    @ManyToOne
    @JoinColumn(name = "enfermedad", nullable = false)
    private Enfermedad enfermedad;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarjeta")
    private Tarjeta tarjeta;

    public Paciente(String nss, @Null Integer edad, String nombre) {
        this.nss = nss;
        this.edad = edad;
        this.nombre = nombre;

    }

    public Paciente(Long id, String nss, @Null Integer edad, String nombre,String imagen) {
        this.id = id;
        this.nss = nss;
        this.edad = edad;
        this.nombre = nombre;
        this.imagen = imagen;
    }
}