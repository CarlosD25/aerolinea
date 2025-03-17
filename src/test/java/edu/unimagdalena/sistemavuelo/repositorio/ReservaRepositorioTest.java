package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.TestContainerSettings;
import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import edu.unimagdalena.sistemavuelo.entidades.Reserva;
import edu.unimagdalena.sistemavuelo.entidades.Vuelo;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
@Import(TestContainerSettings.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservaRepositorioTest {
    private Pasajero pasajero;
    private Vuelo vuelo;
    private Reserva reserva;
    private Pasaporte pasaporte;
    private Vuelo nuevoVuelo;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private VueloRepositorio vueloRepositorio;
    @Autowired
    private PasajeroRepositorio pasajeroRepositorio;
    @Autowired
    private PasaporteRepositorio pasaporteRepositorio;

    @BeforeEach
    void setUp() {
        pasaporte = new Pasaporte();
        pasaporte.setNumero("123456789");
        pasaporte = pasaporteRepositorio.save(pasaporte);

        pasajero = new Pasajero();
        pasajero.setNombre("Juan Pérez");
        pasajero.setNid("987654321");
        pasajero.setPasaporte(pasaporte);
        pasajero = pasajeroRepositorio.save(pasajero);

         nuevoVuelo= new Vuelo();
        nuevoVuelo.setNumeroVuelo(UUID.randomUUID());
        nuevoVuelo.setOrigen("Cali");
        nuevoVuelo.setDestino("Cartagena");
        nuevoVuelo = vueloRepositorio.save(nuevoVuelo);

        vuelo = new Vuelo();
        vuelo.setNumeroVuelo(UUID.randomUUID());
        vuelo.setOrigen("Bogotá");
        vuelo.setDestino("Medellín");
        vuelo = vueloRepositorio.save(vuelo);

        reserva = new Reserva();
        reserva.setCodigoReserva(UUID.randomUUID());
        reserva.setPasajero(pasajero);
        reserva.setVuelo(vuelo);
        reservaRepositorio.save(reserva);

        /*Reserva reserva1 = new Reserva();
        reserva1.setCodigoReserva(UUID.randomUUID());
        reserva1.setPasajero(pasajero);
        reserva1.setVuelo(vuelo);
        reservaRepositorio.save(reserva1);*/
    }

    @AfterEach
    void tearDown() {
        reservaRepositorio.deleteAll();
        vueloRepositorio.deleteAll();
        pasajeroRepositorio.deleteAll();
        pasaporteRepositorio.deleteAll();

    }

    @Test
    void findByPasajero() {
        List<Reserva> reservas = reservaRepositorio.findByPasajero(pasajero);
        assertFalse(reservas.isEmpty());
        assertEquals("987654321", reservas.get(0).getPasajero().getNid());
    }

    @Test
    void findByVuelo() {
        List<Reserva> reservas = reservaRepositorio.findByVuelo(vuelo);
        assertFalse(reservas.isEmpty());
        assertEquals(vuelo.getNumeroVuelo(), reservas.get(0).getVuelo().getNumeroVuelo());
    }

    @Test
    void findByCodigoReserva() {
        Optional<Reserva> reservaR = reservaRepositorio.findByCodigoReserva(reserva.getCodigoReserva());
        assertTrue(reservaR.isPresent());
        assertEquals(reserva.getId(), reservaR.get().getId());
    }

    @Test
    void countByVuelo() {
        long count = reservaRepositorio.countByVuelo(vuelo);
        assertEquals(1, count);
    }

    @Test
    void deleteByPasajero() {
        reservaRepositorio.deleteByPasajero(pasajero);
        List<Reserva> reservas = reservaRepositorio.findByPasajero(pasajero);
        assertTrue(reservas.isEmpty());
    }

    @Test
    void obtenerTodasConDetalles() {
        List<Reserva> reservas = reservaRepositorio.obtenerTodasConDetalles();
        assertFalse(reservas.isEmpty());

    }

    @Test
    void buscarPorIdPasajero() {
        List<Reserva> reservas = reservaRepositorio.buscarPorIdPasajero(pasajero.getId());
        assertFalse(reservas.isEmpty());
        assertEquals("987654321", reservas.get(0).getPasajero().getNid());

    }

    @Test
    void buscarReservasPorVueloOrdenadas() {
        List<Reserva> reservas = reservaRepositorio.buscarReservasPorVueloOrdenadas(vuelo.getId());
        assertFalse(reservas.isEmpty());
    }

    @Test
    void contarReservasPorVuelo() {
        long count = reservaRepositorio.contarReservasPorVuelo(vuelo.getId());
        assertEquals(1, count);
    }



    @Test
    @Transactional
    void actualizarVueloReserva() {

        reservaRepositorio.actualizarVuelo(reserva.getId(), nuevoVuelo.getId());


        entityManager.refresh(reserva);


        Optional<Reserva> resultado=reservaRepositorio.findById(reserva.getId());
        assertEquals(nuevoVuelo.getNumeroVuelo(), resultado.get().getVuelo().getNumeroVuelo());
    }


}