package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeratoreServiceImpl implements ModeratoreService{

    public final ModeratoreRepository moderatoreRepository;

    @Autowired
    public ModeratoreServiceImpl(ModeratoreRepository moderatoreRepository){
        this.moderatoreRepository = moderatoreRepository;
    }

    @Override
    public String login(Moderatore moderatore) {
        if ( moderatore.getUsername()== null || moderatore.getPassword() == null || moderatore.getUsername().isEmpty() || moderatore.getPassword().isEmpty() ){
            return "Username o password non corretti";
        }

        Moderatore mod=moderatoreRepository.findByUsername(moderatore.getUsername());

        if(mod == null){
            return "Username o password non corretti";
        }

        if(mod.getPassword().equals(moderatore.getPassword())){
            return "Login effettuato";
        }
        else{
            return "Username o password non corretti";
        }
    }

    @Override
    public Moderatore findByUsername(String username) {
        return null;
    }

    @Override
    public boolean logout(Moderatore moderatore) {
        return false;
    }

    @Override
    public boolean bannare(Moderatore moderatore, Utente utente, String ruolo) {
        return false;
    }

    @Override
    public boolean sbannare(Moderatore moderatore, Utente utente, String ruolo) {
        return false;
    }

    @Override
    public boolean accettare(Moderatore moderatore, Organizzatore organizzatore) {
        return false;
    }

    @Override
    public boolean rifiutare(Moderatore moderatore, Organizzatore organizzatore) {
        return false;
    }
}
