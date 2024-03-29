package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Richiesta;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;

import java.util.List;

public interface ModeratoreService {

    public String login(Moderatore moderatore);

    public Moderatore findByUsername(String username);

    public boolean bannare(Moderatore moderatore, Utente utente, String ruolo);
    public boolean sbannare(Moderatore moderatore, Utente utente, String ruolo);

    public boolean accettare(Moderatore moderatore, Organizzatore organizzatore);

    public boolean rifiutare(Moderatore moderatore, Organizzatore organizzatore);

    public List<Richiesta> notifiche();


}
