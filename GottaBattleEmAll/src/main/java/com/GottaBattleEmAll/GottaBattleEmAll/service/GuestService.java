package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;

public interface GuestService {

    public boolean registrazioneGiocatore(Giocatore giocatore);


    public boolean registrazioneOrganizzatore(Organizzatore organizzatore);
}
