package com.GottaBattleEmAll.GottaBattleEmAll;


import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ModeratoreIntegrationTest {

    @Autowired
    private ModeratoreServiceImpl moderatoreService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModeratoreRepository moderatoreRepository;

    @Autowired
    private GiocatoreRepository giocatoreRepository;
    @Autowired
    private OrganizzatoreRepository organizzatoreRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testAutenticazione() {



        Moderatore inputModeratore = new Moderatore();
        inputModeratore.setUsername("admin");
        inputModeratore.setPassword("admin");

        String result = moderatoreService.login(inputModeratore);

        assertEquals("Login effettuato", result);
    }

    @Test
    public void testCredenzialiSbagliateoNonPresenti1() {

        Moderatore inputModeratore = new Moderatore();
        inputModeratore.setUsername("nonEsistente");
        inputModeratore.setPassword("invalidPassword");

        String result = moderatoreService.login(inputModeratore);

        assertEquals("Username o password non corretti", result);
    }

    @Test
    public void testCredenzialiSbagliateoNonPresenti2() {

        Moderatore inputModeratore = new Moderatore();
        inputModeratore.setUsername("admin");
        inputModeratore.setPassword("pisello");

        String result = moderatoreService.login(inputModeratore);

        assertEquals("Username o password non corretti", result);
    }


    @Test
    public void testBannare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("admin");


        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore1");
        mockGiocatore.setStato(Stato.ATTIVO);


        boolean result = moderatoreService.bannare(mockModeratore, mockGiocatore, "giocatore");


        assertTrue(result);


    }


    @Test
    public void testSbannare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("admin");

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("giocatore123");
        mockGiocatore.setStato(Stato.BANNATO);

        boolean result = moderatoreService.sbannare(mockModeratore, mockGiocatore, "giocatore");



        assertTrue(result);


    }

    @Test
    public void testAccettare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("admin");

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore1");
        mockOrganizzatore.setStato(Stato.INVERIFICA);

        boolean result = moderatoreService.accettare(mockModeratore, mockOrganizzatore);

        assertTrue(result);



    }

    @Test
    public void testRifiutare() {

        Moderatore mockModeratore = new Moderatore();
        mockModeratore.setUsername("admin");

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("organizzatore2");
        mockOrganizzatore.setStato(Stato.INVERIFICA);

        boolean result = moderatoreService.rifiutare(mockModeratore, mockOrganizzatore);

        assertTrue(result);



    }

}
