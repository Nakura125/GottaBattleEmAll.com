package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.StatoTorneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(torneoRepository.findByNome(anyString())).thenReturn(null);
        when(organizzatoreRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    public void testCreaTorneo() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo Vaccaro");


        Torneo inputTorneo = new Torneo();
        inputTorneo.setNome("TorneoUnisa");
        inputTorneo.setData(LocalDate.parse("2099-01-27"));
        inputTorneo.setRegole("Solo pokemon della regione di Kanto");
        inputTorneo.setPremi("Una pacca sulla spalla");
        inputTorneo.setOrganizzazione("TorneiForLife");
        inputTorneo.setCapienza(16);
        inputTorneo.setOrganizzatore(mockOrganizzatore);

        when(torneoRepository.findByNome("TorneoUnisa")).thenReturn(null);

        String result = torneoService.creaTorneo(inputTorneo, mockOrganizzatore);

        assertEquals(StatoTorneo.ATTESAISCRIZIONI, inputTorneo.getStatoTorneo());

        verify(torneoRepository, times(1)).save(inputTorneo);

        assertEquals("torneo creato con successo", result);

    }

    @Test
    public void testCreaTorneoNomeTorneoGiaEsistente() {

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoUnima");

        when(torneoRepository.findByNome("TorneoUnima")).thenReturn(mockTorneo);

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo Vaccaro");

        Torneo inputTorneo = new Torneo();
        inputTorneo.setNome("TorneoUnima");
        inputTorneo.setData(LocalDate.parse("2099-01-27"));
        inputTorneo.setRegole("Solo pokemon della regione di Unima");
        inputTorneo.setPremi("Una pacca sulla spalla");
        inputTorneo.setOrganizzazione("TorneiForLife");
        inputTorneo.setCapienza(16);
        inputTorneo.setOrganizzatore(mockOrganizzatore);

        String result = torneoService.creaTorneo(inputTorneo, mockOrganizzatore);

        assertNull(inputTorneo.getStatoTorneo());

        verify(torneoRepository, times(0)).save(inputTorneo);

        assertEquals("nome torneo già esistente", result);

    }

    @Test
    public void testIniziareTorneo(){

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo Vaccaro");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoPesce");
        mockTorneo.setData(LocalDate.parse("2099-01-27"));
        mockTorneo.setRegole("Solo pokemon della regione di Kanto");
        mockTorneo.setPremi("Una pacca sulla spalla");
        mockTorneo.setOrganizzazione("TorneiForLife");
        mockTorneo.setCapienza(16);
        mockTorneo.setGiocatoreList(new ArrayList<>());
        mockTorneo.setOrganizzatore(mockOrganizzatore);
        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);

        for(int i = 0; i < 16; i++){
            mockTorneo.getGiocatoreList().add(new Giocatore());
        }

        when(torneoRepository.findByNome("TorneoPesce")).thenReturn(mockTorneo);

        boolean result = torneoService.iniziareTorneo(mockTorneo, mockOrganizzatore);

        assertEquals(StatoTorneo.INCORSO, mockTorneo.getStatoTorneo());

        verify(torneoRepository, times(1)).save(mockTorneo);

        assertTrue(result);

    }

    @Test
    public void testTerminareTorneo(){

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo Vaccaro");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoGodwin");
        mockTorneo.setData(LocalDate.parse("2099-01-27"));
        mockTorneo.setRegole("Solo pokemon della regione di Kanto");
        mockTorneo.setPremi("Una pacca sulla spalla");
        mockTorneo.setOrganizzazione("TorneiForLife");
        mockTorneo.setCapienza(16);
        mockTorneo.setGiocatoreList(new ArrayList<>());
        mockTorneo.setOrganizzatore(mockOrganizzatore);
        mockTorneo.setStatoTorneo(StatoTorneo.INCORSO);

        when(torneoRepository.findByNome("TorneoGodwin")).thenReturn(mockTorneo);

        boolean result = torneoService.terminareTorneo(mockTorneo, mockOrganizzatore);

        assertEquals(StatoTorneo.TORNEOCOMPLETATO, mockTorneo.getStatoTorneo());

        verify(torneoRepository, times(1)).save(mockTorneo);

        assertTrue(result);

    }

    @Test
    public void testToglierePartecipanti() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo Vaccaro");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoGodwin");
        mockTorneo.setData(LocalDate.parse("2099-01-27"));
        mockTorneo.setRegole("Solo pokemon della regione di Kanto");
        mockTorneo.setPremi("Una pacca sulla spalla");
        mockTorneo.setOrganizzazione("TorneiForLife");
        mockTorneo.setCapienza(16);
        mockTorneo.setOrganizzatore(mockOrganizzatore);
        mockTorneo.setGiocatoreList(new ArrayList<>());
        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Paolo_Sorrentino");
        mockTorneo.getGiocatoreList().add(mockGiocatore);

        when(torneoRepository.findByNome("TorneoGodwin")).thenReturn(mockTorneo);

        String result = torneoService.toglierePartecipanti(mockTorneo, mockGiocatore, mockOrganizzatore);

        assertEquals(StatoTorneo.ATTESAISCRIZIONI, mockTorneo.getStatoTorneo());

        verify(torneoRepository, times(1)).save(mockTorneo);

        assertEquals("giocatore rimosso con successo", result);
    }

    @Test
    public void testVisualizzaProfiloUtente() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo Vaccaro");

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoGodwin");
        mockTorneo.setData(LocalDate.parse("2099-01-27"));
        mockTorneo.setRegole("Solo pokemon della regione di Kanto");
        mockTorneo.setPremi("Una pacca sulla spalla");
        mockTorneo.setOrganizzazione("TorneiForLife");
        mockTorneo.setCapienza(16);
        mockTorneo.setOrganizzatore(mockOrganizzatore);
        mockTorneo.setGiocatoreList(new ArrayList<>());
        mockTorneo.setStatoTorneo(StatoTorneo.ATTESAISCRIZIONI);

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Paolo_Sorrentino");
        mockTorneo.getGiocatoreList().add(mockGiocatore);

        when(torneoRepository.findByNome("TorneoGodwin")).thenReturn(mockTorneo);

        Giocatore result = torneoService.visualizzaProfiloUtente(mockTorneo, mockGiocatore, mockOrganizzatore);

        assertEquals(mockGiocatore, result);

    }


}