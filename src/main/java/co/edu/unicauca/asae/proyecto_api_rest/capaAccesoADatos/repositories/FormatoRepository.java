package co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoPPEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoTIEntity;

import org.springframework.stereotype.Repository;

@Repository("IDFormatoRepository")
public class FormatoRepository{
    
    private Map<Integer, FormatoEntity> mapaFormatos;

    public FormatoRepository(){
        this.mapaFormatos = new HashMap<>();
        cargarformatos();
    }

    public Optional<FormatoEntity> obtenerFormato(Integer id) {
        System.out.println("Invocando a consultar un formato");
        return Optional.ofNullable(mapaFormatos.get(id));
    }

    public Optional<Collection<FormatoEntity>> listarFormatosPorFecha(Date fechaInicio, Date fechaFin) {
        System.out.println("Invocando a listar formatos por fecha");
        Collection<FormatoEntity> formatosFiltrados = mapaFormatos.values().stream()
            .filter(formato -> formato.getFecha() != null && 
                    formato.getFecha().compareTo(fechaInicio) >= 0 &&
                    formato.getFecha().compareTo(fechaFin) <= 0)
            .collect(Collectors.toList());
        return formatosFiltrados.isEmpty() ? Optional.empty() : Optional.of(formatosFiltrados);
    }
    
    public FormatoEntity registrarFormato(FormatoEntity formato) {
        System.out.println("Invocando a registrar formato");
        this.mapaFormatos.put(formato.getId(), formato);
        return formato;
    }

    public Optional<FormatoEntity> actualizarFormato(Integer id, FormatoEntity formato) {
        System.out.println("Invocando a actualizar un formato");
        if (this.mapaFormatos.containsKey(id)) {
            this.mapaFormatos.put(id, formato);
            return Optional.of(formato);
        } else {
            return Optional.empty();
        }
    }

    private void cargarformatos() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            mapaFormatos.put(1, new FormatoPPEntity(1, sdf.parse("2023-03-09"), "formulado", "Título 1", "Director 1","Estudiante 1", "30", "Asesor 1"));
            mapaFormatos.put(2, new FormatoTIEntity(2, sdf.parse("2024-03-05"), "aprobado", "Título 2", "Director 2",
                                                    "Estudiante A", "001", "Estudiante B", "002"));
            mapaFormatos.put(3, new FormatoPPEntity(3, sdf.parse("2025-03-09"), "evaluar", "Título 3", "Director 3","Estudiante 3", "30", "Asesor 2"));
            mapaFormatos.put(4, new FormatoTIEntity(4, sdf.parse("2024-03-09"), "formulado", "Título 4", "Director 4",
                                                    "Estudiante C", "003", "Estudiante D", "004"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
