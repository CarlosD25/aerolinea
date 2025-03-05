package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface PasajeroRepositorio extends JpaRepository<Pasajero, Long> {

    //Query Methods
    Optional<Pasajero> findByNombre(String nombre);
    Optional<Pasajero> findByNid(String nid);
    Optional<Pasajero> findByNidAndId(String nid, Long id);
    Optional<Pasajero> findByPasaporte(Pasaporte pasaporte);
    List<Pasajero> findByReservasIsNotEmpty();


    //Query
    @Query("select p from Pasajero p order by p.nombre asc")
    List<Pasajero> obtenerPasajerosOrdenadosPorNombre();

    @Query("select p from Pasajero p where p.nid = ?1")
    Optional<Pasajero> buscarPorNid(String nid);

    @Query("select p from Pasajero p where p.pasaporte.id = ?1")
    List<Pasajero> buscarPorIdPasaporte(Long idPasaporte);

    @Query("select p from Pasajero p where size(p.reservas) > 1")
    List<Pasajero> buscarPasajerosConMultiplesReservas();

    @Query("select count(p) from Pasajero p where size(p.reservas) > 0")
    long contarPasajerosConReserva();



}
