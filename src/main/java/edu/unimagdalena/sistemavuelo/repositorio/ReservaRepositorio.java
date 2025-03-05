package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Reserva;
import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

    //Query Methods

    List<Reserva> encontrarPasajero(Pasajero pasajero);
    List<Reserva> findByVuelo(Vuelo vuelo);
    Optional<Reserva> findByCodigoReserva(UUID codigoReserva);
    long contarVuelos(Vuelo vuelo);
    void borrarPasajero(Pasajero pasajero);

    //Query

    @Query
    ("SELECT r FROM Reserva r JOIN FETCH r.pasajero JOIN FETCH r.vuelo")
    List<Reserva> obtenerTodasConDetalles();

    @Query
    ("SELECT r FROM Reserva r WHERE r.pasajero.id = ?1")
    List<Reserva> buscarPorIdPasajero(Long idPasajero);

    @Query
    ("SELECT r FROM Reserva r WHERE r.vuelo.id = ?1 ORDER BY r.id ASC")
    List<Reserva> buscarReservasPorVueloOrdenadas(Long idVuelo);

    @Query
    ("SELECT COUNT(r) FROM Reserva r WHERE r.vuelo.id = ?1")
    long contarReservasPorVuelo(Long idVuelo);

    @Query
    ("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.pasajero.id = ?1")
    boolean existeReservaPorPasajero(Long idPasajero);

}
