package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Aerolinea;
import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AerolineaRepositorio extends JpaRepository<Aerolinea, Long> {

    //Query Methods

    List<Aerolinea> findByVuelosIn(Set<Vuelo> vuelos);
    Optional<Aerolinea> findByNombre(String nombre);
    List<Aerolinea> findByVuelosId(Long idVuelo);
    long countByVuelosId(Long idVuelo);
    List<Aerolinea> findByNombreContainingIgnoreCase(String nombre);

    //Query

    @Query
    ("select a from Aerolinea a where size(a.vuelos) > 10")
    List<Aerolinea> aerolineasConMasDe10Vuelos();

    @Query
    ("select a from Aerolinea a where a.nombre = ?1")
    Optional<Aerolinea> obtenerAerolineaPorNombre(String nombre);

    @Query
    ("select a from Aerolinea a where a.id=?1")
    Optional<Aerolinea> obtenerAerolineaPorId(Long idVuelo);

    @Query
    ("select size(a.vuelos) from Aerolinea a where a.id = ?1")
    long contarVuelosPorId(Long idAerolinea);

    @Query
    ("select a from Aerolinea a where size(a.vuelos) = 0")
    List<Aerolinea> obtenerAerolineasSinVuelos();

}
