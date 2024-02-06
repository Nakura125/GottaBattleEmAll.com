package com.GottaBattleEmAll.GottaBattleEmAll.repository;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, UUID> {

    public Torneo findByNome(String nome);
//    Page<Torneo> findByGiocatore(Giocatore g, PageRequest of);

    public List<Torneo> findByOrganizzatore(Organizzatore organizzatore);

    @Query("SELECT p FROM Torneo p WHERE p.nome LIKE %:keyword%")
    public List<Torneo> findByNomeLike(@Param("keyword") String nome);

}
