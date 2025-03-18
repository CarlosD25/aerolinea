package edu.unimagdalena.sistemavuelo.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String nid;

    @OneToOne
    @JoinColumn(name = "id_pasaporte",referencedColumnName = "id", nullable = true)
    private Pasaporte pasaporte;

    @OneToMany(mappedBy = "pasajero")
    private Set<Reserva> reservas;
}
