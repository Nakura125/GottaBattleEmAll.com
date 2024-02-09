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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UtenteIntegrationTest {

    @Autowired
    private UtenteServiceImpl utenteService;

    @Autowired
    private GiocatoreRepository giocatoreRepository;
    @Autowired
    private OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testLogin() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("giocatore0");
        inputUtente.setPassword("giocatore0");
        String ruolo = "giocatore";


        String result = utenteService.login(inputUtente, ruolo);


        assertEquals("login effettuato", result);
    }

    @Test
    public void testLoginCredenzialiSbagliate1() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Ugo_Ferrar");
        inputUtente.setPassword("password1243");
        String ruolo = "organizzatore";


        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("credenziali sbagliate o non presenti", result);
    }

    @Test
    public void testLoginCredenzialiSbagliate2() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("giocatore0");
        inputUtente.setPassword("password124");
        String ruolo = "giocatore";



        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("credenziali sbagliate o non presenti", result);
    }

    @Test
    public void testLoginAccountBannato() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("giocatore123");
        inputUtente.setPassword("giocatore");
        String ruolo = "giocatore";

        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("account bannato", result);
    }

    @Test
    public void testLoginAccountAncoraNonAccettato() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("organizzatore1");
        inputUtente.setPassword("organizzatore1");
        String ruolo = "organizzatore";

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("SalvatoreLaTorre");
        mockOrganizzatore.setPassword("password14");
        mockOrganizzatore.setStato(Stato.INVERIFICA);


        String result = utenteService.login(inputUtente, ruolo);

        assertEquals("account ancora non accettato", result);
    }

    @Test
    public void testModificaProfilo() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("Blanco");
        inputUtente.setPassword("giocatore0");
        inputUtente.setNome("giocatore0");
        inputUtente.setCognome("giocatore0");
        inputUtente.setEmail("giocatore0@gmail.com");
        String confermaPassword = "giocatore0";
        String ruolo = "giocatore";
        String username = "giocatore0";


        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        assertEquals("modifica effettuata", result);
    }

    @Test
    public void testModificaProfiloUsernameGiàEsistente() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("giocatore1");
        inputUtente.setPassword("giocatore1");
        inputUtente.setNome("giocatore1");
        inputUtente.setCognome("giocatore1");
        inputUtente.setEmail("giocatore1@gmail.com");
        String confermaPassword = "giocatore1";
        String ruolo = "giocatore";
        String username = "giocatore2";




        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        assertEquals("username già esistente", result);
    }


    @Test
    public void testModificaProfiloEmailNonValida() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("giocatore0");
        inputUtente.setPassword("Mario01");
        inputUtente.setNome("Giovanni");
        inputUtente.setCognome("Blanco");
        inputUtente.setEmail("Bianco01");
        String confermaPassword = "Mario01";
        String ruolo = "giocatore";
        String username = "Blanco";



        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);

        assertEquals("email non valida", result);
    }

    @Test
    public void testModificaProfiloPasswordNonCorrispondono() {
        Utente inputUtente = new Utente();
        inputUtente.setUsername("giocatore0");
        inputUtente.setPassword("Mario01");
        inputUtente.setNome("Giovanni");
        inputUtente.setCognome("Blanco");
        inputUtente.setEmail("Bianco01@gmail.com");
        String confermaPassword = "Mario0";
        String ruolo = "giocatore";
        String username = "Blanco01";

        String result = utenteService.modificaProfilo(inputUtente, confermaPassword, ruolo, username);
        assertEquals("password non corrispondono", result);
    }

}
