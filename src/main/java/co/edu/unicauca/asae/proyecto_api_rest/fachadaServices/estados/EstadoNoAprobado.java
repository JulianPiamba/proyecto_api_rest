package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;

public class EstadoNoAprobado implements EstadoInt {

    @Override
    public Resultado enviarParaEvaluacion(Formato formato){
        return new Resultado(false,"Un formato rechazado no puede enviarse a evaluacion");
        
    }
    
    @Override
    public Resultado aprobarFormato(Formato formato){
        return new Resultado(false, "Un formato rechazado no puede aprobarse");
    }

    @Override
    public Resultado noAprobarFormato(Formato formato){
        return new Resultado(false,"Un formato rechazado no puede ser rechazado nuevamente");
    }
    
    @Override
    public Resultado corregirFormato(Formato formato){
        return new Resultado(false,"Un formato rechazado no puede ser enviado a correcciones");
    }

    @Override
    public String toString(){
        return "Rechazado";
    }
}
