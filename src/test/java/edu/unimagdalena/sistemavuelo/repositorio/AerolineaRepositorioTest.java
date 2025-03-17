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


    private Set<Vuelo> vuelos= new HashSet<>();
    @Autowired
    private AerolineaRepositorio aerolineaRepositorio;
    @Autowired
    private VueloRepositorio vueloRepositorio;

    private Long idG;
    private Long idA;

    @BeforeEach
    void setUp() {

         Vuelo vuelo = new Vuelo();
         Vuelo vuelo1 = new Vuelo();


        vuelo.setNumeroVuelo(UUID.randomUUID());
        vuelo.setOrigen("Bogotá");
        vuelo.setDestino("Madrid");


        vuelo1.setNumeroVuelo(UUID.randomUUID());
        vuelo1.setOrigen("santa marta");
        vuelo1.setDestino("valledupar");

        vueloRepositorio.save(vuelo);
        idG=vuelo.getId();
        vueloRepositorio.save(vuelo1);

        vuelos.add(vuelo);
        vuelos.add(vuelo1);

        for (int i = 0; i < 12; i++) {
            Vuelo nuevoVuelo = new Vuelo();
            nuevoVuelo.setNumeroVuelo(UUID.randomUUID());
            nuevoVuelo.setOrigen("Origen " + i);
            nuevoVuelo.setDestino("Destino " + i);
            vueloRepositorio.save(nuevoVuelo);
            vuelos.add(nuevoVuelo);
        }



        //aerolineaRepositorio.save(new Aerolinea("aerolinea con muchos vuelos", vuelos));

        aerolineaRepositorio.save(new Aerolinea("aerolinea", vuelos));
        idA=aerolineaRepositorio.findByNombre("aerolinea").get().getId();

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
        Optional<Aerolinea> aerolinea = aerolineaRepositorio.findByNombre("aerolinea");
        assertTrue(aerolinea.isPresent());
        assertEquals("aerolinea", aerolinea.get().getNombre());
    }

    @Test
    void findAerolineaByVuelosId() {
        Optional<Aerolinea> aerolinea = aerolineaRepositorio.findAerolineaByVuelosId(idG);
        assertTrue(aerolinea.isPresent());

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
        Optional<Aerolinea> aerolinea= aerolineaRepositorio.obtenerAerolineaPorId(idA);
        assertTrue(aerolinea.isPresent());
        assertEquals("aerolinea", aerolinea.get().getNombre());

    }

    @Test
    void contarVuelosPorId() {
        Aerolinea aerolineaGuardada = aerolineaRepositorio.findAll().get(0);
        long count = aerolineaRepositorio.contarVuelosPorId(aerolineaGuardada.getId());
        assertEquals(14, count);
    }

    @Test
    void obtenerAerolineasSinVuelos() {
        aerolineaRepositorio.save(new Aerolinea("aerolinea sin vuelos", new HashSet<>()));
        List<Aerolinea> aerolineas = aerolineaRepositorio.obtenerAerolineasSinVuelos();
        assertFalse(aerolineas.isEmpty());
        boolean encontrado = false;
        for (Aerolinea aerolinea : aerolineas ) {
            if(aerolinea.getNombre().equalsIgnoreCase("aerolinea sin vuelos")) {
                encontrado=true;
                break;
            }
        }
        assertTrue(encontrado);
    }
}