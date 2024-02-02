package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import org.springframework.stereotype.Service;

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
    public Utente findByUsername(String username, String ruolo) {
        return null;
    }
}
