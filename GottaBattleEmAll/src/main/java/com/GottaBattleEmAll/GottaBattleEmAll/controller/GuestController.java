package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.service.GuestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

@Controller
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService){
        this.guestService = guestService;
    }


    @GetMapping("/registrazione")
    public String registrazione(Model model) {
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registrazione(@ModelAttribute Giocatore giocatore,
                                @ModelAttribute Organizzatore organizzatore,
                                @RequestParam("confermaPassword") String confermaPassword,
                                @RequestParam("ruolo") String ruolo,
                                Model model) {

/*        System.out.println("Ruolo: " + ruolo);

        System.out.println("Giocatore: " +
                "Username: " + giocatore.getUsername() +
                ", Nome: " + giocatore.getNome() +
                ", Cognome: " + giocatore.getCognome() +
                ", Email: " + giocatore.getEmail());

        System.out.println("Organizzatore: " +
                "Username: " + organizzatore.getUsername() +
                ", Nome: " + organizzatore.getNome() +
                ", Cognome: " + organizzatore.getCognome() +
                ", Email: " + organizzatore.getEmail());

        System.out.println("Conferma password: " + confermaPassword);*/


        if ("giocatore".equals(ruolo)) {
            // Chiamare il servizio di registrazione del giocatore come utente
            String risultatoRegistrazione = guestService.registrazioneGiocatore(giocatore, confermaPassword);
            model.addAttribute("risultatoRegistrazione", risultatoRegistrazione);
            System.out.println("Risultato registrazione: " + risultatoRegistrazione);
        } else if ("organizzatore".equals(ruolo)) {
            // Chiamare il servizio di registrazione dell'organizzatore
            String risultatoRegistrazione = guestService.registrazioneOrganizzatore(organizzatore, confermaPassword);
            model.addAttribute("risultatoRegistrazione", risultatoRegistrazione);
            System.out.println("Risultato registrazione: " + risultatoRegistrazione);
        }


        return "registrazione";
    }
}


