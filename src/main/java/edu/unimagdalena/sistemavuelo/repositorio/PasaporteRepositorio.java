package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasaporteRepositorio extends JpaRepository<Pasaporte, Long> {

    //Query Methods

    Optional<Pasaporte> findByNumero(String numero);
    boolean existsByNumero(String numero);
    Optional<Pasaporte> findByPasajero(Pasajero pasajero);
    long count();
    void deleteByNumero(String numero);

    //Query

    @Query
    ("select p from Pasaporte p where p.numero = ?1")
    Optional<Pasaporte> buscarPorNumero(String numero);

    @Query
    ("select count(p) > 0 from Pasaporte p where p.numero = ?1")
    boolean existePasaportePorNumero(String numero);

    @Query
    ("select p from Pasaporte p where p.pasajero.id = ?1")
    Optional<Pasaporte> buscarPorIdPasajero(Long idPasajero);

    @Query
    ("select count(p) from Pasaporte p")
    long contarPasaportes();

    @Query
    ("delete from Pasaporte p where p.numero = ?1")
    void eliminarPorNumero(String numero);


}
