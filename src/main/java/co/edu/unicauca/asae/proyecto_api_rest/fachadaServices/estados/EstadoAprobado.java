package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;

public class EstadoAprobado implements EstadoInt {

     @Override
    public Resultado enviarParaEvaluacion(Formato formato){
        return new Resultado(false,"Un formato aprobado no puede enviarse a evaluacion");
        
    }
    
    @Override
    public Resultado aprobarFormato(Formato formato){
        return new Resultado(false, "Un formato aprobado no puede aprobarse nuevamente");
    }

    @Override
    public Resultado noAprobarFormato(Formato formato){
        return new Resultado(false,"Un formato aprobado no puede ser rechazado");
    }
    
    @Override
    public Resultado corregirFormato(Formato formato){
        return new Resultado(false,"Un formato aprobado no puede ser enviado a correcciones");
    }

    @Override
    public String toString(){
        return "Aprobado";
    }
}

