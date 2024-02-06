package com.GottaBattleEmAll.GottaBattleEmAll.config;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Component
public class DatabasePopulator {

    private final ModeratoreRepository moderatoreRepository;

    private final GiocatoreRepository giocatoreRepository;

    private final OrganizzatoreRepository organizzatoreRepository;

    private  final RichiestaRepository richiestaRepository;

    private final TorneoRepository torneoRepository;



    @Autowired
    public DatabasePopulator(ModeratoreRepository moderatoreRepository, GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository, RichiestaRepository richiestaRepository,TorneoRepository torneoRepository) {
        this.moderatoreRepository = moderatoreRepository;
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;
        this.richiestaRepository = richiestaRepository;
        this.torneoRepository = torneoRepository;

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

        Organizzatore organizzatore1=new Organizzatore();

        organizzatore1.setUsername("organizzatore1");
        organizzatore1.setPassword("organizzatore1");
        organizzatore1.setNome("organizzatore1");
        organizzatore1.setCognome("organizzatore1");
        organizzatore1.setEmail("organizzatore1@email.com");
        organizzatore1.setStato(Stato.INVERIFICA);

        organizzatoreRepository.save(organizzatore1);

        Organizzatore organizzatore2=new Organizzatore();

        organizzatore2.setUsername("organizzatore2");
        organizzatore2.setPassword("organizzatore2");
        organizzatore2.setNome("organizzatore2");
        organizzatore2.setCognome("organizzatore2");
        organizzatore2.setEmail("organizzatore2@email.com");
        organizzatore2.setStato(Stato.INVERIFICA);

        organizzatoreRepository.save(organizzatore2);

        Organizzatore organizzatore3=new Organizzatore();

        organizzatore3.setUsername("organizzatore3");
        organizzatore3.setPassword("organizzatore3");
        organizzatore3.setNome("organizzatore3");
        organizzatore3.setCognome("organizzatore3");
        organizzatore3.setEmail("organizzatore3@email.com");
        organizzatore3.setStato(Stato.INVERIFICA);

        organizzatoreRepository.save(organizzatore3);


        Richiesta richiesta1=new Richiesta();
        richiesta1.setOrganizzatore(organizzatore1);
        richiestaRepository.save(richiesta1);

        Richiesta richiesta2=new Richiesta();
        richiesta2.setOrganizzatore(organizzatore2);
        richiestaRepository.save(richiesta2);

        Richiesta richiesta3=new Richiesta();
        richiesta3.setOrganizzatore(organizzatore3);
        richiestaRepository.save(richiesta3);


        Torneo torneo1=new Torneo();
        torneo1.setNome("Torneo1");
        torneo1.setCapienza(10);
        torneo1.setData(LocalDate.now());
        torneo1.setRegole("Regole");
        torneo1.setPremi("Premi");
        torneo1.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);
        torneo1.setOrganizzazione("2 vs 2");
        torneo1.setOrganizzatore(organizzatore);

        torneo1.addGiocatore(giocatore);

        torneoRepository.save(torneo1);

        Torneo torneo2=new Torneo();
        torneo2.setNome("Torneo2");
        torneo2.setCapienza(10);
        torneo2.setData(LocalDate.now());
        torneo2.setRegole("Regole");
        torneo2.setPremi("Premi");
        torneo2.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);
        torneo2.setOrganizzazione("2 vs 2");
        torneo2.setOrganizzatore(organizzatore);


        torneoRepository.save(torneo2);
    }


    @PreDestroy
    public  void destroy(){
        moderatoreRepository.deleteAll();
        giocatoreRepository.deleteAll();
        richiestaRepository.deleteAll();
        torneoRepository.deleteAll();
        organizzatoreRepository.deleteAll();
    }
}
