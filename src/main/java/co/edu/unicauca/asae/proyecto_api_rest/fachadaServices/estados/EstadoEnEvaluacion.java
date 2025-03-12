package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;

public class EstadoEnEvaluacion implements EstadoInt {

     @Override
    public Resultado enviarParaEvaluacion(Formato formato){
        return new Resultado(false, "Un formato en evaluacion, no puede enviarse a evaluacion");
        
    }
    
    @Override
    public Resultado aprobarFormato(Formato formato){
        EstadoAprobado objEstado = new EstadoAprobado();
        formato.setEstado(objEstado);
        return new Resultado(true, "Estado cambiado a aprobado de manera exitosa");
    }

    @Override
    public Resultado noAprobarFormato(Formato formato){
        EstadoNoAprobado objEstado = new EstadoNoAprobado();
        formato.setEstado(objEstado);
        return new Resultado(true,"Estado cambiado a rechazado de manera exitosa");
    }
    
    @Override
    public Resultado corregirFormato(Formato formato){
        EstadoEnCorreccion objEstado = new EstadoEnCorreccion();
        formato.setEstado(objEstado);
        return new Resultado(true, "Estado cambiado a correcciones");
    }

    @Override
    public String toString() {
        return "evaluar";
    }  
}
