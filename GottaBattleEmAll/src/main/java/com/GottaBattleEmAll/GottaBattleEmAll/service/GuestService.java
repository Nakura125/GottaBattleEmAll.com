package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;

public interface GuestService {

    public String registrazioneGiocatore(Giocatore giocatore, String confermaPassword);


    public String registrazioneOrganizzatore(Organizzatore organizzatore, String confermaPassword);
}
