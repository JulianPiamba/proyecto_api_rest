package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeName;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonTypeName("TI") 
public class DTOFormatoTIRespuesta extends DTOFormato{
    
    int id;
    String estudiante1;
    String estudiante2;
    String estado;
}
