package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TorneoController {

    @GetMapping("/Giocatore/listaTornei")
    public String listaTornei(Model model) {
        return "listaTornei";
    }

    @GetMapping("/Organizzatore/torneoInAttesa")
    public String torneoInAttesa(Model model) {
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
    public String creaTorneo(Model model) {
        return "creaTorneo";
    }

    @GetMapping("Giocatore/ricercaTornei")
    public String ricercaTornei(Model model) {
        return "ricercaTornei";
    }






}
