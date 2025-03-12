package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.EstadoInt;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.Resultado;


public class Formato {
    private EstadoInt estado;

    public Formato(){
        
    }
    
    public void setEstado(EstadoInt estado){
        this.estado = estado;
    }

    public EstadoInt getEstado() {
        return this.estado;
    }

    public Resultado enviarParaEvaluacion(){
        return this.estado.enviarParaEvaluacion(this);
    }

    public Resultado aprobarFormato(){
        return this.estado.aprobarFormato(this);
    }

    public Resultado noAprobarFormato(){
        return this.estado.noAprobarFormato(this);
    }

    public Resultado corregirFormato(){
        return this.estado.corregirFormato(this);
    }

}
