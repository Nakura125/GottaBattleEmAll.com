package com.GottaBattleEmAll.GottaBattleEmAll.repository;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, UUID> {

    public Torneo findByNome(String nome);
    Page<Torneo> findByGiocatore(Giocatore g, PageRequest of);
}
