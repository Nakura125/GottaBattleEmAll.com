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

    @GetMapping("/")
    public String index(Model model) {
        return "homeGuest";
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

        String risultatoRegistrazione= "";
        if ("giocatore".equals(ruolo)) {
            // Chiamare il servizio di registrazione del giocatore come utente
            risultatoRegistrazione = guestService.registrazioneGiocatore(giocatore, confermaPassword);
            model.addAttribute("risultatoRegistrazione", risultatoRegistrazione);
            //System.out.println("Risultato registrazione: " + risultatoRegistrazione);
        } else if ("organizzatore".equals(ruolo)) {
            // Chiamare il servizio di registrazione dell'organizzatore
            risultatoRegistrazione = guestService.registrazioneOrganizzatore(organizzatore, confermaPassword);
            model.addAttribute("risultatoRegistrazione", risultatoRegistrazione);
            //System.out.println("Risultato registrazione: " + risultatoRegistrazione);
        }


        if (risultatoRegistrazione.equals("registrazione effettuata con successo") || risultatoRegistrazione.equals("richiesta di registrazione inviata con successo")) {

            model.addAttribute("message", risultatoRegistrazione);
            return "redirect:/login";
        }else {
            model.addAttribute("error", risultatoRegistrazione);
            return "registrazione";
        }

    }
}


