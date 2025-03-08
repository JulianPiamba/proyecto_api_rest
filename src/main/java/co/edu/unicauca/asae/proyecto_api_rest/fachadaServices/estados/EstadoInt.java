package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;

public interface EstadoInt {
    
    Resultado enviarParaEvaluacion(Formato formato);
    Resultado aprobarFormato(Formato formato);
    Resultado noAprobarFormato(Formato formato);
    Resultado corregirFormato(Formato formato);
}
