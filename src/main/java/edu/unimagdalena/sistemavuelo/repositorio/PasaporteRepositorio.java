package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PasaporteRepositorio extends JpaRepository<Pasaporte, Long> {

    //Query Methods

    Optional<Pasaporte> findByNumero(String numero);
    boolean existsByNumero(String numero);
    Optional<Pasaporte> findByPasajero(Pasajero pasajero);
    long count();


    //Query

    @Query
    ("select p from Pasaporte p where p.numero = ?1")
    Optional<Pasaporte> buscarPorNumero(String numero);

    @Query
    ("select count(p) > 0 from Pasaporte p where p.numero = ?1")
    boolean existePasaportePorNumero(String numero);

    @Query("select p from Pasaporte p where p.numero like ?1%")
    List<Pasaporte> buscarPorPrefijoNumero(String prefijo);

    @Query("select count(p.id) from Pasaporte p where p.pasajero is not null")
    long contarPasaportesActivos();

    @Modifying
    @Transactional
    @Query("update Pasajero pa set pa.pasaporte = null where pa.pasaporte.numero = ?1")
    void desvincularPasaporteEnPasajero(String numero);


}
