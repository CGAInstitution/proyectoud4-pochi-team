package madstodolist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario_tarjeta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name="cantidad")
    private Long cantidad;


    @ManyToOne
    @JoinColumn(name="id",nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="id",nullable = false)
    private Tarjeta tarjeta;



}
