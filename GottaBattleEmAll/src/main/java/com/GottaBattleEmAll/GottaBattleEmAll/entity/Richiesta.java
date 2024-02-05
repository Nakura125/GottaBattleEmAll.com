package com.GottaBattleEmAll.GottaBattleEmAll.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Organizzatore organizzatore;

    @PreRemove
    public void preRemove() {
        organizzatore.setRichiesta(null);
    }

}