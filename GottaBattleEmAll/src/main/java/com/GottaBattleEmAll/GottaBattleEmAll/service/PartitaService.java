package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;

public interface PartitaService {

    public boolean aggiungereRisultato(Long risultato, Partita partita, Torneo torneo, Organizzatore organizzatore);

    public Partita creaPartita(Giocatore giocatore1, Giocatore giocatore2);
}
