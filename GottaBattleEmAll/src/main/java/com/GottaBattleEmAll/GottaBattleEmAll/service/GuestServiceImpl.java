package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GuestServiceImpl implements GuestService{


    public final GiocatoreRepository giocatoreRepository;
    public final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public GuestServiceImpl(GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository){
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    @Override
    public String registrazioneGiocatore(Giocatore giocatore, String confermaPassword) {
        if(giocatore == null || giocatore.getUsername()==null || giocatore.getPassword()==null || giocatore.getNome()==null || giocatore.getCognome()==null || giocatore.getEmail()==null || confermaPassword==null)
            return "parametri null";

        if(giocatore.getUsername().isEmpty() || giocatore.getPassword().isEmpty() || giocatore.getNome().isEmpty() || giocatore.getCognome().isEmpty() || giocatore.getEmail().isEmpty() || confermaPassword.isEmpty())
            return "parametri empty";

        if(!giocatore.getPassword().equals(confermaPassword)) return "password non corrispondono";

        if (!isValidMail(giocatore.getEmail())) return "email non valida";

        if(giocatoreRepository.findByUsername(giocatore.getUsername()) !=null) return "username già esistente";

        giocatore.setStato(Stato.ATTIVO);
        giocatoreRepository.save(giocatore);
        return "registrazione avvenuta con successo";
    }

    private boolean isValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    @Override
    public String registrazioneOrganizzatore(Organizzatore organizzatore, String confermaPassword) {
        if(organizzatore == null || organizzatore.getUsername()==null || organizzatore.getPassword()==null || organizzatore.getNome()==null || organizzatore.getCognome()==null || organizzatore.getEmail()==null || confermaPassword==null){
            return "parametri null";
        }
        if(organizzatore.getUsername().isEmpty() || organizzatore.getPassword().isEmpty() || organizzatore.getNome().isEmpty() || organizzatore.getCognome().isEmpty() || organizzatore.getEmail().isEmpty() || confermaPassword.isEmpty()){
            return "parametri empty";
        }
        if(!organizzatore.getPassword().equals(confermaPassword)){
            return "password non corrispondono";
        }
        if (!isValidMail(organizzatore.getEmail())){
            return "email non valida";
        }

        if(organizzatoreRepository.findByUsername(organizzatore.getUsername())!=null){
            return "username già esistente";
        }
        organizzatore.setStato(Stato.INVERIFICA);
        organizzatoreRepository.save(organizzatore);
        return "richiesta di registrazione inviata con successo";
    }
}
