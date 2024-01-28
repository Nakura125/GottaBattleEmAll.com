package com.GottaBattleEmAll.GottaBattleEmAll.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Giocatore extends Utente{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "squadra",
            joinColumns = @JoinColumn(name = "giocatore_id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id"))
    private List<Pokemon> pokemons;

    @ManyToMany(mappedBy = "giocatoreList")
    private List<Torneo> tornei;
}
