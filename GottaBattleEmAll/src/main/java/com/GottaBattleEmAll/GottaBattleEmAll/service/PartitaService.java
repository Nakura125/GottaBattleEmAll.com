package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;

import java.util.UUID;

public interface PartitaService {

    public boolean aggiungereRisultato(Integer risultato, Partita partita, Torneo torneo, Organizzatore organizzatore);

    public Partita creaPartita(Giocatore giocatore1, Giocatore giocatore2,Torneo torneo);

    public Partita getPartitaById(UUID id);
}
