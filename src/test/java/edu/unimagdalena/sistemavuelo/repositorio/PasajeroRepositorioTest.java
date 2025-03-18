package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.TestContainerSettings;
import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import edu.unimagdalena.sistemavuelo.entidades.Reserva;
import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@ImportTestcontainers(TestContainerSettings.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PasajeroRepositorioTest {

    @Autowired
    private PasajeroRepositorio pasajeroRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private PasaporteRepositorio pasaporteRepositorio;
    @Autowired
    private VueloRepositorio vueloRepositorio;

    private Set<Reserva> reservas= new HashSet<>();
    private Long idG;
    private Long idP;
    private Pasajero pasajero;
    private Pasajero pasajero2;
    private Pasaporte pasaporte;
    private Pasaporte pasaporte2;
    private Reserva reserva;
    private Reserva reserva2;
    private Vuelo vuelo;
    private Vuelo vuelo2;

    @BeforeEach
    void setUp() {
        pasajero=new Pasajero();
        pasaporte= new Pasaporte();
        reserva=new Reserva();
        vuelo=new Vuelo();

        pasajero2=new Pasajero();
        pasaporte2=new Pasaporte();
        reserva2=new Reserva();
        vuelo2=new Vuelo();

        vuelo.setDestino("Valledupar");
        vuelo.setOrigen("Santa marta");
        vuelo.setNumeroVuelo(UUID.randomUUID());

        vuelo2.setDestino("Cienaga");
        vuelo2.setOrigen("san juan");
        vuelo2.setNumeroVuelo(UUID.randomUUID());
        vueloRepositorio.save(vuelo);
        vueloRepositorio.save(vuelo2);

        pasaporte.setNumero("018000");
        pasaporteRepositorio.save(pasaporte);
        idP=pasaporte.getId();

        pasaporte2.setNumero("019000");
        pasaporteRepositorio.save(pasaporte2);

        pasajero.setNombre("Carlos");
        pasajero.setNid("123");
        pasajero.setPasaporte(pasaporte);
        pasajero.setReservas(new HashSet<>());
        pasajeroRepositorio.save(pasajero);
        idG=pasajero.getId();

        pasajero2.setNombre("andres");
        pasajero2.setNid("456");
        pasajero2.setPasaporte(pasaporte2);
        pasajero2.setReservas(new HashSet<>());
        pasajeroRepositorio.save(pasajero2);
        



        reserva.setCodigoReserva(UUID.randomUUID());
        reserva.setVuelo(vuelo);
        reserva.setPasajero(pasajero);
        reservas.add(reserva);

        reserva2.setCodigoReserva(UUID.randomUUID());
        reserva2.setVuelo(vuelo2);
        reserva2.setPasajero(pasajero);
        reservas.add(reserva2);
        reservaRepositorio.save(reserva);
        reservaRepositorio.save(reserva2);
        pasajero.setReservas(reservas);


    }

    @AfterEach
    void tearDown() {
        reservaRepositorio.deleteAll();

        pasajeroRepositorio.deleteAll();
        pasaporteRepositorio.deleteAll();
        vueloRepositorio.deleteAll();
    }

    @Test
    void findByNombre() {
        Optional<Pasajero> pasajero= pasajeroRepositorio.findByNombre("Carlos");
        assertTrue(pasajero.isPresent());
        assertEquals("Carlos",pasajero.get().getNombre());
    }

    @Test
    void findByNid() {
        Optional<Pasajero> pasajero = pasajeroRepositorio.findByNid("123");
        assertTrue(pasajero.isPresent());
        assertEquals("123",pasajero.get().getNid());
        assertEquals("Carlos",pasajero.get().getNombre());
    }

    @Test
    void findByNidAndId() {
        Optional<Pasajero> pasajero= pasajeroRepositorio.findByNidAndId("123",idG);
        assertTrue(pasajero.isPresent());
        assertEquals("123",pasajero.get().getNid());
        assertTrue(pasajero.get().getId().equals(idG));
    }

    @Test
    void findByPasaporte() {
        Optional<Pasajero> pasajero = pasajeroRepositorio.findByPasaporte(pasaporte);
        assertTrue(pasajero.isPresent());
        assertEquals("018000",pasajero.get().getPasaporte().getNumero());
    }

    @Test
    void findByReservasIsNotEmpty() {
        List<Pasajero> pasajeroList = pasajeroRepositorio.findByReservasIsNotEmpty();
        assertTrue(pasajeroList.size()>0);

    }

    @Test
    void obtenerPasajerosOrdenadosPorNombre() {
        List<Pasajero> pasajeroList= pasajeroRepositorio.obtenerPasajerosOrdenadosPorNombre();
        assertTrue(!pasajeroList.isEmpty());
        for(Pasajero p:pasajeroList){
            System.out.println(p.getNombre());
        }
    }

    @Test
    void buscarPorNid() {
        Optional<Pasajero> pasajero = pasajeroRepositorio.buscarPorNid("123");
        assertTrue(pasajero.isPresent());
        assertEquals("123",pasajero.get().getNid());
        assertEquals("Carlos",pasajero.get().getNombre());
    }

    @Test
    void buscarPorIdPasaporte() {
        List<Pasajero> pasajeros = pasajeroRepositorio.buscarPorIdPasaporte(idP);
        assertTrue(!pasajeros.isEmpty());
        boolean encontrado=false;
        for(Pasajero pasajero1:pasajeros){
            if(pasajero1.getPasaporte().getId().equals(idP)){
                encontrado=true;
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    void buscarPasajerosConMultiplesReservas() {
        List<Pasajero> pasajeroList= pasajeroRepositorio.buscarPasajerosConMultiplesReservas();
        assertTrue(pasajeroList.size()>0);
        boolean multiplesReservas=false;
        for(Pasajero p:pasajeroList){
            System.out.println(p.getReservas());
            if(p.getReservas().size()>0){
                multiplesReservas=true;
                break;
            }
        }
        assertTrue(multiplesReservas);
        assertTrue(pasajeroList.size()==1);
    }

    @Test
    void contarPasajerosConReserva() {
        Long pasajerosConReserva = pasajeroRepositorio.contarPasajerosConReserva();
        assertTrue(pasajerosConReserva>0);

    }
}