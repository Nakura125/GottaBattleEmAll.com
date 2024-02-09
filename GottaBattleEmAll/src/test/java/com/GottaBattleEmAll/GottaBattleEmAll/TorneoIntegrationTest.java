package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.StatoTorneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.PartitaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.PartitaService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TorneoIntegrationTest {

    @Autowired
    private TorneoServiceImpl torneoService;

    @Autowired
    private TorneoRepository torneoRepository;
    @Autowired
    private OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @Autowired
    private PartitaRepository partitaRepository;

    @Autowired
    private PartitaService partitaService;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreaTorneo() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore");


        Torneo inputTorneo = new Torneo();
        inputTorneo.setNome("TorneoUnisa");
        inputTorneo.setData(LocalDate.parse("2099-01-27"));
        inputTorneo.setRegole("Solo pokemon della regione di Kanto");
        inputTorneo.setPremi("Una pacca sulla spalla");
        inputTorneo.setOrganizzazione("TorneiForLife");
        inputTorneo.setCapienza(16);
        inputTorneo.setOrganizzatore(mockOrganizzatore);


        String result = torneoService.creaTorneo(inputTorneo, mockOrganizzatore);

        assertEquals(StatoTorneo.ATTESAISCRIZIONI, inputTorneo.getStatoTorneo());


        assertEquals("torneo creato con successo", result);

    }

    @Test
    public void testCreaTorneoNomeTorneoGiaEsistente() {

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo1");


        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore");

        Torneo inputTorneo = new Torneo();
        inputTorneo.setNome("Torneo1");
        inputTorneo.setData(LocalDate.parse("2099-01-27"));
        inputTorneo.setRegole("Solo pokemon della regione di Unima");
        inputTorneo.setPremi("Una pacca sulla spalla");
        inputTorneo.setOrganizzazione("TorneiForLife");
        inputTorneo.setCapienza(16);
        inputTorneo.setOrganizzatore(mockOrganizzatore);

        String result = torneoService.creaTorneo(inputTorneo, mockOrganizzatore);

        assertNull(inputTorneo.getStatoTorneo());

        assertEquals("nome torneo già esistente", result);

    }

    @Test
    public void testIniziareTorneo() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo3");
        mockTorneo.setStatoTorneo(StatoTorneo.ISCRIZIONICOMPLETATE);


        boolean result = torneoService.iniziareTorneo(mockTorneo, mockOrganizzatore);



        assertTrue(result);

    }

    @Test
    public void testTerminareTorneo() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo4");

        mockTorneo.setStatoTorneo(StatoTorneo.INCORSO);


        boolean result = torneoService.terminareTorneo(mockTorneo, mockOrganizzatore);

        assertTrue(result);

    }

    @Test
    public void testToglierePartecipanti() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo3");

        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore1");
        mockTorneo.getGiocatoreList().add(mockGiocatore);


        String result = torneoService.toglierePartecipanti(mockTorneo, mockGiocatore, mockOrganizzatore);

        assertEquals(StatoTorneo.ATTESAISCRIZIONI, mockTorneo.getStatoTorneo());


        assertEquals("giocatore rimosso con successo", result);
    }

    @Test
    public void testVisualizzaProfiloUtente() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo1");
        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore1");


        Giocatore result = torneoService.visualizzaProfiloUtente(mockTorneo, mockGiocatore, mockOrganizzatore);

        assertEquals(mockGiocatore.getUsername(), result.getUsername());

    }



    @Test
    public void testIscrizioneTorneo() {

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore3");


        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo1");
        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);


        String result = torneoService.iscrizioneTorneo(mockGiocatore, mockTorneo);

        assertEquals("iscrizione effettuata con successo", result);


    }
    
    
    @Test
    public void testIscrizioneTorneoIscrizioniPiene() {

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore14");
        mockGiocatore.setTornei(new ArrayList<>());


        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo3");
        mockTorneo.setStatoTorneo(StatoTorneo.ISCRIZIONICOMPLETATE);




        String result = torneoService.iscrizioneTorneo(mockGiocatore, mockTorneo);

        assertEquals("iscrizioni piene", result);


        assertFalse(mockTorneo.getGiocatoreList().contains(mockGiocatore));
        assertFalse(mockGiocatore.getTornei().contains(mockTorneo));

    }

    @Test
    public void testIscrizioneTorneoIscrizioneTorneoRifiutata() {

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore123");
        mockGiocatore.setTornei(new ArrayList<>());


        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("Torneo1");
        mockTorneo.setCapienza(16);
        mockTorneo.setGiocatoreList(new ArrayList<>());
        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);


        mockGiocatore.getTornei().add(mockTorneo);


        String result = torneoService.iscrizioneTorneo(mockGiocatore, mockTorneo);

        assertEquals("iscrizione torneo rifiutata", result);


        assertFalse(mockTorneo.getGiocatoreList().contains(mockGiocatore));
        assertTrue(mockGiocatore.getTornei().contains(mockTorneo));
    }

    @Test
    public void testCercareTorneo () {

        List<Torneo> tornei = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Torneo t = new Torneo();
            t.setNome("T");
            tornei.add(t);
        }


        List<Torneo> result = torneoService.cercareTorneo("Torneo");

        assertEquals(4, result.size());


        for (Torneo t : result) {
            assertTrue(t.getNome().toLowerCase().contains("Torneo".toLowerCase()));
        }
    }

    @Test
    public void testFindByName(){
        Torneo t = new Torneo();
        t.setNome("Torneo1");


        Torneo result = torneoService.findByName("Torneo1");

        assertEquals(t.getNome(), result.getNome());

    }


}



