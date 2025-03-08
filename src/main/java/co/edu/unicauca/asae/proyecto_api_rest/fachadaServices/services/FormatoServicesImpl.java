package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;

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

    }

    @Override
    public List<DTOFormato> listarFormatos(){

    }

    @Override 
    public DTOFormato registrarFormato(DTOFormato formato){

    }

    @Override
    public DTOFormato actualizarFormato(Integer id, DTOFormato formato){
        
    }

    @Override 
    public void cambiarEstado(Integer id, String estado){
        
    }

}
