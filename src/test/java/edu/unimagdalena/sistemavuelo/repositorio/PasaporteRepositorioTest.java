package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.TestContainerSettings;
import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
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

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainerSettings.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class PasaporteRepositorioTest {
    private Pasaporte pasaporte;
    private Pasaporte pasaporte1;
    private Pasajero pasajero;
    private Pasajero pasajero1;
    @Autowired
    private PasaporteRepositorio pasaporteRepositorio;


    @Autowired
    private PasajeroRepositorio pasajeroRepositorio;


    @BeforeEach
    void setUp() {

        pasaporte = new Pasaporte();
        pasaporte.setNumero("ABC123");
        pasaporte = pasaporteRepositorio.save(pasaporte);

        pasaporte1 = new Pasaporte();
        pasaporte1.setNumero("ABC2244");
        pasaporte1 = pasaporteRepositorio.save(pasaporte1);

        pasajero = new Pasajero();
        pasajero.setNombre("Juan Pérez");
        pasajero.setNid("123456789");
        pasajero.setPasaporte(pasaporte);
        pasajero=pasajeroRepositorio.save(pasajero);

        pasajero1 = new Pasajero();
        pasajero1.setNombre("Daniel Este");
        pasajero1.setNid("112312312");
        pasajero1.setPasaporte(pasaporte1);
        pasajero1=pasajeroRepositorio.save(pasajero1);


    }

    @AfterEach
    void tearDown() {

        pasajeroRepositorio.deleteAll();

        pasaporteRepositorio.deleteAll();


    }

    @Test
    void findByNumero() {
        Optional<Pasaporte> encontrado = pasaporteRepositorio.findByNumero("ABC123");
        assertTrue(encontrado.isPresent());
        assertEquals("ABC123", encontrado.get().getNumero());
    }

    @Test
    void existsByNumero() {
        boolean resultado= pasaporteRepositorio.existsByNumero(pasaporte.getNumero());
        assertTrue(resultado);

    }

    @Test
    void findByPasajero() {
        Optional<Pasaporte> resultado= pasaporteRepositorio.findByPasajero(pasajero);
        assertTrue(resultado.isPresent());
        assertEquals(pasaporte, resultado.get());

    }

    @Test
    void count() {
        Long resultado = pasaporteRepositorio.count();
        assertEquals(2L, resultado);
    }



    @Test
    void buscarPorNumero() {
        Optional<Pasaporte> resultado= pasaporteRepositorio.buscarPorNumero(pasaporte.getNumero());
        assertTrue(resultado.isPresent());
        assertEquals(pasaporte, resultado.get());
    }

    @Test
    void existePasaportePorNumero() {
        boolean resultado = pasaporteRepositorio.existePasaportePorNumero(pasaporte.getNumero());
        assertTrue(resultado);

    }

    @Test
    void buscarPorPrefijoNumero() {
        List<Pasaporte> resultado= pasaporteRepositorio.buscarPorPrefijoNumero("ABC");
        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());

    }

    @Test
    void contarPasaportesActivos() {
        Long resultado = pasaporteRepositorio.contarPasaportesActivos();
        assertEquals(2L, resultado);
    }

    @Test
    void desvincularPorNumero() {
        pasaporteRepositorio.desvincularPasaporteEnPasajero(pasaporte.getNumero());
        Optional<Pasaporte >  resultado= pasaporteRepositorio.findByPasajero(pasajero);
        assertFalse(resultado.isPresent());

    }
}