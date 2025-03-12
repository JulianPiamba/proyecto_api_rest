package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;

public class EstadoEnCorreccion implements EstadoInt {

     @Override
    public Resultado enviarParaEvaluacion(Formato formato){
        EstadoEnEvaluacion objEstado = new EstadoEnEvaluacion();
        formato.setEstado(objEstado);
        return new Resultado(true, "Estado cambiado a en evaluacion con exito");
        
    }
    
    @Override
    public Resultado aprobarFormato(Formato formato){
        return new Resultado(false,"Un formato con correciones no puede ser aprobado directamente");
    }

    @Override
    public Resultado noAprobarFormato(Formato formato){
        return new Resultado(false,"Un formato con correciones no puede ser rechazado directamente");
    }
    
    @Override
    public Resultado corregirFormato(Formato formato){
        return new Resultado(false,"Un formato con correciones no puede ser enviado a correciones");
    }

    @Override
    public String toString() {
        return "corregir";
    }    
}
