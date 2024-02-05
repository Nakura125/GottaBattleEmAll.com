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
        if (torneo == null || torneo.getNome() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return false;
        }
        if (torneo.getNome().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return false;
        }

        Torneo t = torneoRepository.findByNome(torneo.getNome());

        if(t == null || !organizzatore.equals(t.getOrganizzatore())){
            return false;
        }
        if(torneo.getStatoTorneo() != StatoTorneo.ATTESAISCRIZIONI){
            return false;
        }
        if (t.getGiocatoreList() == null || t.getGiocatoreList().size() != t.getCapienza()) {
            return false;
        }

        torneo.setStatoTorneo(StatoTorneo.INCORSO);
        //crea partite
        torneoRepository.save(t);
        return true;
    }

    @Override
    public boolean terminareTorneo(Torneo torneo, Organizzatore organizzatore) {
        if (torneo == null || torneo.getNome() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return false;
        }
        if (torneo.getNome().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return false;
        }

        Torneo t = torneoRepository.findByNome(torneo.getNome());

        if(t == null || !organizzatore.equals(t.getOrganizzatore())){
            return false;
        }

        if(t.getStatoTorneo() != StatoTorneo.INCORSO){
            return false;
        }

        t.setStatoTorneo(StatoTorneo.TORNEOCOMPLETATO);
        torneoRepository.save(t);
        return true;
    }

    @Override
    public String toglierePartecipanti(Torneo torneo, Giocatore giocatore, Organizzatore organizzatore) {
        if (torneo == null || torneo.getNome() == null || giocatore == null || giocatore.getUsername() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return "imput nulli";
        }

        if (torneo.getNome().isEmpty() || giocatore.getUsername().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return "imput vuoti";
        }

        Torneo t = torneoRepository.findByNome(torneo.getNome());

        if(t == null || !organizzatore.equals(t.getOrganizzatore())){
            return "torneo non esistente";
        }

        if(t.getStatoTorneo() == StatoTorneo.ATTESAISCRIZIONI && t.getGiocatoreList().contains(giocatore)){
            t.getGiocatoreList().remove(giocatore);
            torneoRepository.save(t);
            return "giocatore rimosso con successo";
        }
        return "impossibile rimuovere giocatore";

    }

    @Override
    public Giocatore visualizzaProfiloUtente(Torneo torneo, Giocatore giocatore, Organizzatore organizzatore) {
        if (torneo == null || torneo.getNome() == null || giocatore == null || giocatore.getUsername() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return null;
        }

        if (torneo.getNome().isEmpty() || giocatore.getUsername().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return null;
        }

        Torneo t = torneoRepository.findByNome(torneo.getNome());

        if(t == null || !organizzatore.equals(t.getOrganizzatore())){
            return null;
        }

        if(t.getGiocatoreList().contains(giocatore)){
            return giocatore;
        }

        return null;
    }

    @Override
    public Torneo findByName(String nome) {
        return torneoRepository.findByNome(nome);
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
