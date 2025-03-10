package co.edu.unicauca.asae.proyecto_api_rest.capaDeControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.DTO.DTOFormato;
import co.edu.unicauca.asae.proyecto_api_rest.fachadaServices.services.IFormatoServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PutMapping("/formato/{id}")
    public ResponseEntity<DTOFormato> actualizarFormato(@PathVariable Integer id, @RequestBody DTOFormato formatoDTO) {
        try {
            DTOFormato formatoActualizado = formatoService.actualizarFormato(id, formatoDTO);
            return ResponseEntity.ok(formatoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/formatos")
    public List<DTOFormato> listarClientes() {
        // Definir las fechas usando LocalDate
        LocalDate inicio = LocalDate.of(2024, 3, 1);
        LocalDate fin = LocalDate.of(2025, 12, 30);

        // Convertir LocalDate a Date
        Date fechaInicio = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return formatoService.listarFormatosPorFecha(fechaInicio, fechaFin);
    }

    @PostMapping("formatos")
    public ResponseEntity<DTOFormato> registrarFormato(@RequestBody DTOFormato formatoPeticionDTO) {
        if (formatoPeticionDTO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        DTOFormato formato = formatoService.registrarFormato(formatoPeticionDTO);
        return ResponseEntity.ok(formato);
    }

}
