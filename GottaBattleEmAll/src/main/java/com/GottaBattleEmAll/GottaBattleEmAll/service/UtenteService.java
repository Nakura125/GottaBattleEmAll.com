package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;

import java.util.List;
public interface UtenteService {

    public String login(Utente utente, String ruolo);

    public String modificaProfilo(Utente utente);

    public void logout(Utente idUtente);

    public <Ruolo extends Utente>  Ruolo findByUsername(String username);


    public List<Organizzatore> findActiveOrganizzatoriPaged(int number, int size);

    public List<Giocatore> findActiveGiocatoriPaged(int number, int size);

}
