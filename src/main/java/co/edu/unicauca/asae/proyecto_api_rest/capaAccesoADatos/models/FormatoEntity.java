package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FormatoEntity {
    
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
