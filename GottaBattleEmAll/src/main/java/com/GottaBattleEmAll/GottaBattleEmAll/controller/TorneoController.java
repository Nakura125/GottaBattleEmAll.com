package com.GottaBattleEmAll.GottaBattleEmAll.controller;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class TorneoController {

    public final TorneoService torneoService;
    public final UtenteService userService;

    @Autowired
    public TorneoController(TorneoService torneoService, UtenteService userService) {
        this.torneoService = torneoService;
        this.userService = userService;
    }



    @GetMapping("/Organizzatore/torneoInAttesa")
    public String torneoInAttesa(@RequestParam(name="name") String nome, HttpSession session,Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        model.addAttribute("torneo", torneo);

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        model.addAttribute("organizzatore", organizzatore);

        List<Giocatore> giocatori = torneoService.getPartecipanti(torneo);
        model.addAttribute("giocatori", giocatori);
        return "torneoInAttesa";
    }

    @GetMapping("/Organizzatore/torneoInCorso")
    public String torneoInCorso(@RequestParam(name="name") String nome ,HttpSession session,Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        model.addAttribute("torneo", torneo);

        int round= (int) Math.floor(Math.log(torneo.getCapienza())/Math.log(2));

        List<Integer> rounds = new ArrayList<>();
        for (int i = 1; i <= round; i++) {
            rounds.add(i);
        }

        model.addAttribute("rounds", rounds);


        List<Partita> partite = new ArrayList<>(torneo.getPartite());

        for(int i= partite.size();i < torneo.getCapienza();i++)
            partite.add(new Partita());

        List<List<Partita>> partiteRound = new ArrayList<>();

        for (int i=0; i< partite.size();i++){
            List<Partita> partiteRoundI = new ArrayList<>();
            for(int j=0;j<partite.size()/2; j++,i++){
                partiteRoundI.add(partite.get(i));
                if(!partite.get(i).getGiocatoreList().isEmpty())
                    System.out.println(partite.get(i).getGiocatoreList().get(0).getUsername());
            }
            partiteRound.add(partiteRoundI);


            List<Partita> partiteRoundI2 = new ArrayList<>();
            for(int j=0;j<partite.size()/4; j++,i++){
                partiteRoundI2.add(partite.get(i));
            }
            partiteRound.add(partiteRoundI2);


            List<Partita> partiteRoundI3 = new ArrayList<>();
            for(int j=0;j<partite.size()/8; j++,i++){
                partiteRoundI3.add(partite.get(i));
            }
            partiteRound.add(partiteRoundI3);

            List<Partita> partiteRoundI4 = new ArrayList<>();
            for(int j=0;j<partite.size()/16; j++,i++){
                partiteRoundI4.add(partite.get(i));
            }
            partiteRound.add(partiteRoundI4);

        }

        model.addAttribute("partiteround", partiteRound);

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        model.addAttribute("organizzatore", organizzatore);

        List<Giocatore> giocatori = torneoService.getPartecipanti(torneo);
        model.addAttribute("giocatori", giocatori);

        return "torneoInCorso";
    }

    @GetMapping("/Organizzatore/torneoConcluso")
    public String torneoConcluso(@RequestParam(name="name") String nome,HttpSession session,Model model) {

        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        model.addAttribute("torneo", torneo);

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        model.addAttribute("organizzatore", organizzatore);

        List<Giocatore> giocatori = torneoService.getPartecipanti(torneo);
        model.addAttribute("giocatori", giocatori);
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
    public String ricercaTornei(@RequestParam(name="nomeTorneo")String nome ,HttpSession session,Model model)
    {
        List<Torneo> tornei=torneoService.cercareTorneo(nome);


        System.out.println("Tornei trovati:");
        tornei.forEach(System.out::println);
        System.out.println("Fine tornei trovati");

        model.addAttribute("tornei", tornei);

        Giocatore giocatore = (Giocatore) session.getAttribute("giocatore");
        model.addAttribute("giocatore", giocatore);

        model.addAttribute("nomeTorneo", nome);
        return "ricercaTornei";
    }


    @GetMapping("/Giocatore/iscrizioneTorneo")
    public String iscrizioneTorneo(@RequestParam(name="name") String nome,RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Giocatore/ricercaTornei";
        }

        model.addAttribute("torneo", torneo);

        Giocatore giocatore = (Giocatore) session.getAttribute("giocatore");
        model.addAttribute("giocatore", giocatore);

        List<Giocatore> giocatori = torneoService.getPartecipanti(torneo);
        model.addAttribute("giocatori", giocatori);

        String error=(String) redirectAttributes.getAttribute("error");

        if (error!=null) {
            model.addAttribute("error", error);
        }

        if (torneo.getStatoTorneo()== StatoTorneo.ATTESAISCRIZIONI)
            return "torneoInAttesaGiocatore";
        return "torneoIscrizioneChiuseGiocatore";
    }

    @GetMapping("/Giocatore/iscrizione")
    public String iscrizione(@RequestParam(name="name") String nome, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Giocatore/ricercaTornei";
        }

        model.addAttribute("torneo", torneo);

        Giocatore giocatore = (Giocatore) session.getAttribute("giocatore");
        model.addAttribute("giocatore", giocatore);

        String result=torneoService.iscrizioneTorneo(giocatore,torneo);
        if (result.equals("iscrizione effettuata con successo")) {
            return "redirect:/Giocatore/listaTornei";
        }

        model.addAttribute("error", result);
        redirectAttributes.addFlashAttribute("error", result);

        return "redirect:/Giocatore/iscrizioneTorneo?name="+nome;
    }

    @GetMapping("/Giocatore/listaTornei")
    public String listaTorneiGiocatore(HttpSession session, Model model) {
        Giocatore giocatore = (Giocatore) session.getAttribute("giocatore");
        model.addAttribute("giocatore", giocatore);

        List<Torneo> tornei = torneoService.getTorneoIscritto(giocatore);
        model.addAttribute("tornei", tornei);
        return "listaTornei";
    }

    @GetMapping("/Organizzatore/iniziaTorneo")
    public String iniziaTorneo(@RequestParam(name="name") String nome, HttpSession session, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        model.addAttribute("torneo", torneo);

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        model.addAttribute("organizzatore", organizzatore);

        List<Giocatore> giocatori = torneoService.getPartecipanti(torneo);
        model.addAttribute("giocatori", giocatori);

        boolean result=torneoService.iniziareTorneo(torneo, organizzatore);
        if (result) {
            return "redirect:/Organizzatore/torneoInCorso?name="+nome;
        }
        model.addAttribute("error", "torneo non iniziabile");
        return "torneoInAttesa";
    }

    @GetMapping("/Organizzatore/AvviaTorneo")
    public String iscriviti(@RequestParam(name="name") String nome, HttpSession session, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        organizzatore= userService.findOrganizzatoreByUsername(organizzatore.getUsername());

        boolean iniziare=torneoService.iniziareTorneo(torneo, organizzatore);

        if(iniziare)
            return "redirect:/Organizzatore/torneoInCorso?name="+nome;

        model.addAttribute("torneo", torneo);
        model.addAttribute("organizzatore", organizzatore);
        model.addAttribute("giocatori", torneoService.getPartecipanti(torneo));
        model.addAttribute("error", "torneo non iniziabile giocatori insufficienti");
        return "torneoInAttesa";
    }

    @GetMapping("/Organizzatore/terminaTorneo")
    public String termina(@RequestParam(name="name") String nome, HttpSession session, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        organizzatore= userService.findOrganizzatoreByUsername(organizzatore.getUsername());

        boolean terminare=torneoService.terminareTorneo(torneo, organizzatore);

        if(terminare)
            return "redirect:/Organizzatore/torneoConcluso?name="+nome;

        model.addAttribute("torneo", torneo);
        model.addAttribute("organizzatore", organizzatore);
        model.addAttribute("giocatori", torneoService.getPartecipanti(torneo));
        model.addAttribute("error", "torneo non concludibile");
        return "torneoInCorso";
    }


    @GetMapping("/Organizzatore/rimuoviPartecipante")
    public String rimuoviPartecipante(@RequestParam(name="name") String nome,@RequestParam(name="giocatore")String username, HttpSession session, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        organizzatore= userService.findOrganizzatoreByUsername(organizzatore.getUsername());

        Giocatore giocatore = userService.findGiocatoreByUsername(username);

        String rimozione=torneoService.toglierePartecipanti(torneo, giocatore,organizzatore);

        if(rimozione.equals("giocatore rimosso con successo"))
            return "redirect:/Organizzatore/torneoInAttesa?name="+nome;

        model.addAttribute("torneo", torneo);
        model.addAttribute("organizzatore", organizzatore);
        model.addAttribute("giocatori", torneoService.getPartecipanti(torneo));
        model.addAttribute("error", rimozione);
        return "torneoInAttesa";
    }


    @GetMapping("/Organizzatore/visualizzaProfiloGiocatore")
    public String visualizzaProfiloGiocatore(@RequestParam(name="name") String nome,@RequestParam(name="giocatore")String username, HttpSession session, Model model) {
        Torneo torneo = torneoService.findByName(nome);
        System.out.println(torneo);

        if (Objects.isNull(torneo)) {
            return "redirect:/Organizzatore/creaTorneo";
        }

        Organizzatore organizzatore = (Organizzatore) session.getAttribute("organizzatore");
        organizzatore= userService.findOrganizzatoreByUsername(organizzatore.getUsername());

        Giocatore giocatore = userService.findGiocatoreByUsername(username);

        Giocatore g=torneoService.visualizzaProfiloUtente(torneo, giocatore,organizzatore);

        if(g==null)
            return "redirect:/Organizzatore/homeOrganizzatore";

        model.addAttribute("torneo", torneo);
        model.addAttribute("organizzatore", organizzatore);
        model.addAttribute("giocatore", g);
        return "profiloUtente";
    }


}
