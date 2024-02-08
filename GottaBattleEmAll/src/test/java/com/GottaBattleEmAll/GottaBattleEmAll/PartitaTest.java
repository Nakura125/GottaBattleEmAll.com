package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.PartitaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.TorneoRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.PartitaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PartitaTest {

    @InjectMocks
    private PartitaServiceImpl partitaService;

    @Mock
    private PartitaRepository partitaRepository;
    @Mock
    private TorneoRepository torneoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(partitaRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    }

    @Test
    public void testAggiungereRisultato() {

        List<Pokemon> pokemonList1 = new ArrayList<>();
        List<Pokemon> pokemonList2 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = new Pokemon();
            pokemon.setNome("Ditto");
            pokemonList1.add(pokemon);
        }

        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = new Pokemon();
            pokemon.setNome("Ditto");
            pokemonList2.add(pokemon);
        }

        Giocatore mockGiocatore1 = new Giocatore();
        mockGiocatore1.setUsername("Pippo1");
        mockGiocatore1.setNome("Pippo1");
        mockGiocatore1.setCognome("Pippo1");
        mockGiocatore1.setEmail("Pippo1@studenti.unisa.it");
        mockGiocatore1.setPassword("Pippo1");
        mockGiocatore1.setPokemons(pokemonList1);

        Giocatore mockGiocatore2 = new Giocatore();
        mockGiocatore2.setUsername("Pippo2");
        mockGiocatore2.setNome("Pippo2");
        mockGiocatore2.setCognome("Pippo2");
        mockGiocatore2.setEmail("Pippo2@studenti.unisa.it");
        mockGiocatore2.setPassword("Pippo2");
        mockGiocatore2.setPokemons(pokemonList2);

        List<Giocatore> giocatoreList = new ArrayList<>();
        giocatoreList.add(mockGiocatore1);
        giocatoreList.add(mockGiocatore2);

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Giuseppone");
        mockOrganizzatore.setNome("Giuseppe");
        mockOrganizzatore.setCognome("one");
        mockOrganizzatore.setEmail("Giuseppone@studenti.unisa.it");
        mockOrganizzatore.setPassword("Giuseppone");

        Partita mockPartita = new Partita();
        mockPartita.setGiocatoreList(giocatoreList);
        mockPartita.setIdVincitore(1);

        List<Partita> partite = new ArrayList<>();
        partite.add(mockPartita);
        partite.add(mockPartita);

        Torneo mockTorneo = new Torneo();
        mockTorneo.setNome("TorneoUnisa");
        mockTorneo.setData(LocalDate.parse("2099-01-27"));
        mockTorneo.setRegole("Solo pokemon della regione di Kanto");
        mockTorneo.setPremi("Una pacca sulla spalla");
        mockTorneo.setOrganizzazione("TorneiForLife");
        mockTorneo.setCapienza(16);
        mockTorneo.setOrganizzatore(mockOrganizzatore);
        mockTorneo.setStatoTorneo(StatoTorneo.INCORSO);
        mockTorneo.setPartite(partite);


        when(torneoRepository.save(any(Torneo.class))).thenReturn(mockTorneo);

        when(partitaRepository.save(any(Partita.class))).thenReturn(mockPartita);

        String result = String.valueOf(partitaService.aggiungereRisultato(1, mockPartita, mockTorneo, mockOrganizzatore));

        verify(partitaRepository, times(1)).save(mockPartita);

        assertTrue(result.equals("true"));

    }
}




