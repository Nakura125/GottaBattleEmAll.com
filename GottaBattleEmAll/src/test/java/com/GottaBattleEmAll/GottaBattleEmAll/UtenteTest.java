package com.GottaBattleEmAll.GottaBattleEmAll;

import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Utente;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.UtenteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UtenteTest {

    @InjectMocks
    private UtenteServiceImpl utenteService;

    @Mock
    private GiocatoreRepository giocatoreRepository;
    @Mock
    private OrganizzatoreRepository organizzatoreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(organizzatoreRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    public void testLogin() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Ugo_Ferrari");
        inputUtente.setPassword("password1243");
        String ruolo = "organizzatore";


        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo_Ferrari");
        mockOrganizzatore.setPassword("password1243");
        mockOrganizzatore.setStato(Stato.ATTIVO);

        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("login effettuato", result);
    }

    @Test
    public void testLoginCredenzialiSbagliate1() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Ugo_Ferrar");
        inputUtente.setPassword("password1243");
        String ruolo = "organizzatore";

        when(organizzatoreRepository.findByUsername("Ugo_Ferrar")).thenReturn(null);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("credenziali sbagliate o non presenti", result);
    }

    @Test
    public void testLoginCredenzialiSbagliate2() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Ugo_Ferrari");
        inputUtente.setPassword("password124");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo_Ferrari");
        mockOrganizzatore.setPassword("password1243");

        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("credenziali sbagliate o non presenti", result);
    }

    @Test
    public void testLoginAccountBannato() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("CristianCarmineEsposito");
        inputUtente.setPassword("password20");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("CristianCarmineEsposito");
        mockOrganizzatore.setPassword("password20");
        mockOrganizzatore.setStato(Stato.BANNATO);

        when(organizzatoreRepository.findByUsername("CristianCarmineEsposito")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("account bannato", result);
    }

    @Test
    public void testLoginAccountAncoraNonAccettato() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("SalvatoreLaTorre");
        inputUtente.setPassword("password14");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("SalvatoreLaTorre");
        mockOrganizzatore.setPassword("password14");
        mockOrganizzatore.setStato(Stato.INVERIFICA);

        when(organizzatoreRepository.findByUsername("SalvatoreLaTorre")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("account ancora non accettato", result);
    }

    @Test
    public void testModificaProfilo() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Blanco01");
        inputUtente.setPassword("Mario01");
        inputUtente.setNome("Giovanni");
        inputUtente.setCognome("Blanco");
        inputUtente.setEmail("Bianco01@gmail.com");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco";

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Blanco");
        mockGiocatore.setPassword("Mario01");
        mockGiocatore.setNome("Giovanni");
        mockGiocatore.setCognome("Blanco");
        mockGiocatore.setEmail("Bianco01@gmail.com");

        when(giocatoreRepository.findByUsername("Blanco01")).thenReturn(null);
        when(giocatoreRepository.findByUsername(username)).thenReturn(mockGiocatore);

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(1)).save(mockGiocatore);

        assertEquals("modifica effettuata", result);
    }

    @Test
    public void testModificaProfiloUsernameGiàEsistente() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Blanco");
        inputUtente.setPassword("Mario01");
        inputUtente.setNome("Giovanni");
        inputUtente.setCognome("Blanco");
        inputUtente.setEmail("Blanco01@gmail.com");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco01";

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Blanco01");
        mockGiocatore.setPassword("Mario01");
        mockGiocatore.setNome("Giovanni");
        mockGiocatore.setCognome("Blanco");
        mockGiocatore.setEmail("Blanco01@gmail.com");

        Giocatore mockGiocatoreGiàEsistente = new Giocatore();
        mockGiocatoreGiàEsistente.setUsername("Blanco");

        when(giocatoreRepository.findByUsername("Blanco")).thenReturn(mockGiocatoreGiàEsistente);
        when(giocatoreRepository.findByUsername(username)).thenReturn(mockGiocatore);


        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(0)).save(mockGiocatore);

        assertEquals("username già esistente", result);
    }


    @Test
    public void testModificaProfiloEmailNonValida() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Blanco");
        inputUtente.setPassword("Mario01");
        inputUtente.setNome("Giovanni");
        inputUtente.setCognome("Blanco");
        inputUtente.setEmail("Bianco01");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco";

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Blanco");
        mockGiocatore.setPassword("Mario01");
        mockGiocatore.setNome("Giovanni");
        mockGiocatore.setCognome("Blanco");
        mockGiocatore.setEmail("Bianco01@gmail.com");

        when(giocatoreRepository.findByUsername("Blanco")).thenReturn(mockGiocatore);

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(0)).save(mockGiocatore);

        assertEquals("email non valida", result);
    }

    @Test
    public void testModificaProfiloPasswordNonCorrispondono() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Blanco01");
        inputUtente.setPassword("Mario01");
        inputUtente.setNome("Giovanni");
        inputUtente.setCognome("Blanco");
        inputUtente.setEmail("Bianco01@gmail.com");
        String confermaPassword = "Mario0";
        String ruolo = "giocatore";
        String username = "Blanco01";

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Blanco01");
        mockGiocatore.setPassword("Mario01");
        mockGiocatore.setNome("Giovanni");
        mockGiocatore.setCognome("Blanco");
        mockGiocatore.setEmail("Bianco01@gmail.com");

        when(giocatoreRepository.findByUsername("Blanco01")).thenReturn(mockGiocatore);

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(0)).save(mockGiocatore);

        assertEquals("password non corrispondono", result);
    }

}
