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
    private final TorneoRepository torneoRepository;


    @Autowired
    public PartitaServiceImpl(PartitaRepository partitaRepository, TorneoRepository torneoRepository) {
        this.partitaRepository = partitaRepository;
        this.torneoRepository = torneoRepository;
    }

    @Override
    public boolean aggiungereRisultato(Integer risultato, Partita partita, Torneo torneo, Organizzatore organizzatore) {

        partita=partitaRepository.findById(partita.getId()).orElse(null);

        if (partita == null || torneo == null || organizzatore == null || risultato == null) {
            return false;
        }

        if (!Objects.equals(torneo.getOrganizzatore().getUsername(), organizzatore.getUsername())) {
            return false;
        }

        if (!torneo.getPartite().contains(partita)) {
            return false;
        }

        int idPartita=torneo.getPartite().indexOf(partita);


        partita.setIdVincitore(risultato);


        if(torneo.getPartite().size()!=torneo.getCapienza()){
            if (idPartita % 2 == 0) {
                if (torneo.getPartite().get(idPartita + 1).getIdVincitore() != null) {
                    Partita partita1 = creaPartita(torneo.getPartite().get(idPartita).getGiocatoreList().get(risultato), torneo.getPartite().get(idPartita + 1).getGiocatoreList().get(torneo.getPartite().get(idPartita + 1).getIdVincitore()),torneo);
                    partitaRepository.save(partita1);
                    torneo.getPartite().add(partita1);
                }
            } else {
                if (torneo.getPartite().get(idPartita - 1).getIdVincitore() != null) {
                    Partita partita1 =
                            creaPartita(torneo.getPartite().get(idPartita).getGiocatoreList().get(risultato), torneo.getPartite().get(idPartita - 1).getGiocatoreList().get(torneo.getPartite().get(idPartita - 1).getIdVincitore()),torneo);
                    partitaRepository.save(partita1);
                    torneo.getPartite().add(partita1);
                }
            }
        }
        partitaRepository.save(partita);
        return true;
    }

    public Partita creaPartita(Giocatore giocatore1, Giocatore giocatore2,Torneo torneo) {
        Partita partita = new Partita();
        partita.getGiocatoreList().add(giocatore1);
        partita.getGiocatoreList().add(giocatore2);
        partitaRepository.save(partita);

        torneo.getPartite().add(partita);

        torneoRepository.save(torneo);
        return partita;
    }

    @Override
    public Partita getPartitaById(UUID id) {
        return partitaRepository.findById(id).orElse(null);
    }

}