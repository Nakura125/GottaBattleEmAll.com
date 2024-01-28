package com.GottaBattleEmAll.GottaBattleEmAll.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pokemon {
    @Id
    private String id;

    private String nome;
}
