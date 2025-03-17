package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.TestContainerSettings;
import edu.unimagdalena.sistemavuelo.entidades.Aerolinea;
import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@Import(TestContainerSettings.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AerolineaRepositorioTest {

    private Vuelo vuelo = new Vuelo();
    private Vuelo vuelo1 = new Vuelo();
    private Set<Vuelo> vuelos= new HashSet<>();

    @Autowired
    private AerolineaRepositorio aerolineaRepositorio;
    @Autowired
    private VueloRepositorio vueloRepositorio;

    @BeforeEach
    void setUp() {



        vuelo.setNumeroVuelo(UUID.randomUUID());
        vuelo.setOrigen("Bogotá");
        vuelo.setDestino("Madrid");


        vuelo1.setNumeroVuelo(UUID.randomUUID());
        vuelo1.setOrigen("santa marta");
        vuelo1.setDestino("valledupar");

        for (int i = 0; i < 12; i++) {
            Vuelo nuevoVuelo = new Vuelo();
            nuevoVuelo.setNumeroVuelo(UUID.randomUUID());
            nuevoVuelo.setOrigen("Origen " + i);
            nuevoVuelo.setDestino("Destino " + i);
            vueloRepositorio.save(nuevoVuelo);
            vuelos.add(nuevoVuelo);
        }

        vueloRepositorio.save(vuelo);
        vueloRepositorio.save(vuelo1);
        vuelos.add(vuelo);
        vuelos.add(vuelo1);
        //aerolineaRepositorio.save(new Aerolinea("aerolinea con muchos vuelos", vuelos));

        aerolineaRepositorio.save(new Aerolinea("aerolinea", vuelos));

    }

    @AfterEach
    void tearDown() {
        aerolineaRepositorio.deleteAll();
        vueloRepositorio.deleteAll();
    }


    @Test
    void findByVuelosIn() {

        List<Aerolinea> aerolineas = aerolineaRepositorio.findByVuelosIn(vuelos);
        assertNotNull(aerolineas);
        assertFalse(aerolineas.isEmpty());
        assertEquals(1, aerolineas.size());
        assertTrue(aerolineas.get(0).getVuelos().containsAll(vuelos));

    }

    @Test

    void findByNombre() {
        Optional<Aerolinea> aerolinea = aerolineaRepositorio.findByNombreIgnoreCase("aerolinea");
        assertTrue(aerolinea.isPresent());
        assertEquals("aerolinea", aerolinea.get().getNombre());
    }

    @Test
    void findByVuelosId() {
        List<Aerolinea> aerolineas = aerolineaRepositorio.findByVuelosId(1L);
        assertFalse(aerolineas.isEmpty());
        assertEquals(1, aerolineas.size());
        assertEquals("aerolinea", aerolineas.get(0).getNombre());
    }

    @Test
    void countByVuelosId() {
        Vuelo vueloGuardado = vueloRepositorio.findAll().get(0);
        long count = aerolineaRepositorio.countByVuelosId(vueloGuardado.getId());
        assertEquals(1, count);
    }

    @Test
    void findByNombreContainingIgnoreCase() {
        List<Aerolinea> aerolineas = aerolineaRepositorio.findByNombreContainingIgnoreCase("AEROLINEA");
        assertFalse(aerolineas.isEmpty());
        assertEquals(1, aerolineas.size());
        assertEquals("aerolinea", aerolineas.get(0).getNombre());
    }

    @Test
    void areolineasConMasDe10Vuelos() {

        List<Aerolinea> aerolineas = aerolineaRepositorio.aerolineasConMasDe10Vuelos();
        assertFalse(aerolineas.isEmpty());
    }

    @Test
    void obtenerAerolineaPorNombre() {
        Optional<Aerolinea> aerolinea = aerolineaRepositorio.obtenerAerolineaPorNombre("aerolinea");
        assertTrue(aerolinea.isPresent());
        assertEquals("aerolinea", aerolinea.get().getNombre());
    }

    @Test
    void obtenerAerolineaPorId() {
        Optional<Aerolinea> aerolinea= aerolineaRepositorio.obtenerAerolineaPorId(2L);
        assertTrue(aerolinea.isPresent());
        assertEquals("aerolinea", aerolinea.get().getNombre());

    }

    @Test
    void contarVuelosPorId() {
    }

    @Test
    void obtenerAerolineasSinVuelos() {
    }
}