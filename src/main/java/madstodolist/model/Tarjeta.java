package madstodolist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tarjetas", schema = "health_database")
@Getter
@Setter
@NoArgsConstructor
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="Objetivo",nullable=false)
    private Long objetivo;

    @Column(name = "tarjeta_banco", nullable = false, length = 500)
    private String tarjeta_banco;


    @OneToOne(mappedBy = "tarjeta")
    private Paciente paciente;



    @OneToMany(mappedBy = "tarjeta", fetch = FetchType.EAGER)
    private Set<Donacion> donaciones = new HashSet<>();

    public Tarjeta(String tarjeta_banco, Paciente paciente, Long objetivo) {
        this.tarjeta_banco = tarjeta_banco;
        this.paciente = paciente;
        this.objetivo = objetivo;
    }

    public Long getRecaudado(){
        return donaciones.stream()
                .map(donacion -> BigInteger.valueOf(donacion.getCantidad()))
                .reduce(BigInteger.ZERO, BigInteger::add)
                .longValue();
    }

    public int getProgreso(){
    return (int) ((getRecaudado() * 100.0) / getObjetivo());
    }
}