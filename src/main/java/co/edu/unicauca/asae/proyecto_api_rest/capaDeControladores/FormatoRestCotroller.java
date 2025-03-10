package co.edu.unicauca.asae.proyecto_api_rest.capaDeControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services.IFormatoServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class FormatoRestCotroller {
    
    @Autowired
	@Qualifier("IDFachadaFormatoServices")
	private IFormatoServices formatoService;

    @GetMapping("/formato/{id}")
    public DTOFormato getFormato(@PathVariable Integer id) {
        DTOFormato objFormato = null;
        objFormato = formatoService.obtenerFormato(id);
        return objFormato;
    }

    @GetMapping("/formatos")
    public List<DTOFormato> listarClientes() {        
        // Definir las fechas usando LocalDate
        LocalDate inicio = LocalDate.of(2024, 3, 1);
        LocalDate fin = LocalDate.of(2024, 3, 9);

        // Convertir LocalDate a Date
        Date fechaInicio = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return formatoService.listarFormatosPorFecha(fechaInicio, fechaFin);
    }
    
    
}
