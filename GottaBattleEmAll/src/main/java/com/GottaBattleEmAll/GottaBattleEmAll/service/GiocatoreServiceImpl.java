package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Pokemon;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import org.springframework.stereotype.Service;

@Service
public class GiocatoreServiceImpl implements GiocatoreService {

    public final GiocatoreRepository giocatoreRepository;

    public GiocatoreServiceImpl(GiocatoreRepository giocatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
    }

    public String sostituireMembroTeam(Pokemon pokemon, int posizione, Giocatore giocatore ){

        if (giocatore == null || pokemon == null || posizione < 0 || posizione > 5) {
            return "parametri null";
        }

        giocatoreRepository.findByUsername(giocatore.getUsername()).getPokemons().set(posizione, pokemon);
        giocatoreRepository.save(giocatore);


        return "Pokemon sostituito con successo";
    }
}
