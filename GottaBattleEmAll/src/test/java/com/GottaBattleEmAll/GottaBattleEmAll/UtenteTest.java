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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UtenteTest {

    @InjectMocks
    private UtenteServiceImpl utenteService;

    @Mock
    private GiocatoreRepository giocatoreRepository;
    @Mock
    private OrganizzatoreRepository organizzatoreRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(organizzatoreRepository.findByUsername(anyString())).thenReturn(null);
    }

    @Test
    public void testLogin() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Ugo_Ferrari");
        when(inputUtente.getPassword()).thenReturn("password1243");
        String ruolo = "organizzatore";


        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo_Ferrari");
        when(mockOrganizzatore.getPassword()).thenReturn("password1243");
        when(mockOrganizzatore.getStato()).thenReturn(Stato.ATTIVO);

        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockOrganizzatore);
        when(passwordEncoder.matches("password1243", mockOrganizzatore.getPassword())).thenReturn(true);


        String result = utenteService.login(inputUtente, ruolo);


        assertEquals("login effettuato", result);
    }

    @Test
    public void testLoginCredenzialiSbagliate1() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Ugo_Ferrar");
        when(inputUtente.getPassword()).thenReturn("password1243");
        String ruolo = "organizzatore";

        when(organizzatoreRepository.findByUsername("Ugo_Ferrar")).thenReturn(null);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("credenziali sbagliate o non presenti", result);
    }

    @Test
    public void testLoginCredenzialiSbagliate2() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Ugo_Ferrari");
        when(inputUtente.getPassword()).thenReturn("password124");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo_Ferrari");
        when(mockOrganizzatore.getPassword()).thenReturn("password1243");

        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("credenziali sbagliate o non presenti", result);
    }

    @Test
    public void testLoginAccountBannato() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("CristianCarmineEsposito");
        when(inputUtente.getPassword()).thenReturn("password20");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("CristianCarmineEsposito");
        when(mockOrganizzatore.getPassword()).thenReturn("password20");
        when(mockOrganizzatore.getStato()).thenReturn(Stato.BANNATO);

        when(organizzatoreRepository.findByUsername("CristianCarmineEsposito")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("account bannato", result);
    }

    @Test
    public void testLoginAccountAncoraNonAccettato() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("SalvatoreLaTorre");
        when(inputUtente.getPassword()).thenReturn("password14");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("SalvatoreLaTorre");
        when(mockOrganizzatore.getPassword()).thenReturn("password14");
        when(mockOrganizzatore.getStato()).thenReturn(Stato.INVERIFICA);

        when(organizzatoreRepository.findByUsername("SalvatoreLaTorre")).thenReturn(mockOrganizzatore);

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("account ancora non accettato", result);
    }

    @Test
    public void testModificaProfilo() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Blanco01");
        when(inputUtente.getPassword()).thenReturn("Mario01");
        when(inputUtente.getNome()).thenReturn("Giovanni");
        when(inputUtente.getCognome()).thenReturn("Blanco");
        when(inputUtente.getEmail()).thenReturn("Bianco01@gmail.com");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco";

        Giocatore mockGiocatore = mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Blanco");
        when(mockGiocatore.getPassword()).thenReturn("Mario01");
        when(mockGiocatore.getNome()).thenReturn("Giovanni");
        when(mockGiocatore.getCognome()).thenReturn("Blanco");
        when(mockGiocatore.getEmail()).thenReturn("Bianco01@gmail.com");

        when(giocatoreRepository.findByUsername("Blanco01")).thenReturn(null);
        when(giocatoreRepository.findByUsername(username)).thenReturn(mockGiocatore);

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(1)).save(mockGiocatore);

        assertEquals("modifica effettuata", result);
    }

    @Test
    public void testModificaProfiloUsernameGiàEsistente() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Blanco");
        when(inputUtente.getPassword()).thenReturn("Mario01");
        when(inputUtente.getNome()).thenReturn("Giovanni");
        when(inputUtente.getCognome()).thenReturn("Blanco");
        when(inputUtente.getEmail()).thenReturn("Blanco01@gmail.com");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco01";

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Blanco01");
        when(mockGiocatore.getPassword()).thenReturn("Mario01");
        when(mockGiocatore.getNome()).thenReturn("Giovanni");
        when(mockGiocatore.getCognome()).thenReturn("Blanco");
        when(mockGiocatore.getEmail()).thenReturn("Blanco01@gmail.com");


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
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Blanco");
        when(inputUtente.getPassword()).thenReturn("Mario01");
        when(inputUtente.getNome()).thenReturn("Giovanni");
        when(inputUtente.getCognome()).thenReturn("Blanco");
        when(inputUtente.getEmail()).thenReturn("Bianco01");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco";

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Blanco");
        when(mockGiocatore.getPassword()).thenReturn("Mario01");
        when(mockGiocatore.getNome()).thenReturn("Giovanni");
        when(mockGiocatore.getCognome()).thenReturn("Blanco");
        when(mockGiocatore.getEmail()).thenReturn("Bianco01@gmail.com");


        when(giocatoreRepository.findByUsername("Blanco")).thenReturn(mockGiocatore);

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(0)).save(mockGiocatore);

        assertEquals("email non valida", result);
    }

    @Test
    public void testModificaProfiloPasswordNonCorrispondono() {
        Utente inputUtente = Mockito.mock(Utente.class);
        when(inputUtente.getUsername()).thenReturn("Blanco01");
        when(inputUtente.getPassword()).thenReturn("Mario01");
        when(inputUtente.getNome()).thenReturn("Giovanni");
        when(inputUtente.getCognome()).thenReturn("Blanco");
        when(inputUtente.getEmail()).thenReturn("Bianco01@gmail.com");
        String confermaPassword = "Mario0";
        String ruolo = "giocatore";
        String username = "Blanco01";

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Blanco01");
        when(mockGiocatore.getPassword()).thenReturn("Mario01");
        when(mockGiocatore.getNome()).thenReturn("Giovanni");
        when(mockGiocatore.getCognome()).thenReturn("Blanco");
        when(mockGiocatore.getEmail()).thenReturn("Bianco01@gmail.com");

        when(giocatoreRepository.findByUsername("Blanco01")).thenReturn(mockGiocatore);

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        verify(giocatoreRepository, times(0)).save(mockGiocatore);

        assertEquals("password non corrispondono", result);
    }

}
