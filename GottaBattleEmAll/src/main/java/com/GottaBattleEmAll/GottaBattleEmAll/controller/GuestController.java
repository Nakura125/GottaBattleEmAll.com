package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @GetMapping("/registrazione")
    public String registrazione( Model model) {
        return "registrazione";
    }




}
