package com.GottaBattleEmAll.GottaBattleEmAll.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class Utente {
    protected String username;
    protected String nome;
    protected String cognome;
    protected String password;
    protected String email;
    @Enumerated(EnumType.STRING)
    protected Stato stato;
}
