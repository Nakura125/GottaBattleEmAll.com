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
public class Organizzatore extends Utente{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Torneo> tornei;


    @OneToOne(mappedBy = "organizzatore",cascade = CascadeType.DETACH)
    private Richiesta richiesta;

    @PreRemove
    public void preRemove() {
        if (richiesta != null)
            richiesta.setOrganizzatore(null);
    }

}
