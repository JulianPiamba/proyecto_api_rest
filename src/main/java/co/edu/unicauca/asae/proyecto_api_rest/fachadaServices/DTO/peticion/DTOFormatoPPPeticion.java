package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion;
import com.fasterxml.jackson.annotation.JsonTypeName;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonTypeName("PP")
public class DTOFormatoPPPeticion extends DTOFormato{
    private String asesor;
    private String estudiante;

    public DTOFormatoPPPeticion(){
        
    }

}
