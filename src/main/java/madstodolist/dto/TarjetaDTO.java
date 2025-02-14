package madstodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TarjetaDTO {
    private Long id;
    private Long objetivo;
    private String tarjeta_banco;
    private Long recaudado;
    private int progreso;

    // Constructor, getters, and setters
}
