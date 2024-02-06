package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;

import java.util.List;

public interface TorneoService {

    public String creaTorneo(Torneo torneo, Organizzatore organizzatore);

    public boolean iniziareTorneo(Torneo torneo,Organizzatore organizzatore);

    public boolean terminareTorneo(Torneo torneo,Organizzatore organizzatore);

    public String toglierePartecipanti(Torneo torneo, Giocatore giocatore, Organizzatore organizzatore);


    public Giocatore visualizzaProfiloUtente(Torneo torneo,Giocatore giocatore, Organizzatore organizzatore);

    public List<Giocatore> getPartecipanti(Torneo torneo);

    public Torneo findByName(String nome);

    public boolean seguireOrganizzatore(Giocatore giocatore, Organizzatore organizzatore);

    public List<Torneo> getTorneoIscritto(Giocatore giocatore);

    public String iscrizioneTorneo(Giocatore giocatore, Torneo torneo);

    public List<Torneo> cercareTorneo(String nome);
}
