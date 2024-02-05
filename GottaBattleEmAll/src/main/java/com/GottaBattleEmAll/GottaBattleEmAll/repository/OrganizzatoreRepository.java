package com.GottaBattleEmAll.GottaBattleEmAll.repository;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, UUID> {

    public Organizzatore findByUsername(String username);

    //find paged all organizzatori with Stato.ATTIVO
    public Page<Organizzatore> findAll(Pageable pageable);
}
