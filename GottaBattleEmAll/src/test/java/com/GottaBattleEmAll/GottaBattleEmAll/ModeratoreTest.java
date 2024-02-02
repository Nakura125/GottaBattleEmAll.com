package com.GottaBattleEmAll.GottaBattleEmAll;


import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.Mockito.*;

public class ModeratoreTest {

    @InjectMocks
    private ModeratoreServiceImpl moderatoreService;
    @Mock
    private ModeratoreRepository moderatoreRepository;

    @Mock
    private GiocatoreRepository giocatoreRepository;
    @Mock
    private OrganizzatoreRepository organizzatoreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(moderatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(organizzatoreRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    public void testAutenticazione() {
        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("testUser");//usare querlli del TCS
        mockModeratore.setPassword("testPassword");

        when(moderatoreRepository.findByUsername("testUser")).thenReturn(mockModeratore);

        Moderatore inputModeratore = new Moderatore();
        inputModeratore.setUsername("testUser");
        inputModeratore.setPassword("testPassword");

        String result = moderatoreService.login(inputModeratore);

        assertEquals("Login effettuato", result);
    }

    @Test
    public void testCredenzialiSbagliateoNonPresenti1() {
        when(moderatoreRepository.findByUsername("nonEsistente")).thenReturn(null);//usare querlli del TCS

        Moderatore inputModeratore = new Moderatore();
        inputModeratore.setUsername("nonEsistente");
        inputModeratore.setPassword("invalidPassword");

        String result = moderatoreService.login(inputModeratore);

        assertEquals("Username o password non corretti", result);
    }

    @Test
    public void testCredenzialiSbagliateoNonPresenti2() {
        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("testUser");//usare querlli del TCS
        mockModeratore.setPassword("testPassword");

        when(moderatoreRepository.findByUsername("testUser")).thenReturn(mockModeratore);

        Moderatore inputModeratore = new Moderatore();
        inputModeratore.setUsername("testUser");
        inputModeratore.setPassword("pisello");

        String result = moderatoreService.login(inputModeratore);

        assertEquals("Username o password non corretti", result);
    }


    @Test
    public void testBannare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("testModeratore");
        when(moderatoreRepository.findByUsername("testModeratore")).thenReturn(mockModeratore);


        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Il_demone");
        mockGiocatore.setStato(Stato.ATTIVO);
        when(giocatoreRepository.findByUsername("Il_demone")).thenReturn(mockGiocatore);


        boolean result = moderatoreService.bannare(mockModeratore, mockGiocatore, "giocatore");

        assertTrue(result);

        assertEquals(Stato.BANNATO, mockGiocatore.getStato());

        verify(giocatoreRepository, times(1)).save(mockGiocatore);
    }


    @Test
    public void testSbannare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("testModeratore");
        when(moderatoreRepository.findByUsername("testModeratore")).thenReturn(mockModeratore);

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Gianni_dAngelo");
        mockGiocatore.setStato(Stato.BANNATO);
        when(giocatoreRepository.findByUsername("Gianni_dAngelo")).thenReturn(mockGiocatore);

        boolean result = moderatoreService.sbannare(mockModeratore, mockGiocatore, "giocatore");

        assertTrue(result);

        assertEquals(Stato.ATTIVO, mockGiocatore.getStato());

        verify(giocatoreRepository, times(1)).save(mockGiocatore);
    }

    @Test
    public void testAccettare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("testModeratore");
        when(moderatoreRepository.findByUsername("testModeratore")).thenReturn(mockModeratore);

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("UgoVaccaro");
        mockOrganizzatore.setStato(Stato.INVERIFICA);
        when(organizzatoreRepository.findByUsername("UgoVaccaro")).thenReturn(mockOrganizzatore);

        boolean result = moderatoreService.accettare(mockModeratore, mockOrganizzatore);

        assertTrue(result);

        assertEquals(Stato.ATTIVO, mockOrganizzatore.getStato());

        verify(organizzatoreRepository, times(1)).save(mockOrganizzatore);

    }

    @Test
    public void testRifiutare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("testModeratore");
        when(moderatoreRepository.findByUsername("testModeratore")).thenReturn(mockModeratore);

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo_Ferrari");
        mockOrganizzatore.setStato(Stato.INVERIFICA);
        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockOrganizzatore);

        boolean result = moderatoreService.rifiutare(mockModeratore, mockOrganizzatore);

        assertTrue(result);

        assertEquals(Stato.RIFIUTATO, mockOrganizzatore.getStato());

        verify(organizzatoreRepository, times(1)).save(mockOrganizzatore);

    }

}
