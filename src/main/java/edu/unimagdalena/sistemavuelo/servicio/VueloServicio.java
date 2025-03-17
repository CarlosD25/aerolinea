package edu.unimagdalena.sistemavuelo.servicio;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import edu.unimagdalena.sistemavuelo.repositorio.VueloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VueloServicio {

    @Autowired
    private final VueloRepositorio vueloRepositorio;

    public VueloServicio(VueloRepositorio vueloRepositorio) {
        this.vueloRepositorio = vueloRepositorio;
    }

    public Vuelo createVuelo(Vuelo vuelo) {
        return this.vueloRepositorio.save(vuelo);
    }

    public Optional<Vuelo> obtenerVuelo(Long id) {
        return this.vueloRepositorio.findById(id);
    }
}
