package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion.DTOFormatoPPPeticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion.DTOFormatoTIPeticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoPPRespuesta;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoTIRespuesta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoFormato")
@JsonSubTypes({
    @JsonSubTypes.Type(value = DTOFormatoPPRespuesta.class, name = "PP"),
    @JsonSubTypes.Type(value = DTOFormatoTIRespuesta.class, name = "TI")
})

public class DTOFormato {

    Date fecha;
    String titulo;
    String tipoFormato; 
    String director;
    public DTOFormato(){
    }
}
