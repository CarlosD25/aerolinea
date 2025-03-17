package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.TestContainerSettings;
import edu.unimagdalena.sistemavuelo.entidades.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@Import(TestContainerSettings.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VueloRepositorioTest {

    private Pasaporte pasaporte;
    private Vuelo vuelo;
    private Vuelo vuelo1;
    private Pasajero pasajero;
    private Reserva reserva;
    private Reserva reserva1;
    private Aerolinea aerolinea;
    private Set<Vuelo> vuelos= new HashSet<>();

    @Autowired
    private VueloRepositorio vueloRepositorio;

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private AerolineaRepositorio aerolineaRepositorio;
    @Autowired
    private PasajeroRepositorio pasajeroRepositorio;


    @Autowired
    private PasaporteRepositorio pasaporteRepositorio;


    @BeforeEach
    void setUp() {
        vuelo = new Vuelo();
        vuelo.setNumeroVuelo(UUID.randomUUID());
        vuelo.setOrigen("Santa Marta");
        vuelo.setDestino("Barranquilla");

        vuelo = vueloRepositorio.save(vuelo);

        vuelo1 = new Vuelo();
        vuelo1.setNumeroVuelo(UUID.randomUUID());
        vuelo1.setOrigen("Santa Marta");
        vuelo1.setDestino("Medellín");
        vuelo1= vueloRepositorio.save(vuelo1);


        vuelos.add(vuelo);
        vuelos.add(vuelo1);

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("987654321");
        pasaporte = pasaporteRepositorio.save(pasaporte);

        pasajero = new Pasajero();
        pasajero.setNombre("Juan Pérez");
        pasajero.setNid("123456789");
        pasajero.setPasaporte(pasaporte);
        pasajero = pasajeroRepositorio.save(pasajero);

        reserva = new Reserva();
        reserva.setCodigoReserva(UUID.randomUUID());
        reserva.setPasajero(pasajero);
        reserva.setVuelo(vuelo);
        reserva= reservaRepositorio.save(reserva);

        reserva1= new Reserva();
        reserva1.setCodigoReserva(UUID.randomUUID());
        reserva1.setPasajero(pasajero);
        reserva1.setVuelo(vuelo1);
        reserva1 = reservaRepositorio.save(reserva1);


        aerolinea = new Aerolinea();
        aerolinea.setNombre("Avianca");
        aerolinea.setVuelos(vuelos);
        aerolineaRepositorio.save(aerolinea);

    }
    @AfterEach
    void tearDown() {
        aerolineaRepositorio.deleteAll();
        reservaRepositorio.deleteAll();
        pasajeroRepositorio.deleteAll();
        pasaporteRepositorio.deleteAll();
        vueloRepositorio.deleteAll();


    }

    @Test
    void findByNumeroVuelo() {
        Optional<Vuelo> resultado = vueloRepositorio.findByNumeroVuelo(vuelo.getNumeroVuelo());
        assertTrue(resultado.isPresent());
        assertEquals(vuelo.getNumeroVuelo(), resultado.get().getNumeroVuelo());
    }

    @Test
    void findByOrigen() {
        List<Vuelo> resultado = vueloRepositorio.findByOrigenIgnoreCase("SAnta Marta");
        assertFalse(resultado.isEmpty());
        assertEquals(2, vuelos.size());
    }

    @Test
    void findByDestino() {
        List<Vuelo> resultado = vueloRepositorio.findByDestinoIgnoreCase("Medellín");
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void findByOrigenAndDestino() {
        List<Vuelo> resultado = vueloRepositorio.findByOrigenAndDestinoIgnoreCase("Santa Marta", "Barranquilla");
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void existsByNumeroVuelo() {
        boolean resultado= vueloRepositorio.existsByNumeroVuelo(vuelo.getNumeroVuelo());
        assertTrue(resultado);

    }

    @Test
    void buscarVueloPorCodigoReserva() {
        Optional<Vuelo> resultado = vueloRepositorio.buscarVueloPorCodigoReserva(reserva.getCodigoReserva());
        assertTrue(resultado.isPresent());
        assertEquals(vuelo.getId(), resultado.get().getId());

    }

    @Test
    void contarPasajerosPorVuelo() {
        long cantidadPasajeros = vueloRepositorio.contarPasajerosPorVuelo(vuelo.getId());
        assertEquals(1, cantidadPasajeros);
    }

    @Test
    void buscarVuelosPorPasajero() {
        List<Vuelo> resultado = vueloRepositorio.buscarVuelosPorPasajero(pasajero.getId());
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
        assertEquals(vuelo.getId(), resultado.get(0).getId());
        assertEquals(vuelo1.getId(), resultado.get(1).getId());
    }

    @Test
    void buscarVuelosPorAerolinea() {
        List<Vuelo> resultado = vueloRepositorio.buscarVuelosPorAerolinea(aerolinea.getNombre());
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.contains(vuelo));
        assertEquals(2,resultado.size());
    }

    @Test
    void vueloTieneReservas() {
        boolean resultado = vueloRepositorio.vueloTieneReservas(vuelo.getId());
        assertTrue(resultado);
    }
}