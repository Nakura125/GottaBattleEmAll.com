package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Richiesta;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ModeratoreController {

    public  final ModeratoreService moderatoreService;

    public final UtenteService utenteService;

    @Autowired
    public ModeratoreController(ModeratoreService moderatoreService, UtenteService utenteService){
        this.moderatoreService = moderatoreService;
        this.utenteService = utenteService;
    }

    @GetMapping("/Moderatore/")
    public String index(Model model) {
        return "redirect:/Moderatore/loginAdmin";
    }



    @GetMapping("/Moderatore/loginAdmin")
    public String loginAdmin(Model model) {
        return "loginAdmin";
    }

    @PostMapping("/Moderatore/loginAdmin")
    public String loginAdmin(Moderatore moderatore, Model model, HttpSession session) {
        String result = moderatoreService.login(moderatore);
        if(result.equals("Login effettuato")){
            session.setAttribute("moderatore", moderatore);
            return "admin";
        }
        else{
            model.addAttribute("error", result);
            return "loginAdmin";
        }
    }

    @GetMapping("/Moderatore/homeAdmin")
    public String homeAdmin(Model model,HttpSession session) {

        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");
        model.addAttribute("moderatore", moderatore);
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
    public String listaUtenti(Model model,HttpSession session) {
        Moderatore moderatore=(Moderatore) session.getAttribute("moderatore");
        model.addAttribute("moderatore", moderatore);
        return "listaUtenti";
    }

}
