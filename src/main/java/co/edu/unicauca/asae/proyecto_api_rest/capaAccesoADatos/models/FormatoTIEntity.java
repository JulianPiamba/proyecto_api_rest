package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormatoTIEntity extends FormatoEntity {
    private String estudiante1;
    private String codigoEstudiante1;
    private String estudiante2;
    private String codigoEstudiante2;

    public FormatoTIEntity() {}

    public FormatoTIEntity(Integer id, Date fecha, String estado, String titulo, String director, 
                           String estudiante1, String codigoEstudiante1, 
                           String estudiante2, String codigoEstudiante2) {
        super(id, fecha, estado, titulo, director, "TI");
        this.estudiante1 = estudiante1;
        this.codigoEstudiante1 = codigoEstudiante1;
        this.estudiante2 = estudiante2;
        this.codigoEstudiante2 = codigoEstudiante2;
    }
}

