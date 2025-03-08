package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoEntity;
import org.springframework.stereotype.Repository;

@Repository("IDFormatoRepository")
public class FormatoRepository {
    
    private Map<Integer, FormatoEntity> mapaFormatos;

    public FormatoRepository(){
        this.mapaFormatos = new HashMap<>();
        //cargarformatos();
    }

    public Optional<FormatoEntity> obtenerFormato(Integer id){
        System.out.println("Invocando a consultar un formato");
        return Optional.ofNullable(mapaFormatos.get(id));
    }

    public Optional<Collection<FormatoEntity>> listarFormato(){
        System.out.println("Invocando a listar formatos");
        return mapaFormatos.isEmpty() ? Optional.empty() : Optional.of(mapaFormatos.values());
    }

    public FormatoEntity registrarFormato(FormatoEntity formato){
        System.out.println("Invocando a registrar formatos");
        this.mapaFormatos.put(formato.getId(), formato);        
        return formato;
    }

    public Optional<FormatoEntity> actualizarFormato(Integer id, FormatoEntity formato){

    }

}
