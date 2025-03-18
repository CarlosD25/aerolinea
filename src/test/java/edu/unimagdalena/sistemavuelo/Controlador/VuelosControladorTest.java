package edu.unimagdalena.sistemavuelo.Controlador;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import edu.unimagdalena.sistemavuelo.servicio.VueloServicio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VuelosControladorTest {
    @Mock
    private VueloServicio vueloServicio;

    @InjectMocks
    private VuelosControlador vuelosControlador;

    public VuelosControladorTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearVuelo() {

        Vuelo vuelo = new Vuelo(null, UUID.randomUUID(), "Santa Marta", "Barranquilla", null, null);

        when(vueloServicio.createVuelo(vuelo)).thenReturn(new Vuelo(1L, vuelo.getNumeroVuelo(), "Santa Marta", "Barranquilla", null, null));

        ResponseEntity<Vuelo> response = vuelosControlador.crearVuelo(vuelo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Santa Marta", response.getBody().getOrigen());
        assertEquals("Barranquilla", response.getBody().getDestino());

        verify(vueloServicio, times(1)).createVuelo(vuelo);
    }


    @Test
    void obtenerVuelo() {
        Vuelo vuelo = new Vuelo(1L, UUID.randomUUID(), "Santa Marta", "Barranquilla", null, null);

        when(vueloServicio.obtenerVuelo(1L)).thenReturn(Optional.of(vuelo));

        ResponseEntity<Vuelo> response = vuelosControlador.obtenerVuelo(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Santa Marta", response.getBody().getOrigen());
        assertEquals("Barranquilla", response.getBody().getDestino());

        verify(vueloServicio, times(1)).obtenerVuelo(1L);
    }

    @Test
    void getVueloById_NotFound() {

        when(vueloServicio.obtenerVuelo(2L)).thenReturn(Optional.empty());

        ResponseEntity<Vuelo> response = vuelosControlador.obtenerVuelo(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody()); 

        verify(vueloServicio, times(1)).obtenerVuelo(2L);
    }

}
