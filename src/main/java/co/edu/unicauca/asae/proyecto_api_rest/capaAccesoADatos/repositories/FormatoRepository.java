package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoEntity;
import org.springframework.stereotype.Repository;

@Repository("IDFormatoRepository")
public class FormatoRepository{
    
    private Map<Integer, FormatoEntity> mapaFormatos;

        public FormatoRepository(){
            this.mapaFormatos = new HashMap<>();
            cargarformatos();
        }

        public Optional<FormatoEntity> obtenerFormato(Integer id){
            System.out.println("Invocando a consultar un formato");
            return Optional.ofNullable(mapaFormatos.get(id));
        }

        public Optional<Collection<FormatoEntity>> listarFormatosPorFecha(Date fechaInicio, Date fechaFin){
            System.out.println("Invocando a listar formatos por fecha");
            Collection<FormatoEntity> formatosFiltrados = mapaFormatos.values().stream()
                .filter(formato -> formato.getFecha().compareTo(fechaInicio) >= 0 &&
                            formato.getFecha().compareTo(fechaFin) <= 0)
                .collect(Collectors.toList());
            return formatosFiltrados.isEmpty() ? Optional.empty() : Optional.of(formatosFiltrados);
        }

        public FormatoEntity registrarFormato(FormatoEntity formato){
            System.out.println("Invocando a registrar formatos");
            this.mapaFormatos.put(formato.getId(), formato);        
            return formato;
        }

        public Optional<FormatoEntity> actualizarFormato(Integer id, FormatoEntity formato){
            Optional<FormatoEntity> respuesta;
            System.out.println("Invocando a actualizar un formato");
            if(this.mapaFormatos.containsKey(id)){
                this.mapaFormatos.put(id, formato);
                respuesta = Optional.of(formato);
            }else {
                respuesta = Optional.empty();
            }
            return respuesta;
        }

       private void cargarformatos() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            mapaFormatos.put(1, new FormatoEntity(1, sdf.parse("2024-03-01"), "Activo", "Título 1", "Director 1", "PP"));
            mapaFormatos.put(2, new FormatoEntity(2, sdf.parse("2024-03-05"), "Pendiente", "Título 2", "Director 2", "TI"));
            mapaFormatos.put(3, new FormatoEntity(3, sdf.parse("2024-03-09"), "Inactivo", "Título 3", "Director 3", "PP"));
            mapaFormatos.put(4, new FormatoEntity(4, sdf.parse("2024-03-09"), "Revisión", "Título 4", "Director 4", "TI"));
            } catch (Exception e) {
                e.printStackTrace();
            }   
        }


}
