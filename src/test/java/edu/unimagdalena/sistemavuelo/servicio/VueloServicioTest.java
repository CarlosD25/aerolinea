package edu.unimagdalena.sistemavuelo.servicio;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import edu.unimagdalena.sistemavuelo.repositorio.VueloRepositorio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        Vuelo vuelo = new Vuelo(null, UUID.randomUUID(), "Santa Marta", "Barranquilla", null, null);

        when(vueloRepositorio.save(any(Vuelo.class))).thenAnswer(invocation -> {
            Vuelo vueloGuardado = invocation.getArgument(0);
            vueloGuardado.setId(1L); // Simula que JPA genera el ID
            return vueloGuardado;
        });

        Vuelo crearVuelo = vueloServicio.createVuelo(vuelo);

        assertNotNull(crearVuelo.getId()); // Ahora el ID no será null
        assertEquals(vuelo.getNumeroVuelo(), crearVuelo.getNumeroVuelo()); // Corrige esta línea (antes comparabas con getId)
        assertEquals("Santa Marta", crearVuelo.getOrigen());
        assertEquals("Barranquilla", crearVuelo.getDestino());
        verify(vueloRepositorio, times(1)).save(vuelo);
    }


    @Test
    void obtenerVuelo() {
        Vuelo vuelo = new Vuelo(1L, UUID.randomUUID(), "Santa Marta", "Barranquilla", null, null);

        when(vueloRepositorio.findById(1L)).thenReturn(Optional.of(vuelo));

        Optional<Vuelo> vueloObtenido = vueloServicio.obtenerVuelo(1L);

        assertTrue(vueloObtenido.isPresent());
        assertEquals(1L, vueloObtenido.get().getId());
        assertEquals("Santa Marta", vueloObtenido.get().getOrigen());
        assertEquals("Barranquilla", vueloObtenido.get().getDestino());


        verify(vueloRepositorio, times(1)).findById(1L);
    }

    @Test
    void obtenerVuelo_NoEncontrado() {

        when(vueloRepositorio.findById(2L)).thenReturn(Optional.empty());

        Optional<Vuelo> vueloObtenido = vueloServicio.obtenerVuelo(2L);

        assertFalse(vueloObtenido.isPresent());

        verify(vueloRepositorio, times(1)).findById(2L);
    }
}