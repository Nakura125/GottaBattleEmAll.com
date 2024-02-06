package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class UtenteController {

    public final UtenteService utenteService;
    public final TorneoRepository torneoRepository;

    @Autowired
    public UtenteController(UtenteService utenteService, TorneoRepository torneoRepository) {
        this.utenteService = utenteService;
        this.torneoRepository = torneoRepository;
    }


    @GetMapping("/Giocatore/")
    public String homeGiocatore(Model model) {
        return "redirect:/Giocatore/homePlayer";
    }

    @GetMapping("/Giocatore/homePlayer")
    public String homePlayer(Model model,HttpSession session) {

        Giocatore giocatore = (Giocatore) session.getAttribute("giocatore");

        model.addAttribute("giocatore", giocatore);
        return "homePlayer";
    }


    @GetMapping("/Organizzatore/")
    public String indexOrganizzatore( Model model) {

        return "redirect:/Organizzatore/homeOrganizzatore";
    }

    @GetMapping("/Organizzatore/homeOrganizzatore")
    public String homeOrganizzatore( Model model,HttpSession session){

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");

        List<Torneo> tornei = torneoRepository.findByOrganizzatore(organizzatore);

        model.addAttribute("tornei", tornei);
        model.addAttribute("organizzatore", organizzatore);

        return "homeOrganizzatore";
    }
    

    @GetMapping("/Organizzatore/impostazioniOrganizzatore")
    public String impostazioniBro(Model model, HttpSession session) {

        Organizzatore organizzatore=(Organizzatore) session.getAttribute("organizzatore");

        model.addAttribute("organizzatore", organizzatore);

        return "impostazioniOrganizzatore";
    }

    @PostMapping("/Organizzatore/impostazioniOrganizzatore")
    public String impostazioniBro(@ModelAttribute Organizzatore organizzatore,@RequestParam(name = "confermaPassword")String confermaPassword, Model model,HttpSession session) {

        //the old username you can get from the session
        String usernamevecchio = ((Organizzatore)session.getAttribute("organizzatore")).getUsername();

        //the new username is in the organizzatore object
        String organizzatoreAggiornato = utenteService.modificaProfilo(organizzatore, confermaPassword, "organizzatore", usernamevecchio);

        if ( organizzatoreAggiornato.equals("modifica effettuata")) {
            //if the username is changed, the session attribute must be updated
            session.setAttribute("organizzatore", organizzatore);
        }

        //if the username is changed, the session attribute must be updated
        model.addAttribute("message", organizzatoreAggiornato);

        return "impostazioniOrganizzatore";
    }


    @GetMapping("/Giocatore/impostazioniGiocatore")
    public String impostazioniPippo(Model model, HttpSession session) {

        //get the giocatore from the session
        Giocatore giocatore=(Giocatore) session.getAttribute("giocatore");

        //add the giocatore to the model
        model.addAttribute("giocatore", giocatore);
        return "impostazioniGiocatore";
    }

    @PostMapping("/Giocatore/impostazioniGiocatore")
    public String impostazioniPippo(@ModelAttribute Giocatore giocatore,@RequestParam(name = "confermaPassword")String confermaPassword, Model model,HttpSession session) {


        //the old username you can get from the session
        String usernamevecchio = ((Giocatore)session.getAttribute("giocatore")).getUsername();

        //the new username is in the giocatore object
        String giocatoreAggiornato = utenteService.modificaProfilo(giocatore, confermaPassword, "giocatore",usernamevecchio);

        if ( giocatoreAggiornato.equals("modifica effettuata")) {
            //if the username is changed, the session attribute must be updated
            session.setAttribute("giocatore", giocatore);
        }

        //if the username is changed, the session attribute must be updated
        model.addAttribute("message", giocatoreAggiornato);
        return "impostazioniGiocatore";
    }


    @GetMapping("/Giocatore/squadra")
    public String squadra(Model model,HttpSession session) {

        Giocatore organizzatore = (Giocatore) session.getAttribute("organizzatore");

        model.addAttribute("organizzatore", organizzatore);
        return "squadra";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Utente utente, @RequestParam( name = "ruolo")String ruolo, Model model, HttpSession session) {
        String result =utenteService.login(utente, ruolo);


        //if the login is successful, the user is redirected to the home page of the user's role
        if(result.equals("login effettuato")) {
            model.addAttribute("username", utente.getUsername());
            if(ruolo.equals("giocatore")) {
                session.setAttribute("giocatore", utenteService.findGiocatoreByUsername(utente.getUsername()));
                return "redirect:/Giocatore/homePlayer";
            }
            else{
                session.setAttribute("organizzatore", utenteService.findOrganizzatoreByUsername(utente.getUsername()));
                return "redirect:/Organizzatore/homeOrganizzatore";
            }

        }else
        {
            //if the login is not successful, the user is redirected to the login page with an error message
            model.addAttribute("error", result);
            return "login";
        }
    }

    @GetMapping("login")
    public String login(Model model) {
        Utente utente = new Utente();
        model.addAttribute("utente", utente);
        return "login";
    }

}
