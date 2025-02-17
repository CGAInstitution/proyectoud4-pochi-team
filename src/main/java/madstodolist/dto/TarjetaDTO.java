package madstodolist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import madstodolist.model.Usuario;

@Getter
@Setter
@AllArgsConstructor
public class TarjetaDTO {
    private Long id;
    private Long objetivo;
    private String tarjeta_banco;
    private Long recaudado;
    private int progreso;
}
