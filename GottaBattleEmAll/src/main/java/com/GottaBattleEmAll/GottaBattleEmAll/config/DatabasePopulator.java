package com.GottaBattleEmAll.GottaBattleEmAll.config;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.RichiestaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class DatabasePopulator {

    private final ModeratoreRepository moderatoreRepository;

    private final GiocatoreRepository giocatoreRepository;

    private final OrganizzatoreRepository organizzatoreRepository;

    private  final RichiestaRepository richiestaRepository;



    @Autowired
    public DatabasePopulator(ModeratoreRepository moderatoreRepository, GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository, RichiestaRepository richiestaRepository) {
        this.moderatoreRepository = moderatoreRepository;
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;
        this.richiestaRepository = richiestaRepository;

    }

    @PostConstruct
    public void populate() {
        // Popolamento del database con un moderatore di default
        moderatoreRepository.save(new Moderatore("admin", "admin"));

        //Popolamento del database con un giocatore di default
        Giocatore giocatore=new Giocatore();
        giocatore.setUsername("giocatore");
        giocatore.setPassword("giocatore");
        giocatore.setNome("giocatore");
        giocatore.setCognome("giocatore");
        giocatore.setEmail("giocatore@email.com");
        giocatore.setStato(Stato.ATTIVO);


        giocatoreRepository.save(giocatore);

        //Popolamento del database con un organizzatore di default
        Organizzatore organizzatore=new Organizzatore();

        organizzatore.setUsername("organizzatore");
        organizzatore.setPassword("organizzatore");
        organizzatore.setNome("organizzatore");
        organizzatore.setCognome("organizzatore");
        organizzatore.setEmail("organizzatore@email.com");
        organizzatore.setStato(Stato.ATTIVO);

        organizzatoreRepository.save(organizzatore);

    }


    @PreDestroy
    public  void destroy(){
        moderatoreRepository.deleteAll();
        giocatoreRepository.deleteAll();
        richiestaRepository.deleteAll();
        organizzatoreRepository.deleteAll();
    }
}
