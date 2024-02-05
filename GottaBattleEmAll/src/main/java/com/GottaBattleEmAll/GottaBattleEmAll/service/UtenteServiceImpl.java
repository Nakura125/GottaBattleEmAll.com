package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtenteServiceImpl implements UtenteService{
    public final GiocatoreRepository giocatoreRepository;
    public final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public UtenteServiceImpl(GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    @Override
    public String login(Utente utente, String ruolo) {
        if (utente == null || utente.getUsername() == null || utente.getPassword() == null) {
            return "parametri null";
        }
        if (utente.getUsername().isEmpty() || utente.getPassword().isEmpty()) {
            return "parametri empty";
        }
        if (ruolo.equals("giocatore")) {
            Giocatore g = giocatoreRepository.findByUsername(utente.getUsername());
            if (g == null) {
                return "credenziali sbagliate o non presenti";
            }
            if (g.getStato() == Stato.BANNATO) {
                return "account bannato";
            }
            if (g.getStato() == Stato.ATTIVO) {
                if (g.getPassword().equals(utente.getPassword())) {
                    return "login effettuato";
                }
            }
            return "credenziali sbagliate o non presenti";
        }



        if (ruolo.equals("organizzatore")) {
            Organizzatore o = organizzatoreRepository.findByUsername(utente.getUsername());
            if (o == null) {
                return "credenziali sbagliate o non presenti";
            }
            if (o.getStato() == Stato.BANNATO) {
                return "account bannato";
            }
            if (o.getStato() == Stato.ATTIVO) {
                if (o.getPassword().equals(utente.getPassword())) {
                    return "login effettuato";
                }
            }
            if (o.getStato() == Stato.INVERIFICA) {
                return "account ancora non accettato";
            }
        }
        return "credenziali sbagliate o non presenti";
    }

    @Override
    public String modificaProfilo(Utente utente, String confermaPassword, String ruolo, String username) {
        if (utente == null || utente.getUsername() == null || utente.getPassword() == null || utente.getNome() == null || utente.getCognome() == null || utente.getEmail() == null || confermaPassword == null) {
            return "parametri null";
        }

        if(utente.getUsername().isEmpty() || utente.getPassword().isEmpty() || utente.getNome().isEmpty() || utente.getCognome().isEmpty() || utente.getEmail().isEmpty() || confermaPassword.isEmpty()){
            return "parametri empty";
        }

        if (!utente.getPassword().equals(confermaPassword)) {
            return "password non corrispondono";
        }

        if (!isValidMail(utente.getEmail())) {
            return "email non valida";
        }

        if (ruolo.equals("giocatore")) {
            Giocatore g = giocatoreRepository.findByUsername(username);
            if (g == null) {
                return "utente non trovato";
            }
            if (giocatoreRepository.findByUsername(utente.getUsername()) != null && !utente.getUsername().equals(username)){
                return "username già esistente";
            }

            g.setUsername(utente.getUsername());
            g.setNome(utente.getNome());
            g.setCognome(utente.getCognome());
            g.setEmail(utente.getEmail());
            g.setPassword(utente.getPassword());
            giocatoreRepository.save(g);
            return "modifica effettuata";
        }

        if (ruolo.equals("organizzatore")) {
            Organizzatore o = organizzatoreRepository.findByUsername(username);
            if (o == null) {
                return "utente non trovato";
            }

            if (organizzatoreRepository.findByUsername(utente.getUsername()) != null && !utente.getUsername().equals(username)){
                return "username già esistente";
            }
            o.setUsername(utente.getUsername());
            o.setNome(utente.getNome());
            o.setCognome(utente.getCognome());
            o.setEmail(utente.getEmail());
            o.setPassword(utente.getPassword());
            organizzatoreRepository.save(o);
            return "modifica effettuata";
        }
        return null;
    }


    private boolean isValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    @Override
    public void logout(Utente idUtente) {

    }

    @Override
    public Giocatore findGiocatoreByUsername(String username) {
        return giocatoreRepository.findByUsername(username);
    }

    @Override
    public Organizzatore findOrganizzatoreByUsername(String username) {
        return organizzatoreRepository.findByUsername(username);
    }


    @Override
    public List<Organizzatore> findOrganizzatoriPaged(int number, int size) {

        //find all active organizzatori and return them with pagination
        return organizzatoreRepository.findAll(PageRequest.of(number, size)).getContent();
    }

    @Override
    public List<Giocatore> findGiocatoriPaged(int number, int size) {
        //find all active giocatori and return them with pagination
        return giocatoreRepository.findAll(PageRequest.of(number, size)).getContent();
    }


}
