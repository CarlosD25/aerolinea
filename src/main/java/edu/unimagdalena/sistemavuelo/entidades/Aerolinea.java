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
public class Aerolinea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "AEROLINEA_VUELO",
            joinColumns = @JoinColumn(name = "id_aerolinea", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_vuelo",referencedColumnName = "id")
    )
    private Set<Vuelo> vuelos;
}
