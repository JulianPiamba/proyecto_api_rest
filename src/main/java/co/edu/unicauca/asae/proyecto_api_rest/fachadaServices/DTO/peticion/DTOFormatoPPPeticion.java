package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DTOFormatoPPPeticion extends DTOFormato{
    
    private int id;
    private String asesor;
    private String estudiante;

    public DTOFormatoPPPeticion(){
        
    }

}
