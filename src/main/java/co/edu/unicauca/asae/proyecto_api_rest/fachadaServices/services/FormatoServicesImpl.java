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
import co.edu.unicauca.asae.proyecto_api_rest.capaAccesoADatos.repositories.FormatoRepository;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion.DTOFormatoPPPeticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.peticion.DTOFormatoTIPeticion;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoPPRespuesta;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoTIRespuesta;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.estados.Resultado;

@Service("IDFachadaFormatoServices")
public class FormatoServicesImpl implements IFormatoServices{
    
    @Qualifier("IDFormatoRepository")
    private FormatoRepository servicioAccesoBaseDatos;

    private ModelMapper modelMapper;

    public FormatoServicesImpl(FormatoRepository servicioAccesoBaseDatos, ModelMapper modelMapper){
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper= modelMapper;
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
                        throw new IllegalArgumentException("Tipo de formato desconocido: " + formatoEntity.getTipoFormato());
                }
            })
            .orElseThrow(() -> new RuntimeException("Formato con ID " + id + " no encontrado"));
    }
    
    @Override
    public List<DTOFormato> listarFormatosPorFecha(Date fechaInicio, Date fechaFin) {
        Optional<Collection<FormatoEntity>> formatosEntityOpt = this.servicioAccesoBaseDatos.listarFormatosPorFecha(fechaInicio, fechaFin);
        
        if (formatosEntityOpt.isEmpty()) {
            return List.of();
        }
        
        Collection<FormatoEntity> formatosEntity = formatosEntityOpt.get();
        ObjectMapper objectMapper = new ObjectMapper();
        
        return formatosEntity.stream().map(entity -> {
            try {
                switch (entity.getTipoFormato()) {
                    case "PP":
                        return objectMapper.convertValue(entity, DTOFormatoPPRespuesta.class);
                    case "TI":
                        return objectMapper.convertValue(entity, DTOFormatoTIRespuesta.class);
                    default:
                        throw new IllegalArgumentException("Tipo de formato desconocido: " + entity.getTipoFormato());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al convertir FormatoEntity a DTOFormato", e);
            }
        }).collect(Collectors.toList());
    }

    /*@Override 
    public DTOFormato registrarFormato(DTOFormato formatoPeticionDTO) {
       
        FormatoEntity formatoEntity = this.modelMapper.map(formatoPeticionDTO, FormatoEntity.class);
        FormatoEntity objFormatoEntity = this.servicioAccesoBaseDatos.registrarFormato(formatoEntity);
        System.out.println(objFormatoEntity);
        DTOFormato formatoDTO = this.modelMapper.map(objFormatoEntity, DTOFormato.class);

        Date fechaActual = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        formatoDTO.setFecha(fechaActual);
        return formatoDTO;
    }*/
    @Override
    public DTOFormato registrarFormato(DTOFormato formatoPeticionDTO) {
        FormatoEntity formatoEntity = this.modelMapper.map(formatoPeticionDTO, FormatoEntity.class);
        Date fechaActual = new Date();
        formatoEntity.setFecha(fechaActual);
        System.out.println("Intentando guardar formato: " + formatoEntity);
        FormatoEntity objFormatoEntity = this.servicioAccesoBaseDatos.registrarFormato(formatoEntity);
        return this.modelMapper.map(objFormatoEntity, DTOFormato.class);
    }


    

    @Override
    public DTOFormato actualizarFormato(Integer id, DTOFormato formato){
        return null;
    }

    @Override 
    public void cambiarEstado(Integer id, String estado){
        
    }

}
