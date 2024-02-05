package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Pokemon;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.GiocatoreService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.GiocatoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GiocatoreTest {

    @InjectMocks
    private GiocatoreServiceImpl giocatoreService;

    @Mock
    private GiocatoreRepository giocatoreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    public void testSostituireMembroTeam() {

        List<Pokemon> pokemonList = new ArrayList<>();

        for(int i = 0; i < 6; i++)
        {
            Pokemon pokemon = new Pokemon();
            pokemon.setNome("Ditto");
            pokemon.setId(String.valueOf(i));
            pokemonList.add(pokemon);
        }

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Ugo_Ferrari");
        mockGiocatore.setNome("Ugo");
        mockGiocatore.setCognome("Ferrari");
        mockGiocatore.setEmail("UgoFerrari69@studenti.unisa.it");
        mockGiocatore.setPassword("password1243");

        mockGiocatore.setPokemons(pokemonList);

        Pokemon pokemonNuovo = new Pokemon();
        pokemonNuovo.setNome("Charizard");
        pokemonNuovo.setId("1");

        when(giocatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockGiocatore);

        String result = giocatoreService.sostituireMembroTeam(pokemonNuovo, 1, mockGiocatore);

        verify(giocatoreRepository, times(1)).save(mockGiocatore);

        assertEquals("Pokemon sostituito con successo", result);

    }
}
