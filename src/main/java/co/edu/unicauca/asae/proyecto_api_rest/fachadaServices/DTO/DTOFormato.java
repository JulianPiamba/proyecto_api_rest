package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
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

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipoFormato", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DTOFormatoPPPeticion.class, name = "PP"),
    @JsonSubTypes.Type(value = DTOFormatoTIPeticion.class, name = "TI"),
    @JsonSubTypes.Type(value = DTOFormatoPPRespuesta.class, name = "PP"),
    @JsonSubTypes.Type(value = DTOFormatoTIRespuesta.class, name = "TI")
})
public  class DTOFormato {
    Integer id;
    Date fecha;
    String titulo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String tipoFormato; 
    String director;

    public DTOFormato(){
    }



}