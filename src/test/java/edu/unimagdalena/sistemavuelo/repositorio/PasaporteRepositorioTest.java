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
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainerSettings.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class PasaporteRepositorioTest {
    private Pasaporte pasaporte;
    private Pasajero pasajero;
    @Autowired
    private PasaporteRepositorio pasaporteRepositorio;


    @Autowired
    private PasajeroRepositorio pasajeroRepositorio;


    @BeforeEach
    void setUp() {
        pasaporte = new Pasaporte();
        pasaporte.setNumero("ABC123");
        pasaporte=pasaporteRepositorio.save(pasaporte);

        pasajero = new Pasajero();
        pasajero.setNombre("Juan Pérez");
        pasajero.setNid("123456789");
        pasajero.setPasaporte(pasaporte);

        pasajero = pasajeroRepositorio.save(pasajero);




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
    }

    @Test
    void count() {
    }

    @Test
    void deleteByNumero() {
    }

    @Test
    void buscarPorNumero() {
    }

    @Test
    void existePasaportePorNumero() {
    }

    @Test
    void buscarPorIdPasajero() {
    }

    @Test
    void contarPasaportes() {
    }

    @Test
    void eliminarPorNumero() {
    }
}