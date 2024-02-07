package com.GottaBattleEmAll.GottaBattleEmAll.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Partita {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "partita_giocatore",
            joinColumns = @JoinColumn(name = "partita_id"),
            inverseJoinColumns = @JoinColumn(name = "giocatore_id"))
    List<Giocatore> giocatoreList=new ArrayList<>();

    Long idVincitore;
}
