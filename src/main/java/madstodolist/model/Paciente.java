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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enfermedad", nullable = false)
    private Enfermedad enfermedad;

    @Column(name = "Objetivo", nullable = false)
    private Long objetivo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "tarjeta",nullable = true)
    private Tarjeta tarjeta;

    @Lob
    @Column(name = "profilePicture", nullable = true)
    private byte[] profilePicture;

    @OneToOne(mappedBy = "paciente")
    private Usuario usuario;

    public Paciente(String nss, @Null Integer edad, String nombre, Long objetivo) {
        this.nss = nss;
        this.edad = edad;
        this.objetivo = objetivo;
        this.nombre = nombre;

    }

    public Paciente(String nss, @Null Integer edad, String nombre, Long objetivo, @Null String imagen) {
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
}