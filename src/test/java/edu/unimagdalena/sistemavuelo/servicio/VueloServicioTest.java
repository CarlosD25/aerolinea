package edu.unimagdalena.sistemavuelo.servicio;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import edu.unimagdalena.sistemavuelo.repositorio.VueloRepositorio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VueloServicioTest {

    @Mock
    private VueloRepositorio vueloRepositorio;

    @InjectMocks
    private VueloServicio vueloServicio;

    public VueloServicioTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVuelo() {
        Vuelo vuelo = new Vuelo(null, UUID.randomUUID(),"Santa Marta", "Barranquilla",null,null);
        when(vueloRepositorio.save(any(Vuelo.class))).thenReturn(
                new Vuelo()
        )
    }

    @Test
    void obtenerVuelo() {
    }
}