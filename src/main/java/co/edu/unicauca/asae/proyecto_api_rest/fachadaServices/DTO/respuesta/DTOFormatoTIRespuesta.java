package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOFormatoTIRespuesta extends DTOFormato{
    String estudiante1;
    String estudiante2;
    String estado;
}
