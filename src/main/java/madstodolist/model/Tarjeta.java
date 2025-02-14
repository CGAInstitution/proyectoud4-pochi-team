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

    @Column(name = "recaudado", nullable = false)
    private Long recaudado = 0L;

    @OneToOne(mappedBy = "tarjeta")
    private Paciente paciente;



    @ManyToMany
    @JoinTable(
            name = "usuario_tarjeta",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tarjeta_id")
    )
    private Set<Usuario> usuarios = new HashSet<>();

    public Tarjeta(String tarjeta_banco, Long recaudado, Paciente paciente) {
        this.tarjeta_banco = tarjeta_banco;
        this.recaudado = recaudado;
        this.paciente = paciente;
        this.objetivo = objetivo;
    }

    public int getProgreso(){
    return (int) ((getRecaudado() * 100.0) / getObjetivo());
    }
}