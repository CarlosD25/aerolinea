package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import edu.unimagdalena.sistemavuelo.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface PasajeroRepositorio extends JpaRepository<Pasajero, Long> {

    //Query Methods
    List<Pasajero> findByNombre(String nombre, Pageable pageable);
    Optional<Pasajero> findByNid(String nid);
    Optional<Pasajero> findByNidAndId(String nid, Long id);
    Optional<Pasajero> findById(Long id);
    Optional<Pasajero> findByPasaporte(Pasaporte pasaporte);

    //Query
    @Query
    ("select p from Pasajero p where p.id=?1")
    Pasajero buscarPorId(Long id);

    @Query
    ("select p from Pasajero p where p.nid=?1")
    Pasajero buscarPorNid(int nid);

    @Query
    ("select p from Pasajero p where p.reservas=?1")
    Pasajero buscarPorReserva(Reserva reserva);

    @Query
    ("select p from Pasajero p where p.pasaporte=?1")
    Pasajero buscarPorPasaporte(Pasaporte reserva);

    @Query
    ("select p from Pasajero p where p.reservas")
    Pasajero buscarPorPasaporte(Pasaporte reserva);



}
