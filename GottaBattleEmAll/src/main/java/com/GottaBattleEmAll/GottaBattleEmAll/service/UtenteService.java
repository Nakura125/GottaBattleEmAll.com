package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;

public interface UtenteService {

    public String login(Utente utente, String ruolo);

    public String modificaProfilo(Utente utente);

    public void logout(Utente idUtente);

    public Utente findByUsername(String username,String ruolo);

}
