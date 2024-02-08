package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Partita;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.PartitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class PartitaController {

    PartitaService partitaService;
    TorneoRepository torneoRepository;

    OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public PartitaController(PartitaService partitaService, TorneoRepository torneoRepository,OrganizzatoreRepository organizzatoreRepository) {
        this.partitaService = partitaService;
        this.torneoRepository = torneoRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }


    @GetMapping("/Organizzatore/aggiungereRisultato")
    public String aggiungereRisultato
            (@RequestParam(name = "risultato") String res,
             @RequestParam(name="partita_id") String partita_id,
             @RequestParam(name = "nomeTorneo") String nomeTorneo,
             Model model, HttpSession session) {

        Partita partita = partitaService.getPartitaById(UUID.fromString(partita_id));

        Torneo torneo = torneoRepository.findByNome(nomeTorneo);

        Integer risultato= Integer.parseInt(res);

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");

        organizzatore = organizzatoreRepository.findByUsername(organizzatore.getUsername());

        boolean ris=partitaService.aggiungereRisultato(risultato, partita, torneo, organizzatore);

        if(ris){
            return "redirect:/Organizzatore/torneoInCorso?name="+ torneo.getNome();
        }
        return "redirect:/Organizzatore/torneoInCorso?name="+ torneo.getNome()+"&error=1";

    }
}
