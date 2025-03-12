package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoFormato")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FormatoPPEntity.class, name = "PP"),
    @JsonSubTypes.Type(value = FormatoTIEntity.class, name = "TI")
})
public abstract class FormatoEntity {
    
    Integer id;
    Date fecha;
    String estado;
    String titulo;
    String director;
    String tipoFormato;
    //List<String> listaObjetivos;

    public FormatoEntity(){
        
    }
}
