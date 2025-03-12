package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoPPEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.models.FormatoTIEntity;
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FormatoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.Formato;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
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
        FormatoEntity formatoEntity = this.servicioAccesoBaseDatos.obtenerFormato(id)
                .orElseThrow(() -> new NoSuchElementException("Formato con ID " + id + " no encontrado"));

        return modelMapper.map(formatoEntity, obtenerClaseDTO(formatoEntity));
    }

    private Class<? extends DTOFormato> obtenerClaseDTO(FormatoEntity formatoEntity) {
        if (formatoEntity instanceof FormatoPPEntity) {
            return DTOFormatoPPRespuesta.class;
        } else if (formatoEntity instanceof FormatoTIEntity) {
            return DTOFormatoTIRespuesta.class;
        } else {
            throw new IllegalArgumentException(
                    "Tipo de formato desconocido: " + formatoEntity.getClass().getSimpleName());
        }
    }

    @Override
    public List<DTOFormato> listarFormatosPorFecha(Date fechaInicio, Date fechaFin) {
        Collection<FormatoEntity> formatosEntity = servicioAccesoBaseDatos.listarFormatosPorFecha(fechaInicio, fechaFin)
                .orElse(Collections.emptyList());

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

    @Override
    public DTOFormato registrarFormato(DTOFormato formato) {
        if (formato.getTipoFormato() == null ||
                (!formato.getTipoFormato().equals("PP") &&
                        !formato.getTipoFormato().equals("TI"))) {
            throw new IllegalArgumentException("Tipo de formato debe ser PP o TI");
        }
        Class<? extends FormatoEntity> claseEntity = obtenerClaseEntity(formato.getTipoFormato());
        FormatoEntity formatoEntity = modelMapper.map(formato, claseEntity);

        if (formatoEntity.getFecha() == null) {
            Date fechaActual = new Date();
            formatoEntity.setFecha(fechaActual);
        }

        if (formatoEntity.getEstado() == null) {
            formatoEntity.setEstado("formulado");
        }

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

        switch (objFormatoEntity.getTipoFormato()) {
            case "PP":
                return modelMapper.map(objFormatoEntity, DTOFormatoPPRespuesta.class);
            case "TI":
                return modelMapper.map(objFormatoEntity, DTOFormatoTIRespuesta.class);
            default:
                throw new IllegalArgumentException("Tipo de formato desconocido: " + objFormatoEntity.getTipoFormato());
        }
    }

    private Class<? extends FormatoEntity> obtenerClaseEntity(String tipoFormato) {
        switch (tipoFormato) {
            case "PP":
                return FormatoPPEntity.class;
            case "TI":
                return FormatoTIEntity.class;
            default:
                throw new IllegalArgumentException("Tipo de formato desconocido: " + tipoFormato);
        }
    }

    @Override
    public DTOFormato actualizarFormato(Integer id, DTOFormato formatoDTO) {
        Optional<FormatoEntity> formatoExistenteOpt = this.servicioAccesoBaseDatos.obtenerFormato(id);
        if (formatoExistenteOpt.isEmpty()) {
            throw new NoSuchElementException("Formato con ID " + id + " no encontrado para actualizar");
        }

        FormatoEntity formatoExistente = formatoExistenteOpt.get();

        if (formatoDTO.getTipoFormato() != null &&
                !formatoDTO.getTipoFormato().equals(formatoExistente.getTipoFormato())) {
            throw new IllegalArgumentException("No se puede cambiar el tipo de formato");
        }
        Class<? extends FormatoEntity> claseEntity = obtenerClaseEntity(formatoExistente.getTipoFormato());

        FormatoEntity formatoActualizado = this.modelMapper.map(formatoDTO, claseEntity);
        formatoActualizado.setId(id);
        formatoActualizado.setTipoFormato(formatoExistente.getTipoFormato());

        if (formatoActualizado.getFecha() == null) {
            formatoActualizado.setFecha(formatoExistente.getFecha());
        }

        if (formatoActualizado.getEstado() == null) {
            formatoActualizado.setEstado(formatoExistente.getEstado());
        }

        if (formatoActualizado.getTitulo() == null) {
            formatoActualizado.setTitulo(formatoExistente.getTitulo());
        }

        if (formatoActualizado.getDirector() == null) {
            formatoActualizado.setDirector(formatoExistente.getDirector());
        }

        if (formatoExistente instanceof FormatoPPEntity && formatoActualizado instanceof FormatoPPEntity) {
            FormatoPPEntity ppExistente = (FormatoPPEntity) formatoExistente;
            FormatoPPEntity ppActualizado = (FormatoPPEntity) formatoActualizado;

            if (ppActualizado.getAsesor() == null) {
                ppActualizado.setAsesor(ppExistente.getAsesor());
            }

            if (ppActualizado.getEstudiante() == null) {
                ppActualizado.setEstudiante(ppExistente.getEstudiante());
            }
            if (ppActualizado.getCodigo() == null) {
                ppActualizado.setCodigo((ppExistente.getCodigo()));
            }
        } else if (formatoExistente instanceof FormatoTIEntity && formatoActualizado instanceof FormatoTIEntity) {
            FormatoTIEntity tiExistente = (FormatoTIEntity) formatoExistente;
            FormatoTIEntity tiActualizado = (FormatoTIEntity) formatoActualizado;

            if (tiActualizado.getEstudiante1() == null) {
                tiActualizado.setEstudiante1(tiExistente.getEstudiante1());
            }

            if (tiActualizado.getEstudiante2() == null) {
                tiActualizado.setEstudiante2(tiExistente.getEstudiante2());
            }

            if (tiActualizado.getCodigoEstudiante1() == null) {
                tiActualizado.setCodigoEstudiante1((tiExistente.getCodigoEstudiante1()));
            }

            if (tiActualizado.getCodigoEstudiante2() == null) {
                tiActualizado.setCodigoEstudiante2((tiExistente.getCodigoEstudiante2()));
            }
        }

        Optional<FormatoEntity> formatoActualizadoOpt = this.servicioAccesoBaseDatos.actualizarFormato(id,
                formatoActualizado);

        if (formatoActualizadoOpt.isEmpty()) {
            throw new RuntimeException("Error al actualizar el formato con ID " + id);
        }

        FormatoEntity formatoGuardado = formatoActualizadoOpt.get();

        DTOFormato formatoActualizadoDTO;
        switch (formatoGuardado.getTipoFormato()) {
            case "PP":
                formatoActualizadoDTO = modelMapper.map(formatoGuardado, DTOFormatoPPRespuesta.class);
                break;
            case "TI":
                formatoActualizadoDTO = modelMapper.map(formatoGuardado, DTOFormatoTIRespuesta.class);
                break;
            default:
                throw new IllegalArgumentException("Tipo de formato desconocido: " + formatoGuardado.getTipoFormato());
        }

        return formatoActualizadoDTO;
    }

    @Override
    public String cambiarEstado(Integer id, String estadoNuevo) {
        Optional<FormatoEntity> formatoExistenteOpt = this.servicioAccesoBaseDatos.obtenerFormato(id);
        if (formatoExistenteOpt.isEmpty()) {
            throw new NoSuchElementException("Formato con ID " + id + " no encontrado");
        }
        FormatoEntity formatoExistente = formatoExistenteOpt.get();
        obtenerEstadoActual(formatoExistente.getEstado());
        Resultado objResultado = null;
        switch (estadoNuevo.toLowerCase()) {
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
                return "No se puede puede enviar a estado formulado";
            default:
                break;
        }
        if (objResultado.cambioPermitido() == true) {
            String estadoActual = this.formato.getEstado().toString();
            System.out.println(estadoActual);
            formatoExistente.setEstado(estadoActual);
            DTOFormato formatoDTO = modelMapper.map(formatoExistente, DTOFormato.class);
            actualizarFormato(id, formatoDTO);
            return objResultado.mensaje();
        } else {
            return objResultado.mensaje();
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
