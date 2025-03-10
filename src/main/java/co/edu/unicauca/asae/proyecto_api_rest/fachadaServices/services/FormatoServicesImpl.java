package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoTIEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FormatoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion.DTOFormatoPPPeticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion.DTOFormatoTIPeticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoPPRespuesta;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoTIRespuesta;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.EstadoAprobado;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.EstadoEnCorreccion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.EstadoEnEvaluacion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.EstadoFormulado;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.EstadoNoAprobado;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.Resultado;

@Service("IDFachadaFormatoServices")
public class FormatoServicesImpl implements IFormatoServices {

    Formato formato = new Formato();

    @Qualifier("IDFormatoRepository")
    private FormatoRepository servicioAccesoBaseDatos;

    private ModelMapper modelMapper;

    public FormatoServicesImpl(FormatoRepository servicioAccesoBaseDatos, ModelMapper modelMapper) {
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper = modelMapper;
    }

    @Override
    public DTOFormato obtenerFormato(Integer id) {
        return this.servicioAccesoBaseDatos.obtenerFormato(id)
                .map(formatoEntity -> {
                    switch (formatoEntity.getTipoFormato()) {
                        case "PP":
                            return modelMapper.map(formatoEntity, DTOFormatoPPRespuesta.class);
                        case "TI":
                            return modelMapper.map(formatoEntity, DTOFormatoTIRespuesta.class);
                        default:
                            throw new IllegalArgumentException(
                                    "Tipo de formato desconocido: " + formatoEntity.getTipoFormato());
                    }
                })
                .orElseThrow(() -> new RuntimeException("Formato con ID " + id + " no encontrado"));
    }

    @Override
    public List<DTOFormato> listarFormatosPorFecha(Date fechaInicio, Date fechaFin) {
        Optional<Collection<FormatoEntity>> formatosEntityOpt = this.servicioAccesoBaseDatos
                .listarFormatosPorFecha(fechaInicio, fechaFin);

        if (formatosEntityOpt.isEmpty()) {
            return List.of();
        }

        Collection<FormatoEntity> formatosEntity = formatosEntityOpt.get();

        return formatosEntity.stream().map(entity -> {
            switch (entity.getTipoFormato()) {
                case "PP":
                    return modelMapper.map(entity, DTOFormatoPPRespuesta.class);
                case "TI":
                    return modelMapper.map(entity, DTOFormatoTIRespuesta.class);
                default:
                    throw new IllegalArgumentException("Tipo de formato desconocido: " + entity.getTipoFormato());
            }
        }).collect(Collectors.toList());
    }

    /*
     * @Override
     * public DTOFormato registrarFormato(DTOFormato formatoPeticionDTO) {
     * 
     * FormatoEntity formatoEntity = this.modelMapper.map(formatoPeticionDTO,
     * FormatoEntity.class);
     * FormatoEntity objFormatoEntity =
     * this.servicioAccesoBaseDatos.registrarFormato(formatoEntity);
     * System.out.println(objFormatoEntity);
     * DTOFormato formatoDTO = this.modelMapper.map(objFormatoEntity,
     * DTOFormato.class);
     * 
     * Date fechaActual =
     * Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
     * formatoDTO.setFecha(fechaActual);
     * return formatoDTO;
     * }
     */
    @Override
    public DTOFormato registrarFormato(DTOFormato formatoPeticionDTO) {
        // Validar el tipo de formato
        if (formatoPeticionDTO.getTipoFormato() == null || 
            (!formatoPeticionDTO.getTipoFormato().equals("PP") && 
             !formatoPeticionDTO.getTipoFormato().equals("TI"))) {
            throw new IllegalArgumentException("Tipo de formato debe ser PP o TI");
        }
        
        // Asegurar que título y director no sean nulos
        if (formatoPeticionDTO.getTitulo() == null || formatoPeticionDTO.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        
        if (formatoPeticionDTO.getDirector() == null || formatoPeticionDTO.getDirector().isEmpty()) {
            throw new IllegalArgumentException("El director no puede estar vacío");
        }
        
        FormatoEntity formatoEntity = this.modelMapper.map(formatoPeticionDTO, FormatoEntity.class);
        
        // Si no se proporcionó una fecha, usar la fecha actual
        if (formatoEntity.getFecha() == null) {
            Date fechaActual = new Date();
            formatoEntity.setFecha(fechaActual);
        }
        
        // Estado por defecto si es nulo
        if (formatoEntity.getEstado() == null) {
            formatoEntity.setEstado("Pendiente"); // O el estado inicial que prefieras
        }
        
        // Autogenerar ID si no existe
        if (formatoEntity.getId() == null) {
            int maxId = this.servicioAccesoBaseDatos.listarFormatosPorFecha(new Date(0), new Date())
                .map(collection -> collection.stream()
                    .mapToInt(FormatoEntity::getId)
                    .max()
                    .orElse(0))
                .orElse(0);
            formatoEntity.setId(maxId + 1);
        }
        
        FormatoEntity objFormatoEntity = this.servicioAccesoBaseDatos.registrarFormato(formatoEntity);
        
        // Convertir la entidad guardada de vuelta a DTO según su tipo
        switch (objFormatoEntity.getTipoFormato()) {
            case "PP":
                return modelMapper.map(objFormatoEntity, DTOFormatoPPRespuesta.class);
            case "TI":
                return modelMapper.map(objFormatoEntity, DTOFormatoTIRespuesta.class);
            default:
                throw new IllegalArgumentException("Tipo de formato desconocido: " + objFormatoEntity.getTipoFormato());
        }
    }

    @Override
    public DTOFormato actualizarFormato(Integer id, DTOFormato formatoDTO) {
        // Primero verificamos si el formato existe
        Optional<FormatoEntity> formatoExistenteOpt = this.servicioAccesoBaseDatos.obtenerFormato(id);

        if (formatoExistenteOpt.isEmpty()) {
            throw new RuntimeException("Formato con ID " + id + " no encontrado para actualizar");
        }

        FormatoEntity formatoExistente = formatoExistenteOpt.get();

        // Mapeamos el DTO a entidad, preservando el ID
        FormatoEntity formatoEntity = this.modelMapper.map(formatoDTO, FormatoEntity.class);
        formatoEntity.setId(id); // Aseguramos que el ID sea el mismo

        // Preservar la fecha si no se proporcionó una nueva
        if (formatoEntity.getFecha() == null) {
            formatoEntity.setFecha(formatoExistente.getFecha());
        }

        // Preservar otros campos si son nulos en el DTO de actualización
        if (formatoEntity.getEstado() == null) {
            formatoEntity.setEstado(formatoExistente.getEstado());
        }

        // Aquí podrías añadir más campos específicos según cada tipo de formato

        // Llamamos al repositorio para actualizar
        Optional<FormatoEntity> formatoActualizadoOpt = this.servicioAccesoBaseDatos.actualizarFormato(id,
                formatoEntity);

        if (formatoActualizadoOpt.isEmpty()) {
            throw new RuntimeException("Error al actualizar el formato con ID " + id);
        }

        FormatoEntity formatoActualizado = formatoActualizadoOpt.get();

        // Convertimos la entidad actualizada a DTO según su tipo
        DTOFormato formatoActualizadoDTO;
        switch (formatoActualizado.getTipoFormato()) {
            case "PP":
                formatoActualizadoDTO = modelMapper.map(formatoActualizado, DTOFormatoPPRespuesta.class);
                break;
            case "TI":
                formatoActualizadoDTO = modelMapper.map(formatoActualizado, DTOFormatoTIRespuesta.class);
                break;
            default:
                throw new IllegalArgumentException(
                        "Tipo de formato desconocido: " + formatoActualizado.getTipoFormato());
        }

        return formatoActualizadoDTO;
    }

    @Override
    public void cambiarEstado(Integer id, String estadoNuevo){
        // Primero verificamos si el formato existe
        Optional<FormatoEntity> formatoExistenteOpt = this.servicioAccesoBaseDatos.obtenerFormato(id);
        obtenerEstadoActual(formatoExistenteOpt.get().getEstado());
        Resultado objResultado = null;
        switch (estadoNuevo) {
            case "evaluar":
                objResultado = this.formato.enviarParaEvaluacion();
                break;
            case "corregir":
                objResultado = this.formato.corregirFormato();
                break;
            case "aprobado":
                objResultado = this.formato.aprobarFormato();
                    break;
            case "rechazado":
                objResultado = this.formato.noAprobarFormato();
                    break;
            case "formulado":
                break;
            default:
                break;
        }
    }

    
    public void obtenerEstadoActual(String estado) {
        switch (estado) {
            case "evaluar":
                formato.setEstado(new EstadoEnEvaluacion());
                break;
            case "corregir":
                formato.setEstado(new EstadoEnCorreccion());
                break;
            case "aprobado":
                formato.setEstado(new EstadoAprobado());
                break;
            case "rechazado":
                formato.setEstado(new EstadoNoAprobado());
                break;
            case "formulado":
                formato.setEstado(new EstadoFormulado());
                break;
        }
    }

}
