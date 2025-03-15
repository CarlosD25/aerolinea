package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VueloRepositorio extends JpaRepository<Vuelo, Long> {

    //Query methods

    Optional<Vuelo> findByNumeroVuelo(UUID numeroVuelo);
    List<Vuelo> findByOrigen(String origen);
    List<Vuelo> findByDestino(String destino);
    List<Vuelo> findByOrigenAndDestino(String origen, String destino);
    boolean existsByNumeroVuelo(UUID numeroVuelo);

    //Query

    @Query
    ("select v from Vuelo v where v.numeroVuelo = ?1")
    Optional<Vuelo> buscarPorNumeroVuelo(UUID numeroVuelo);
    
    @Query
    ("select v from Vuelo v where v.origen = ?1")
    List<Vuelo> buscarPorOrigen(String origen);
    
    @Query
    ("select v from Vuelo v where v.destino = ?1")
    List<Vuelo> buscarPorDestino(String destino);
    
    @Query
    ("select v from Vuelo v where v.origen = ?1 AND v.destino = ?2")
    List<Vuelo> buscarPorOrigenYDestino(String origen, String destino);
    
    @Query
    ("select count(v) from Vuelo v where v.origen = ?1")
    long contarVuelosPorOrigen(String origen);


}
