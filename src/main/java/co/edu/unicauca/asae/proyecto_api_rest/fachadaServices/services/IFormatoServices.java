package co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services;

import java.util.Date;
import java.util.List;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.*;
//import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.respuesta.DTOFormatoPPRespuesta;

public interface IFormatoServices {

    public DTOFormato obtenerFormato(Integer id);
    public List<DTOFormato> listarFormatosPorFecha(Date fechaInicio, Date fechaFin);
    public DTOFormato registrarFormato(DTOFormato formato);
    public DTOFormato actualizarFormato(Integer id, DTOFormato formato);
    public String cambiarEstado(Integer id, String estado);
    
}
