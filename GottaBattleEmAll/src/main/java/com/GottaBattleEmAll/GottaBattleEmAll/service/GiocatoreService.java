package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Pokemon;

public interface GiocatoreService {

    public String sostituireMembroTeam(Pokemon pokemon, int posizione, Giocatore giocatore);
}
