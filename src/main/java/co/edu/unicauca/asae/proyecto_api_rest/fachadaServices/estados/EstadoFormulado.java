package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;

public class EstadoFormulado implements EstadoInt{

    @Override
    public Resultado enviarParaEvaluacion(Formato formato){
        EstadoEnEvaluacion objEstado = new EstadoEnEvaluacion();
        formato.setEstado(objEstado);
        return new Resultado(true, "Estado cambiado a evaluacion de manera exitosa");
        
    }
    
    @Override
    public Resultado aprobarFormato(Formato formato){
        return new Resultado(false, "Un formato formulado, no puede ser directamente aprobado");
    }

    @Override
    public Resultado noAprobarFormato(Formato formato){
        return new Resultado(false, "Un formato formulado, no puede ser directamente rechazado");
    }
    
    @Override
    public Resultado corregirFormato(Formato formato){
        return new Resultado(false, "Un formato formulado, no puede tener correcciones directamente");
    }

    @Override
    public String toString() {
        return "formulado";
    }  

}
