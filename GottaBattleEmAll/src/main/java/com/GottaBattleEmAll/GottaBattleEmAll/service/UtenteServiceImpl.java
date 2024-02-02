package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService{
    @Override
    public String login(Utente utente, String ruolo) {
        return null;
    }

    @Override
    public String modificaProfilo(Utente utente) {
        return null;
    }

    @Override
    public void logout(Utente idUtente) {

    }

    @Override
    public <Ruolo extends Utente> Ruolo findByUsername(String username) {
        return null;
    }

    @Override
    public List<Giocatore> findActiveGiocatoriPaged(int number, int size) {
        return null;
    }

    @Override
    public List<Organizzatore> findActiveOrganizzatoriPaged(int number, int size) {
        return null;
    }
}
