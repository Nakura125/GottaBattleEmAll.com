package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.StatoTorneo;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Torneo;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.TorneoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        Organizzatore organizzatore = new Organizzatore();
        organizzatore.setUsername("Ugo Vaccaro");


        Torneo inputTorneo = new Torneo();
        inputTorneo.setNome("TorneoUnisa");
        inputTorneo.setData(LocalDate.parse("2099-01-27"));
        inputTorneo.setRegole("Solo pokemon della regione di Kanto");
        inputTorneo.setPremi("Una pacca sulla spalla");
        inputTorneo.setOrganizzazione("TorneiForLife");
        inputTorneo.setCapienza(16);
        inputTorneo.setOrganizzatore(organizzatore);

        when(torneoRepository.findByNome("TorneoUnisa")).thenReturn(null);

        String result = torneoService.creaTorneo(inputTorneo, organizzatore);

        assertEquals(StatoTorneo.ATTESAISCRIZIONI, inputTorneo.getStatoTorneo());

        verify(torneoRepository, times(1)).save(inputTorneo);

        assertEquals("torneo creato con successo", result);

    }

    @Test
    public void testCreaTorneoNomeTorneoGiaEsistente() {

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoUnima");

        when(torneoRepository.findByNome("TorneoUnima")).thenReturn(mockTorneo);

        Organizzatore organizzatore = new Organizzatore();
        organizzatore.setUsername("Ugo Vaccaro");

        Torneo inputTorneo = new Torneo();
        inputTorneo.setNome("TorneoUnima");
        inputTorneo.setData(LocalDate.parse("2099-01-27"));
        inputTorneo.setRegole("Solo pokemon della regione di Unima");
        inputTorneo.setPremi("Una pacca sulla spalla");
        inputTorneo.setOrganizzazione("TorneiForLife");
        inputTorneo.setCapienza(16);
        inputTorneo.setOrganizzatore(organizzatore);

        String result = torneoService.creaTorneo(inputTorneo, organizzatore);

        assertNull(inputTorneo.getStatoTorneo());

        verify(torneoRepository, times(0)).save(inputTorneo);

        assertEquals("nome torneo già esistente", result);

    }

}
