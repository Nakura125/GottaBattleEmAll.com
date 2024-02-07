package com.GottaBattleEmAll.GottaBattleEmAll.repository;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GiocatoreRepository extends JpaRepository<Giocatore, UUID>{

    public Giocatore findByUsername(String username);

    @Query("SELECT g.tornei FROM Giocatore g WHERE g.username = :username")
    public Page<Torneo> getTorneiPartecipanti(@Param("username") String username, Pageable pageable);

    public Page<Giocatore> findAll(Pageable pageable);

}
