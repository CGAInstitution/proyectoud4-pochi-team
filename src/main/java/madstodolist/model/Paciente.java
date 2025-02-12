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

    @Column(name="Objetivo",nullable=false)
    private int objetivo;


    @ManyToOne
    @JoinColumn(name = "enfermedad", nullable = false)
    private Enfermedad enfermedad;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tarjeta")
    private Tarjeta tarjeta;

    public Paciente(String nss, @Null Integer edad, String nombre,int objetivo) {
        this.nss = nss;
        this.edad = edad;
        this.nombre = nombre;
        this.objetivo=objetivo;

    }

    public Paciente(Long id, String nss, @Null Integer edad, String nombre,String imagen,int objetivo) {
        this.id = id;
        this.nss = nss;
        this.edad = edad;
        this.nombre = nombre;
        this.imagen = imagen;
        this.objetivo=objetivo;
    }
}