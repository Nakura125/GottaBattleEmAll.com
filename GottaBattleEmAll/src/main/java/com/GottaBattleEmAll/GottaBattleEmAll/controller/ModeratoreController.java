package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeratoreController {

    @GetMapping("/Moderatore/")
    public String index(Model model) {
        return "loginAdmin";
    }
    @GetMapping("/Moderatore/loginAdmin")
    public String loginAdmin(Model model) {
        return "loginAdmin";
    }

    @GetMapping("/Moderatore/homeAdmin")
    public String homeAdmin(Model model) {
        return "admin";
    }

    @GetMapping("/Moderatore/centroNotifiche")
    public String centroNotifiche(Model model) {
        return "centroNotifiche";
    }

    @GetMapping("/Moderatore/impostazioni")
    public String impostazioni(Model model) {
        return "impostazioni";
    }

    @GetMapping("/Moderatore/listaUtenti")
    public String listaUtenti(Model model) {
        return "listaUtenti";
    }

}
