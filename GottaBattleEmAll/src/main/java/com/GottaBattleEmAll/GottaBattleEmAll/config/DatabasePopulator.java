package com.GottaBattleEmAll.GottaBattleEmAll.config;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
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



    @Autowired
    public DatabasePopulator(ModeratoreRepository moderatoreRepository, GiocatoreRepository giocatoreRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.moderatoreRepository = moderatoreRepository;
        this.giocatoreRepository = giocatoreRepository;
        this.organizzatoreRepository = organizzatoreRepository;

    }

    @PostConstruct
    public void populate() {
        moderatoreRepository.save(new Moderatore("admin", "admin"));
    }


    @PreDestroy
    public  void destroy(){
        moderatoreRepository.deleteAll();
        giocatoreRepository.deleteAll();
        organizzatoreRepository.deleteAll();
    }
}
