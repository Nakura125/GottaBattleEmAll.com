package com.GottaBattleEmAll.GottaBattleEmAll.config;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class DatabasePopulator {

    private final ModeratoreRepository moderatoreRepository;

    @Autowired
    public DatabasePopulator(ModeratoreRepository moderatoreRepository) {
        this.moderatoreRepository = moderatoreRepository;
    }

    @PostConstruct
    public void populate() {
        moderatoreRepository.save(new Moderatore("admin", "admin"));
    }


    @PreDestroy
    public  void destroy(){
        moderatoreRepository.deleteAll();
    }
}
