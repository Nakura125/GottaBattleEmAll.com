package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.PartitaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartitaServiceImpl implements PartitaService {

    private final PartitaRepository partitaRepository;


    @Autowired
    public PartitaServiceImpl(PartitaRepository partitaRepository) {
        this.partitaRepository = partitaRepository;
    }

    @Override
    public boolean aggiungereRisultato(Long risultato, Partita partita, Torneo torneo, Organizzatore organizzatore) {

        if (partita == null || torneo == null || organizzatore == null || risultato == null) {
            return false;
        }

        if (!Objects.equals(torneo.getOrganizzatore().getUsername(), organizzatore.getUsername())) {
            return false;
        }

        if (!torneo.getPartite().contains(partita)) {
            return false;
        }

        partita.setIdVincitore(risultato);
        partitaRepository.save(partita);
        return true;
    }
}