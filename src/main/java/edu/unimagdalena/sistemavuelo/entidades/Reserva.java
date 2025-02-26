package edu.unimagdalena.sistemavuelo.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private UUID codigoReserva;

    @ManyToOne
    @JoinColumn(name = "id_pasajero",nullable = false)
    private Pasajero pasajero;

    @ManyToOne
    @JoinColumn(name = "id_vuelo",nullable = false)
    private Vuelo vuelo;

}
