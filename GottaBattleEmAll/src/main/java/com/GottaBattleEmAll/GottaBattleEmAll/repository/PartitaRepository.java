package com.GottaBattleEmAll.GottaBattleEmAll.repository;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository

public interface PartitaRepository extends JpaRepository<Partita, UUID> {

}
