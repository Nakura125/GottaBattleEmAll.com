package com.GottaBattleEmAll.GottaBattleEmAll.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String nome;

    private int capienza;
    private LocalDate data;
    private String regole;
    private String premi;
    private String organizzazione;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Partita> partite;

    @ManyToMany
    @JoinTable(
            name = "torneo_giocatore",
            joinColumns = @JoinColumn(name = "torneo_id"),
            inverseJoinColumns = @JoinColumn(name = "giocatore_id"))
    List<Giocatore> giocatoreList;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    Organizzatore organizzatore;

    @Enumerated(EnumType.STRING)
    private StatoTorneo statoTorneo;

}
