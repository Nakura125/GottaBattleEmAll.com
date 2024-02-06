package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.StatoTorneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorneoServiceImpl implements TorneoService {

    private final TorneoRepository torneoRepository;
    private final OrganizzatoreRepository organizzatoreRepository;

    private final GiocatoreRepository giocatoreRepository;

    public TorneoServiceImpl(TorneoRepository torneoRepository, OrganizzatoreRepository organizzatoreRepository, GiocatoreRepository giocatoreRepository) {
        this.torneoRepository = torneoRepository;
        this.organizzatoreRepository = organizzatoreRepository;
        this.giocatoreRepository = giocatoreRepository;
    }

    @Override
    public String creaTorneo(Torneo torneo, Organizzatore organizzatore) {
        if (torneo == null || torneo.getNome() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return "input nulli";
        }

        if (torneo.getNome().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return "input vuoti";
        }

        if (torneoRepository.findByNome(torneo.getNome()) != null) {
            return "nome torneo già esistente";
        }

        Organizzatore o = organizzatoreRepository.findByUsername(organizzatore.getUsername());
        int capienza = torneo.getCapienza();
        if (capienza != 2 && (capienza < 4 || capienza > 16 || capienza % 4 != 0)) {
            return "capienza non valida.";
        }

        torneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);
        torneo.setOrganizzatore(o);
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


        if (t == null || !organizzatore.equals(t.getOrganizzatore())) {
            return false;
        }
        if (t.getStatoTorneo() != StatoTorneo.ATTESAISCRIZIONI) {
            return false;
        }
        if (t.getGiocatoreList() == null || t.getGiocatoreList().size() != t.getCapienza()) {
            return false;
        }

        t.setStatoTorneo(StatoTorneo.INCORSO);
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

        if (t == null || !organizzatore.equals(t.getOrganizzatore())) {
            return false;
        }

        if (t.getStatoTorneo() != StatoTorneo.INCORSO) {
            return false;
        }

        t.setStatoTorneo(StatoTorneo.TORNEOCOMPLETATO);
        torneoRepository.save(t);
        return true;
    }

    @Override
    public String toglierePartecipanti(Torneo torneo, Giocatore giocatore, Organizzatore organizzatore) {
        if (torneo == null || torneo.getNome() == null || giocatore == null || giocatore.getUsername() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return "input nulli";
        }

        if (torneo.getNome().isEmpty() || giocatore.getUsername().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return "input vuoti";
        }

        Torneo t = torneoRepository.findByNome(torneo.getNome());
        Giocatore g = giocatoreRepository.findByUsername(giocatore.getUsername());

        if (t == null || !organizzatore.equals(t.getOrganizzatore())) {
            return "torneo non esistente";
        }

        if (t.getStatoTorneo() == StatoTorneo.ATTESAISCRIZIONI && t.getGiocatoreList().contains(giocatore)) {
            t.getGiocatoreList().remove(g);
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

        if (t == null || !organizzatore.equals(t.getOrganizzatore())) {
            return null;
        }

        Giocatore g = giocatoreRepository.findByUsername(giocatore.getUsername());
        if (g != null && t.getGiocatoreList().contains(g)) {
            return g;
        }
        return null;
    }

    @Override
    public Torneo findByName(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null;
    }
        return torneoRepository.findByNome(nome);
    }

    @Override
    public boolean seguireOrganizzatore(Giocatore giocatore, Organizzatore organizzatore) {
        if (giocatore == null || giocatore.getUsername() == null || organizzatore == null || organizzatore.getUsername() == null) {
            return false;
        }
        if (giocatore.getUsername().isEmpty() || organizzatore.getUsername().isEmpty()) {
            return false;
        }

        Organizzatore o = organizzatoreRepository.findByUsername(organizzatore.getUsername());
        if (o == null) {
            return false;
        }
        Giocatore g = giocatoreRepository.findByUsername(giocatore.getUsername());
        if (!g.getOrganizzatori().contains(o)) {
            g.getOrganizzatori().add(o);
            return true;
        }
        return false;
    }

    @Override
    public List<Torneo> getTorneoIscritto(Giocatore giocatore) {
        if (giocatore == null || giocatore.getUsername() == null) {
            return null;
        }
        if (giocatore.getUsername().isEmpty()) {
            return null;
        }

        Giocatore g = giocatoreRepository.findByUsername(giocatore.getUsername());

        return null;
    }


    @Override
    public String iscrizioneTorneo(Giocatore giocatore, Torneo torneo) {
        if (giocatore == null || giocatore.getUsername() == null || torneo == null || torneo.getNome() == null) {
            return "input nulli";
        }
        if (giocatore.getUsername().isEmpty() || torneo.getNome().isEmpty()) {
            return "input vuoti";
        }

        Torneo t = torneoRepository.findByNome(torneo.getNome());
        Giocatore g = giocatoreRepository.findByUsername(giocatore.getUsername());


        if (t == null || g == null) {
            return "torneo o giocatore non esistente";
        }

        if (t.getCapienza() == t.getGiocatoreList().size()) {
            return "iscrizioni piene";
        }

        if (g.getTornei().contains(t)) {
            return "iscrizione torneo rifiutata";
        }

        if (t.getStatoTorneo() == StatoTorneo.ATTESAISCRIZIONI && t.getGiocatoreList().size() < t.getCapienza() &&
                !t.getGiocatoreList().contains(g)) {
            t.getGiocatoreList().add(g);
            g.getTornei().add(t);
            if (t.getGiocatoreList().size() == t.getCapienza()) {
                t.setStatoTorneo(StatoTorneo.ISCRIZIONICOMPLETATE);
            }
            torneoRepository.save(t);
            giocatoreRepository.save(g);
            return "iscrizione effettuata con successo";
        }
        return null;
    }

    @Override
    public List<Torneo> cercareTorneo(String nome) {
        if (nome != null && !nome.isEmpty()) {
            return torneoRepository.findByNomeContainingIgnoreCase(nome);
        }
        return null;
    }
}
