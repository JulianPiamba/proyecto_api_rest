package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DTOFormato {
    
    Date fecha;
    String titulo;

    public DTOFormato(){

    }
}
