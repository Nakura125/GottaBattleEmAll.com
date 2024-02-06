package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;

public interface PartitaService {

    public boolean aggiungereRisultato(String risultato, Partita partita, Torneo torneo, Organizzatore organizzatore);
}
