package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.StatoTorneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.PartitaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.PartitaService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.mockito.quality.MockitoHint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TorneoTest {

    @InjectMocks
    private TorneoServiceImpl torneoService;

    @Mock
    private TorneoRepository torneoRepository;
    @Mock
    private OrganizzatoreRepository organizzatoreRepository;

    @Mock
    private GiocatoreRepository giocatoreRepository;

    @Mock
    private PartitaRepository partitaRepository;

    @Mock
    private PartitaService partitaService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(torneoRepository.findByNome(anyString())).thenReturn(null);
        when(organizzatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(partitaRepository.save(any())).thenReturn(null);
        when(partitaService.creaPartita(any(), any(), any())).thenReturn(null);
        when(torneoRepository.save(any())).thenReturn(null);
        when(giocatoreRepository.save(any())).thenReturn(null);
    }

    @Test
    public void testCreaTorneo() {

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");

        Torneo inputTorneo = Mockito.mock(Torneo.class);
        when(inputTorneo.getNome()).thenReturn("TorneoUnisa");
        when(inputTorneo.getData()).thenReturn(LocalDate.parse("2099-01-27"));
        when(inputTorneo.getRegole()).thenReturn("Solo pokemon della regione di Kanto");
        when(inputTorneo.getPremi()).thenReturn("Una pacca sulla spalla");
        when(inputTorneo.getOrganizzazione()).thenReturn("1vs1");
        when(inputTorneo.getCapienza()).thenReturn(16);
        when(inputTorneo.getOrganizzatore()).thenReturn(mockOrganizzatore);


        when(torneoRepository.findByNome("TorneoUnisa")).thenReturn(null);

        String result = torneoService.creaTorneo(inputTorneo, mockOrganizzatore);


        verify(torneoRepository, times(1)).save(inputTorneo);

        assertEquals("torneo creato con successo", result);

    }

    @Test
    public void testCreaTorneoNomeTorneoGiaEsistente() {

        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoUnisa");


        when(torneoRepository.findByNome("TorneoUnima")).thenReturn(mockTorneo);

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");


        Torneo inputTorneo = Mockito.mock(Torneo.class);
        when(inputTorneo.getNome()).thenReturn("TorneoUnima");
        when(inputTorneo.getData()).thenReturn(LocalDate.parse("2099-01-27"));
        when(inputTorneo.getRegole()).thenReturn("Solo pokemon della regione di Unima");
        when(inputTorneo.getPremi()).thenReturn("Una pacca sulla spalla");
        when(inputTorneo.getOrganizzazione()).thenReturn("TorneiForLife");
        when(inputTorneo.getCapienza()).thenReturn(16);
        when(inputTorneo.getOrganizzatore()).thenReturn(mockOrganizzatore);

        String result = torneoService.creaTorneo(inputTorneo, mockOrganizzatore);

        assertNull(inputTorneo.getStatoTorneo());

        verify(torneoRepository, times(0)).save(inputTorneo);

        assertEquals("nome torneo già esistente", result);

    }

    @Test
    public void testIniziareTorneo() {

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");


        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoPesce");
        when(mockTorneo.getData()).thenReturn(LocalDate.parse("2099-01-27"));
        when(mockTorneo.getRegole()).thenReturn("Solo pokemon della regione di Kanto");
        when(mockTorneo.getPremi()).thenReturn("Una pacca sulla spalla");
        when(mockTorneo.getOrganizzazione()).thenReturn("1vs1");
        when(mockTorneo.getCapienza()).thenReturn(2);
        when(mockTorneo.getOrganizzatore()).thenReturn(mockOrganizzatore);
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.ISCRIZIONICOMPLETATE);

        List<Giocatore> mockGiocatori = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
            mockGiocatori.add(mockGiocatore);
        }
        when(mockTorneo.getGiocatoreList()).thenReturn(mockGiocatori);


        when(torneoRepository.findByNome("TorneoPesce")).thenReturn(mockTorneo);
        when(organizzatoreRepository.findByUsername("Ugo Vaccaro")).thenReturn(mockOrganizzatore);


        boolean result = torneoService.iniziareTorneo(mockTorneo, mockOrganizzatore);

        verify(torneoRepository, times(1)).save(mockTorneo);

        assertTrue(result);

    }

    @Test
    public void testTerminareTorneo() {

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");


        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoGodwin");
        when(mockTorneo.getData()).thenReturn(LocalDate.parse("2099-01-27"));
        when(mockTorneo.getRegole()).thenReturn("Solo pokemon della regione di Kanto");
        when(mockTorneo.getPremi()).thenReturn("Una pacca sulla spalla");
        when(mockTorneo.getOrganizzazione()).thenReturn("TorneiForLife");
        when(mockTorneo.getCapienza()).thenReturn(16);
        when(mockTorneo.getOrganizzatore()).thenReturn(mockOrganizzatore);
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.INCORSO);

        when(torneoRepository.findByNome("TorneoGodwin")).thenReturn(mockTorneo);
        when(organizzatoreRepository.findByUsername("Ugo Vaccaro")).thenReturn(mockOrganizzatore);


        boolean result = torneoService.terminareTorneo(mockTorneo, mockOrganizzatore);


        verify(torneoRepository, times(1)).save(mockTorneo);

        assertTrue(result);

    }

    @Test
    public void testToglierePartecipanti() {

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");


        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoGodwin");
        when(mockTorneo.getData()).thenReturn(LocalDate.parse("2099-01-27"));
        when(mockTorneo.getRegole()).thenReturn("Solo pokemon della regione di Kanto");
        when(mockTorneo.getPremi()).thenReturn("Una pacca sulla spalla");
        when(mockTorneo.getOrganizzazione()).thenReturn("TorneiForLife");
        when(mockTorneo.getCapienza()).thenReturn(16);
        when(mockTorneo.getOrganizzatore()).thenReturn(mockOrganizzatore);
        when(mockTorneo.getGiocatoreList()).thenReturn(new ArrayList<>());
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.ATTESAISCRIZIONI);


        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Paolo_Sorrentino");
        mockTorneo.getGiocatoreList().add(mockGiocatore);

        when(torneoRepository.findByNome("TorneoGodwin")).thenReturn(mockTorneo);
        when(giocatoreRepository.findByUsername("Paolo_Sorrentino")).thenReturn(mockGiocatore);
        when(organizzatoreRepository.findByUsername("Ugo Vaccaro")).thenReturn(mockOrganizzatore);

        String result = torneoService.toglierePartecipanti(mockTorneo, mockGiocatore, mockOrganizzatore);

        assertEquals(StatoTorneo.ATTESAISCRIZIONI, mockTorneo.getStatoTorneo());

        verify(torneoRepository, times(1)).save(mockTorneo);

        assertEquals("giocatore rimosso con successo", result);
    }

    @Test
    public void testVisualizzaProfiloUtente() {

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");


        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoGodwin");
        when(mockTorneo.getData()).thenReturn(LocalDate.parse("2099-01-27"));
        when(mockTorneo.getRegole()).thenReturn("Solo pokemon della regione di Kanto");
        when(mockTorneo.getPremi()).thenReturn("Una pacca sulla spalla");
        when(mockTorneo.getOrganizzazione()).thenReturn("1vs1");
        when(mockTorneo.getCapienza()).thenReturn(16);
        when(mockTorneo.getOrganizzatore()).thenReturn(mockOrganizzatore);
        when(mockTorneo.getGiocatoreList()).thenReturn(new ArrayList<>());
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.ATTESAISCRIZIONI);



        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Paolo_Sorrentino");
        mockTorneo.getGiocatoreList().add(mockGiocatore);

        when(torneoRepository.findByNome("TorneoGodwin")).thenReturn(mockTorneo);
        when(organizzatoreRepository.findByUsername("Ugo Vaccaro")).thenReturn(mockOrganizzatore);
        when(giocatoreRepository.findByUsername("Paolo_Sorrentino")).thenReturn(mockGiocatore);

        Giocatore result = torneoService.visualizzaProfiloUtente(mockTorneo, mockGiocatore, mockOrganizzatore);

        assertEquals(mockGiocatore, result);

    }

    @Test
    public void testSeguireOrganizzatore() {

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo Vaccaro");



        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Annalisa_DeBonis");
        when(mockGiocatore.getOrganizzatori()).thenReturn(new ArrayList<>());


        when(organizzatoreRepository.findByUsername("Ugo Vaccaro")).thenReturn(mockOrganizzatore);
        when(giocatoreRepository.findByUsername("Annalisa_DeBonis")).thenReturn(mockGiocatore);

        boolean result = torneoService.seguireOrganizzatore(mockGiocatore, mockOrganizzatore);

        assertTrue(result);
        assertTrue(mockGiocatore.getOrganizzatori().contains(mockOrganizzatore));

    }


    @Test
    public void testIscrizioneTorneo() {

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Ugo_Ferrari");
        when(mockGiocatore.getTornei()).thenReturn(new ArrayList<>());



        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoUnisa");
        when(mockTorneo.getCapienza()).thenReturn(16);
        when(mockTorneo.getGiocatoreList()).thenReturn(new ArrayList<>());
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.ATTESAISCRIZIONI);

        when(giocatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockGiocatore);
        when(torneoRepository.findByNome("TorneoUnisa")).thenReturn(mockTorneo);


        String result = torneoService.iscrizioneTorneo(mockGiocatore, mockTorneo);

        assertEquals("iscrizione effettuata con successo", result);

        verify(torneoRepository, times(1)).save(mockTorneo);
        verify(giocatoreRepository, times(1)).save(mockGiocatore);

        assertTrue(mockTorneo.getGiocatoreList().contains(mockGiocatore));
        assertTrue(mockGiocatore.getTornei().contains(mockTorneo));
    }
    
    
    @Test
    public void testIscrizioneTorneoIscrizioniPiene() {

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Ugo_Ferrari");
        when(mockGiocatore.getTornei()).thenReturn(new ArrayList<>());


        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoSinnoh");
        when(mockTorneo.getCapienza()).thenReturn(2);
        when(mockTorneo.getGiocatoreList()).thenReturn(new ArrayList<>());
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.ISCRIZIONICOMPLETATE);

        for (int i = 0; i < 2; i++) {
            mockTorneo.getGiocatoreList().add(new Giocatore());
        }

        when(giocatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockGiocatore);
        when(torneoRepository.findByNome("TorneoSinnoh")).thenReturn(mockTorneo);

        String result = torneoService.iscrizioneTorneo(mockGiocatore, mockTorneo);

        assertEquals("iscrizioni piene", result);

        verify(torneoRepository, times(0)).save(mockTorneo);

        assertFalse(mockTorneo.getGiocatoreList().contains(mockGiocatore));
        assertFalse(mockGiocatore.getTornei().contains(mockTorneo));

    }

    @Test
    public void testIscrizioneTorneoIscrizioneTorneoRifiutata() {

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Annalisa_DeBonis");
        when(mockGiocatore.getTornei()).thenReturn(new ArrayList<>());
        List<String> iscrizioni = new ArrayList<>();
        iscrizioni.add("TorneoUnisa");
        when(mockGiocatore.getIscrizioni()).thenReturn(iscrizioni);

        Torneo mockTorneo = Mockito.mock(Torneo.class);
        when(mockTorneo.getNome()).thenReturn("TorneoUnisa");
        when(mockTorneo.getCapienza()).thenReturn(16);
        when(mockTorneo.getGiocatoreList()).thenReturn(new ArrayList<>());
        when(mockTorneo.getStatoTorneo()).thenReturn(StatoTorneo.ATTESAISCRIZIONI);


        mockGiocatore.getTornei().add(mockTorneo);

        when(giocatoreRepository.findByUsername("Annalisa_DeBonis")).thenReturn(mockGiocatore);
        when(torneoRepository.findByNome("TorneoUnisa")).thenReturn(mockTorneo);

        String result = torneoService.iscrizioneTorneo(mockGiocatore, mockTorneo);

        assertEquals("iscrizione torneo rifiutata", result);

        verify(torneoRepository, times(0)).save(mockTorneo);

        assertFalse(mockTorneo.getGiocatoreList().contains(mockGiocatore));
        assertTrue(mockGiocatore.getTornei().contains(mockTorneo));
    }

    @Test
    public void testCercareTorneo () {

        List<Torneo> tornei = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Torneo t = Mockito.mock(Torneo.class);
            when(t.getNome()).thenReturn("TorneoUnisaGG");
            tornei.add(t);
        }

        when(torneoRepository.findByNomeLike("TorneoUnisa")).thenReturn(tornei);

        List<Torneo> result = torneoService.cercareTorneo("TorneoUnisa");

        assertEquals(5, result.size());

        verify(torneoRepository, times(1)).findByNomeLike("TorneoUnisa");

        for (Torneo t : result) {
            assertTrue(t.getNome().toLowerCase().contains("TorneoUnisa".toLowerCase()));
        }
    }

    @Test
    public void testFindByName(){
        Torneo t = Mockito.mock(Torneo.class);
        when(t.getNome()).thenReturn("TorneoUnisa");


        when(torneoRepository.findByNome("TorneoUnisa")).thenReturn(t);

        Torneo result = torneoService.findByName("TorneoUnisa");

        assertEquals(t, result);

        verify(torneoRepository, times(1)).findByNome("TorneoUnisa");
    }


}



