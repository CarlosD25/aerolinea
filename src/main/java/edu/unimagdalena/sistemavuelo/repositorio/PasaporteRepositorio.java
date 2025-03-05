package edu.unimagdalena.sistemavuelo.repositorio;

import edu.unimagdalena.sistemavuelo.entidades.Pasajero;
import edu.unimagdalena.sistemavuelo.entidades.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasaporteRepositorio extends JpaRepository<Pasajero, Long> {

    //Query Methods

    Optional<Pasaporte> encontrarPasaportePorNumero(String numero);
    boolean existePasaporte(String numero);
    Optional<Pasaporte> encontrarPasajero(Pasajero pasajero);
    long count();
    void eliminarPasaporte(String numero);

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
