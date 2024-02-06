package com.GottaBattleEmAll.GottaBattleEmAll.service;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.PartitaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class PartitaServiceImpl implements PartitaService {

    private final PartitaRepository partitaRepository;

    public PartitaServiceImpl(PartitaRepository partitaRepository, TorneoRepository torneoRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.partitaRepository = partitaRepository;
    }

    @Override
    public boolean aggiungereRisultato(String risultato, Partita partita, Torneo torneo, Organizzatore organizzatore) {

        if (partita == null || torneo == null || organizzatore == null || risultato == null) {
            return false;
        }

        if (!Objects.equals(torneo.getOrganizzatore().getUsername(), organizzatore.getUsername())) {
            return false;
        }

        if (!torneo.getPartite().contains(partita)) {
            return false;
        }

        partita.setIdVincitore(Long.valueOf(risultato));
        partitaRepository.save(partita);
        return true;
    }
}