package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class TorneoController {

    public final TorneoService torneoService;

    @Autowired
    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @GetMapping("/Giocatore/listaTornei")
    public String listaTornei(Model model) {
        return "listaTornei";
    }

    @GetMapping("/Organizzatore/torneoInAttesa")
    public String torneoInAttesa(@RequestParam(name="name") String nome, Model model) {
        return "torneoInAttesa";
    }

    @GetMapping("/Organizzatore/torneoInCorso")
    public String torneoInCorso(Model model) {
        return "torneoInCorso";
    }

    @GetMapping("/Organizzatore/torneoConcluso")
    public String torneoConcluso(Model model) {
        return "torneoFinitoBro";
    }


    @GetMapping("/Organizzatore/creaTorneo")
    public String creaTorneo(Model model, HttpSession session) {

        Torneo torneo = new Torneo();
        model.addAttribute("torneo", torneo);

        Organizzatore organizzatore =(Organizzatore) session.getAttribute("organizzatore");
        model.addAttribute("organizzatore", organizzatore);
        return "creaTorneo";
    }

    @PostMapping("/Organizzatore/creaTorneo")
    public String creaTorneoPost(@ModelAttribute Torneo torneo, HttpSession session, Model model) {
        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");

        String result=torneoService.creaTorneo(torneo, organizzatore);
        if (result.equals( "torneo creato con successo")) {
            return "redirect:/Organizzatore/torneoInAttesa";
        }
        model.addAttribute("error", result);
        model.addAttribute("organizzatore", organizzatore);
        return "creaTorneo";
    }

    @GetMapping("Giocatore/ricercaTornei")
    public String ricercaTornei(Model model) {
        return "ricercaTornei";
    }






}
