package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.RichiestaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ModeratoreController {

    public  final ModeratoreService moderatoreService;

    public final UtenteService utenteService;

    public final TorneoService torneoService;

    @Autowired
    public ModeratoreController(ModeratoreService moderatoreService, UtenteService utenteService, TorneoService torneoService){
        this.moderatoreService = moderatoreService;
        this.utenteService = utenteService;
        this.torneoService = torneoService;
    }

    @GetMapping("/Moderatore/")
    public String index(Model model) {
        return "redirect:/Moderatore/loginAdmin";
    }



    @GetMapping("/Moderatore/loginAdmin")
    public String loginAdmin(Model model) {
        return "loginAdmin";
    }


    @GetMapping("/Moderatore/logoutAdmin")
    public String logoutAdmin(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("moderatore");
        redirectAttributes.addFlashAttribute("message", "Logout effettuato");
        return "redirect:/Moderatore/loginAdmin";
    }

    @GetMapping("/Moderatore/bannaOrganizzatore")
    public String bannaOrganizzatore(@RequestParam(value = "username") String username,Model model, HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");



        Organizzatore utente=new Organizzatore();
        utente.setUsername(username);
        if(moderatoreService.bannare(moderatore, utente,"organizzatore"))
            model.addAttribute("message", "Utente bannato: $"+ username);
        else
            model.addAttribute("error", "Errore nel bannare l'utente: $"+ username);
        model.addAttribute("moderatore", moderatore);


        List<Giocatore> giocatori=utenteService.findGiocatoriPaged(0,5);
        List<Organizzatore> organizzatori=utenteService.findOrganizzatoriPaged(0,5);

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("organizzatori", organizzatori);

        return "listaUtenti";
    }

    @GetMapping("/Moderatore/sbannaOrganizzatore")
    public String sbannaOrganizzatore(@RequestParam(value = "username") String username,Model model, HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");

        Organizzatore utente=new Organizzatore();
        utente.setUsername(username);
        if(moderatoreService.sbannare(moderatore, utente,"organizzatore"))
            model.addAttribute("message", "Utente sbannato: $"+ username);
        else
            model.addAttribute("error", "Errore nel bannare l'utente: $"+ username);

        model.addAttribute("moderatore", moderatore);


        List<Giocatore> giocatori=utenteService.findGiocatoriPaged(0,5);
        List<Organizzatore> organizzatori=utenteService.findOrganizzatoriPaged(0,5);

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("organizzatori", organizzatori);

        return "listaUtenti";
    }

    @GetMapping("/Moderatore/bannaGiocatore")
    public String bannaGiocatore(@RequestParam(value = "username") String username,Model model, HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");


        Giocatore utente=new Giocatore();
        utente.setUsername(username);
        if(moderatoreService.bannare(moderatore, utente,"giocatore"))
            model.addAttribute("message", "Utente bannato: $"+ username);
        else
            model.addAttribute("error", "Errore nel bannare l'utente: $"+ username);
        model.addAttribute("moderatore", moderatore);

        List<Giocatore> giocatori=utenteService.findGiocatoriPaged(0,5);
        List<Organizzatore> organizzatori=utenteService.findOrganizzatoriPaged(0,5);

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("organizzatori", organizzatori);

        return "listaUtenti";
    }

    @GetMapping("/Moderatore/sbannaGiocatore")
    public String sbannaGiocatore(@RequestParam(value = "username") String username,Model model, HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");

        Giocatore utente=new Giocatore();
        utente.setUsername(username);
        if(moderatoreService.sbannare(moderatore, utente,"giocatore"))
            model.addAttribute("message", "Utente bannato: $"+ username);
        else
            model.addAttribute("error", "Errore nel bannare l'utente: $"+ username);
        model.addAttribute("moderatore", moderatore);
        List<Giocatore> giocatori=utenteService.findGiocatoriPaged(0,5);
        List<Organizzatore> organizzatori=utenteService.findOrganizzatoriPaged(0,5);

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("organizzatori", organizzatori);

        return "listaUtenti";
    }

    @PostMapping("/Moderatore/loginAdmin")
    public String loginAdmin(Moderatore moderatore, Model model, HttpSession session) {
        String result = moderatoreService.login(moderatore);
        if(result.equals("Login effettuato")){
            session.setAttribute("moderatore", moderatore);
            return "redirect:/Moderatore/homeAdmin";
        }
        else{
            model.addAttribute("error", result);
            return "loginAdmin";
        }
    }

    @GetMapping("/Moderatore/homeAdmin")
    public String homeAdmin(Model model,HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");
        List<Richiesta> richieste = moderatoreService.notifiche();
        int countUtentiIscritti = utenteService.countUtentiTotali();
        int countGiocatori = utenteService.countGiocatoriTotali();
        int countOrganizzatori = utenteService.countOrganizzatoriTotali();
        int countTornei = torneoService.countTorneiTotali();


        model.addAttribute("richieste", richieste);
        model.addAttribute("moderatore", moderatore);
        model.addAttribute("countUtentiIscritti", countUtentiIscritti);
        model.addAttribute("countGiocatori", countGiocatori);
        model.addAttribute("countOrganizzatori", countOrganizzatori);
        model.addAttribute("countTornei", countTornei);


        return "admin";
    }

    @GetMapping("/Moderatore/centroNotifiche")
    public String centroNotifiche(Model model,HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");
        model.addAttribute("moderatore", moderatore);
        List<Richiesta> notifiche=moderatoreService.notifiche();

        model.addAttribute("notifiche", notifiche);

        return "centroNotifiche";
    }

    @GetMapping("/Moderatore/impostazioni")
    public String impostazioni(Model model,HttpSession session) {
        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");
        model.addAttribute("moderatore", moderatore);
        return "impostazioni";
    }

    @GetMapping("/Moderatore/listaUtenti")
    public String listaUtenti(@RequestParam(defaultValue = "0",name="page1")String page1, @RequestParam(defaultValue = "0",name="page2")String page2, Model model, HttpSession session) {
        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");
        model.addAttribute("moderatore", moderatore);
        int giocatorpage=0;
        int organizzatorpage=0;

        if(page1!=null) {
            giocatorpage=Integer.parseInt(page1);
        }

        if(page2!=null) {
            organizzatorpage=Integer.parseInt(page2);
        }

        model.addAttribute("pageBack1", giocatorpage-1==-1 ? giocatorpage : giocatorpage-1);
        model.addAttribute("pageForward1", giocatorpage+1);
        model.addAttribute("pageBack2", organizzatorpage-1 ==-1 ? organizzatorpage : organizzatorpage-1);
        model.addAttribute("pageForward2", organizzatorpage+1);


        List<Giocatore> giocatori=utenteService.findGiocatoriPaged(giocatorpage,5);
        List<Organizzatore> organizzatori=utenteService.findOrganizzatoriPaged(organizzatorpage,5);

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("organizzatori", organizzatori);


        return "listaUtenti";
    }


    @GetMapping("/Moderatore/accettareRichiesta")
    public String accettareRichiesta(@RequestParam(value = "username") String username,Model model, HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");

        Organizzatore organizzatore=new Organizzatore();
        organizzatore.setUsername(username);
        moderatoreService.accettare(moderatore, organizzatore);

        model.addAttribute("message", "Richiesta accettata per l'utente: $"+ username);
        model.addAttribute("moderatore", moderatore);
        return "centroNotifiche";
    }

    @GetMapping("/Moderatore/rifiutareRichiesta")
    public String rifiutareRichiesta(@RequestParam(value = "username") String username,Model model, HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");

        Organizzatore organizzatore=new Organizzatore();
        organizzatore.setUsername(username);
        moderatoreService.rifiutare(moderatore, organizzatore);

        model.addAttribute("message", "Richiesta rifiutata per l'utente: $"+ username);
        model.addAttribute("moderatore", moderatore);

        return "centroNotifiche";
    }

}
