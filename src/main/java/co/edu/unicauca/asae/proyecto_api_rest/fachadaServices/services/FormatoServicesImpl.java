package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services;

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
    public DTOFormato obtenerFormato(Integer id){
        DTOFormato formatoRetornar = null;
        Optional<FormatoEntity> optionalFormato = this.servicioAccesoBaseDatos.obtenerFormato(id);
        if(optionalFormato.isPresent())
		{
			FormatoEntity formatoEntity = optionalFormato.get();
			formatoRetornar= this.modelMapper.map(formatoEntity, DTOFormato.class);
		}
        return formatoRetornar;
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

    @Override 
    public DTOFormato registrarFormato(DTOFormato formato){
        return null;
    }

    @Override
    public DTOFormato actualizarFormato(Integer id, DTOFormato formato){
        return null;
    }

    @Override 
    public void cambiarEstado(Integer id, String estado){
        
    }

}
