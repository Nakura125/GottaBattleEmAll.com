package com.GottaBattleEmAll.GottaBattleEmAll.repository;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, UUID> {

    public Torneo findByNome(String nome);
}
