package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteServiceImpl implements UtenteService {

    public final GiocatoreRepository giocatoreRepository;
    public final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public UtenteServiceImpl(GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    @Override
    public String login(Utente utente, String ruolo) {
        if (utente == null || utente.getUsername() == null || utente.getPassword() == null) {
            return "parametri null";
        }
        if (utente.getUsername().isEmpty() || utente.getPassword().isEmpty()) {
            return "parametri empty";
        }
        if (ruolo.equals("giocatore")) {
            Giocatore g = giocatoreRepository.findByUsername(utente.getUsername());
            if (g == null) {
                return "credenziali sbagliate o non presenti";
            }
            if (g.getStato() == Stato.BANNATO) {
                return "account bannato";
            }
            if (g.getStato() == Stato.ATTIVO) {
                if (g.getPassword().equals(utente.getPassword())) {
                    return "login effettuato";
                }
            }
            return "credenziali sbagliate o non presenti";
            }



        if (ruolo.equals("organizzatore")) {
            Organizzatore o = organizzatoreRepository.findByUsername(utente.getUsername());
            if (o == null) {
                return "credenziali sbagliate o non presenti";
            }
            if (o.getStato() == Stato.BANNATO) {
                return "account bannato";
            }
            if (o.getStato() == Stato.ATTIVO) {
                if (o.getPassword().equals(utente.getPassword())) {
                    return "login effettuato";
                }
            }
            if (o.getStato() == Stato.INVERIFICA) {
                return "account ancora non accettato";
            }
        }
        return "credenziali sbagliate o non presenti";
    }


    @Override
    public String modificaProfilo(Utente utente) {
        return null;
    }

    @Override
    public void logout(Utente idUtente) {

    }

    @Override
    public Utente findByUsername(String username, String ruolo) {
        return null;
    }
}
