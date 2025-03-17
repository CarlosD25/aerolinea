package edu.unimagdalena.sistemavuelo.Controlador;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import edu.unimagdalena.sistemavuelo.servicio.VueloServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/vuelos")
public class VuelosControlador {

    @Autowired
    private final VueloServicio vueloServicio;

    public VuelosControlador(VueloServicio vueloServicio) {
        this.vueloServicio = vueloServicio;
    }

    @PostMapping
    public ResponseEntity<Vuelo> crearVuelo(@RequestBody Vuelo vuelo) {
        Vuelo vueloCreado = vueloServicio.createVuelo(vuelo);
        return new ResponseEntity<>(vueloCreado, HttpStatus.CREATED);
    }


    @GetMapping("/{id]")
    public ResponseEntity<Vuelo> obtenerVuelo(@PathVariable Long id) {
        Optional<Vuelo> vuelo = vueloServicio.obtenerVuelo(id);
        return vuelo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
