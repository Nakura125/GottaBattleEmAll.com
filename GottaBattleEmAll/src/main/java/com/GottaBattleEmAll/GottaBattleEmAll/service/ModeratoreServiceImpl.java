package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.RichiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratoreServiceImpl implements ModeratoreService{

    public final ModeratoreRepository moderatoreRepository;
    public final GiocatoreRepository giocatoreRepository;
    public final OrganizzatoreRepository organizzatoreRepository;

    public final RichiestaRepository richiestaRepository;

    @Autowired
    public ModeratoreServiceImpl(ModeratoreRepository moderatoreRepository, GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository, RichiestaRepository richiestaRepository){
        this.moderatoreRepository = moderatoreRepository;
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;
        this.richiestaRepository = richiestaRepository;

    }

    @Override
    public String login(Moderatore moderatore) {
        if (moderatore.getUsername()== null || moderatore.getPassword() == null || moderatore.getUsername().isEmpty() || moderatore.getPassword().isEmpty() ){
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
        if(username==null || username.isEmpty()) {
            return null;
        }
        return moderatoreRepository.findByUsername(username);
    }

    @Override
    public boolean logout(Moderatore moderatore) {
        return false;
    }

    @Override
    public boolean bannare(Moderatore moderatore, Utente utente, String ruolo) {


        if(moderatore.getUsername()==null || moderatore.getUsername().isEmpty() || utente.getUsername()==null
                || utente.getUsername().isEmpty() || ruolo==null || ruolo.isEmpty()) {
            return false;
        }


        if (moderatoreRepository.findByUsername(moderatore.getUsername()) == null)
            return false;


        if (ruolo.equals("giocatore")) {
            Giocatore g = giocatoreRepository.findByUsername(utente.getUsername());
            if (g != null && g.getStato() == Stato.ATTIVO) {
                g.setStato(Stato.BANNATO);
                giocatoreRepository.save(g);
                return true;
            }
        }

        if (ruolo.equals("organizzatore")) {
            Organizzatore o = organizzatoreRepository.findByUsername(utente.getUsername());
            if (o != null && o.getStato() == Stato.ATTIVO) {
                o.setStato(Stato.BANNATO);
                organizzatoreRepository.save(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sbannare(Moderatore moderatore, Utente utente, String ruolo) {

        if(moderatore.getUsername()==null || moderatore.getUsername().isEmpty() || utente.getUsername()==null
                || utente.getUsername().isEmpty() || ruolo==null || ruolo.isEmpty()) {
            return false;
        }


        if (moderatoreRepository.findByUsername(moderatore.getUsername()) == null)
            return false;

        if (ruolo.equals("giocatore")) {
            Giocatore g = giocatoreRepository.findByUsername(utente.getUsername());
            if (g != null && g.getStato() == Stato.BANNATO) {
                g.setStato(Stato.ATTIVO);
                giocatoreRepository.save(g);
                return true;
            }
        }
        if (ruolo.equals("organizzatore")) {
            Organizzatore o = organizzatoreRepository.findByUsername(utente.getUsername());
            if (o != null && o.getStato() == Stato.BANNATO) {
                o.setStato(Stato.ATTIVO);
                organizzatoreRepository.save(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean accettare(Moderatore moderatore, Organizzatore organizzatore) {
        if(moderatore.getUsername()==null || moderatore.getUsername().isEmpty() || organizzatore.getUsername()==null
                || organizzatore.getUsername().isEmpty()) {
            return false;
        }

        if (moderatoreRepository.findByUsername(moderatore.getUsername()) == null)
            return false;

        Organizzatore o = organizzatoreRepository.findByUsername(organizzatore.getUsername());
        if (o != null && o.getStato() == Stato.INVERIFICA) {
            o.setStato(Stato.ATTIVO);
            organizzatoreRepository.save(o);
            return true;
        }
        return false;
    }


    @Override
    public boolean rifiutare(Moderatore moderatore, Organizzatore organizzatore) {
        if(moderatore.getUsername()==null || moderatore.getUsername().isEmpty() || organizzatore.getUsername()==null
                || organizzatore.getUsername().isEmpty()) {
            return false;
        }

        if (moderatoreRepository.findByUsername(moderatore.getUsername()) == null)
            return false;

        Organizzatore o = organizzatoreRepository.findByUsername(organizzatore.getUsername());
        if (o != null && o.getStato() == Stato.INVERIFICA) {
            o.setStato(Stato.RIFIUTATO);
            organizzatoreRepository.save(o);
            return true;
        }
        return false;
    }

    @Override
    public List<Richiesta> notifiche(){
        //prende tutte le richieste
        return richiestaRepository.findAll();

    }
}
