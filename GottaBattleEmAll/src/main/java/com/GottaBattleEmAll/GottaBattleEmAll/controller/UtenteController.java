package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtenteController {
    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/Giocatore/homePlayer";
    }

    @GetMapping("/Giocatore/homePlayer")
    public String homePlayer(Model model) {
        return "homePlayer";
    }

    @GetMapping("/Organizzatore/homeOrganizzatore")
    public String homeOrganizzatore( Model model) {
        return "homeOrganizzatore";
    }

    @GetMapping("/Organizzatore/impostazioniOrganizzatore")
    public String impostazioniBro(Model model) {
        return "impostazioniOrganizzatore";
    }

    @GetMapping("/Giocatore/impostazioniGiocatore")
    public String impostazioniPippo(Model model) {
        return "impostazioniGiocatore";
    }

    @GetMapping("/Giocatore/squadra")
    public String squadra(Model model) {
        return "squadra";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "login";
    }

}
