package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormatoPPEntity extends FormatoEntity{
    
    String estudiante;
    String codigo;
    String asesor;

    public FormatoPPEntity() {}

    public FormatoPPEntity(Integer id, Date fecha, String estado, String titulo, String director, String estudiante, String codigo, String asesor) {
        super(id, fecha, estado, titulo, director, "PP");
        this.estudiante = estudiante;
        this.codigo = codigo;
        this.asesor = asesor;
    }
}
