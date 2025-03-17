package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VueloRepositorio extends JpaRepository<Vuelo, Long> {

    //Query methods

    Optional<Vuelo> findByNumeroVuelo(UUID numeroVuelo);
    List<Vuelo> findByOrigenIgnoreCase(String origen);
    List<Vuelo> findByDestinoIgnoreCase(String destino);
    List<Vuelo> findByOrigenAndDestinoIgnoreCase(String origen, String destino);
    boolean existsByNumeroVuelo(UUID numeroVuelo);

    //Query

    @Query
    ("SELECT v FROM Vuelo v JOIN v.reservas r WHERE r.codigoReserva = ?1")
    Optional<Vuelo> buscarVueloPorCodigoReserva(UUID codigoReserva);

    @Query
      ("SELECT COUNT(r) FROM Vuelo v JOIN v.reservas r WHERE v.id = ?1")
    long contarPasajerosPorVuelo(Long idVuelo);
    
    @Query
       ("SELECT v FROM Vuelo v JOIN v.reservas r WHERE r.pasajero.id = ?1")
    List<Vuelo> buscarVuelosPorPasajero(Long idPasajero);

    @Query
       ("SELECT v FROM Vuelo v JOIN v.aerolineas a WHERE a.nombre = ?1")
    List<Vuelo> buscarVuelosPorAerolinea(String nombreAerolinea);
    
    @Query
       ("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Vuelo v JOIN v.reservas r WHERE v.id = ?1")
    boolean vueloTieneReservas(Long idVuelo);


}
