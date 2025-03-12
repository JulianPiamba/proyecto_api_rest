package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion;
import com.fasterxml.jackson.annotation.JsonTypeName;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonTypeName("TI")
public class DTOFormatoTIPeticion extends DTOFormato{
    private String estudiante1;
    private String estudiante2;

    public DTOFormatoTIPeticion(){

    }
}
