package co.edu.unicauca.asae.proyecto_api_rest.capaDeControladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/formato/{id}/{estado}")
    public ResponseEntity<String> cambiarEstado(@PathVariable Integer id, @PathVariable String estado) {
        String mensaje = formatoService.cambiarEstado(id, estado);
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/formatos")
    public ResponseEntity<? extends DTOFormato> registrarFormato(@RequestBody DTOFormato formato) {

        DTOFormato resultado = formatoService.registrarFormato(formato);
        return ResponseEntity.ok(resultado);
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

    @GetMapping("/formato/{fechaInicio}/{fechaFin}")
    public ResponseEntity<List<DTOFormato>> listarFormatosPorFecha(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {

        try {
            List<DTOFormato> formatos = formatoService.listarFormatosPorFecha(fechaInicio, fechaFin);

            if (formatos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(formatos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
