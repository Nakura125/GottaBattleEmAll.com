package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.StatoTorneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;

import java.util.List;

public class TorneoServiceImpl implements TorneoService{

    private final TorneoRepository torneoRepository;

    private final OrganizzatoreRepository organizzatoreRepository;

    public TorneoServiceImpl(TorneoRepository torneoRepository, OrganizzatoreRepository organizzatoreRepository){
        this.torneoRepository = torneoRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    @Override
    public String creaTorneo(Torneo torneo, Organizzatore organizzatore) {
        if (torneo == null || torneo.getNome() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return "imput nulli";
        }

        if(torneo.getNome().isEmpty() || organizzatore.getUsername().isEmpty()){
            return "imput vuoti";
        }

        if (torneoRepository.findByNome(torneo.getNome()) != null) {
            return "nome torneo già esistente";
        }

        int capienza = torneo.getCapienza();
        if (capienza != 2 && (capienza < 4 || capienza > 16 || capienza % 4 != 0)) {
            return "capienza non valida.";
        }

        torneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);
        torneo.setOrganizzatore(organizzatore);
        torneoRepository.save(torneo);

        return "torneo creato con successo";

    }

    @Override
    public boolean iniziareTorneo(Torneo torneo, Organizzatore organizzatore) {
        return false;
    }

    @Override
    public boolean terminareTorneo(Torneo torneo, Organizzatore organizzatore) {
        return false;
    }

    @Override
    public String toglierePartecipanti(Torneo torneo, Giocatore giocatore, Organizzatore organizzatore) {
        return null;
    }

    @Override
    public Giocatore visualizzaProfiloUtente(Torneo torneo, Giocatore giocatore, Organizzatore organizzatore) {
        return null;
    }

    @Override
    public Torneo findByName(String nome) {
        return null;
    }

    @Override
    public boolean seguireOrganizzatore(Giocatore giocatore, Organizzatore organizzatore) {
        return false;
    }

    @Override
    public List<Torneo> getTorneoIscritto(Giocatore giocatore) {
        return null;
    }

    @Override
    public String iscrizioneTorneo(Giocatore giocatore, Torneo torneo) {
        return null;
    }

    @Override
    public List<Torneo> cercareTorneo(String nome) {
        return null;
    }
}
