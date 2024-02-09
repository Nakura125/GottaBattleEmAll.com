package com.GottaBattleEmAll.GottaBattleEmAll;


import com.GottaBattleEmAll.GottaBattleEmAll.entity.Giocatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Organizzatore;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Richiesta;
import com.GottaBattleEmAll.GottaBattleEmAll.entity.Stato;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.RichiestaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class GuestIntegrationTest {

    @Autowired
    private GuestServiceImpl guestService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GiocatoreRepository giocatoreRepository;
    @Autowired
    private OrganizzatoreRepository organizzatoreRepository;
    @Autowired
    private RichiestaRepository richiestaRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testRegistrazioneGiocatore() {

        Giocatore inputGiocatore = new Giocatore();
        inputGiocatore.setUsername("Ugo_Bronte");
        inputGiocatore.setNome("Ugo");
        inputGiocatore.setCognome("Bronte");
        inputGiocatore.setEmail("Ugo.bronte69@studenti.unisa.it");
        inputGiocatore.setPassword("password1243");


        String result = guestService.registrazioneGiocatore(inputGiocatore, "password1243");


        assertEquals(Stato.ATTIVO, inputGiocatore.getStato());

        assertEquals("registrazione avvenuta con successo", result);
    }


    @Test
    public void testRegistrazioneGiocatoreUsernameGiàEsistente() {


        Giocatore inputGiocatore = new Giocatore();
        inputGiocatore.setUsername("giocatore1");
        inputGiocatore.setNome("Ugo");
        inputGiocatore.setCognome("Vaccaro");
        inputGiocatore.setEmail("Ugo.vaccaro@studenti.unica.it");
        inputGiocatore.setPassword("dijkstra12");


        String result = guestService.registrazioneGiocatore(inputGiocatore, "dijkstra12");

        assertNull(inputGiocatore.getStato());


        assertEquals("username già esistente", result);
    }

    @Test
    public void testRegistrazioneGiocatoreEmailNonValida() {

        Giocatore inputGiocatore = new Giocatore();
        inputGiocatore.setUsername("Ugo_Ferrari");
        inputGiocatore.setNome("Ugo");
        inputGiocatore.setCognome("Ferrari");
        inputGiocatore.setEmail("Ugo.ferrari");
        inputGiocatore.setPassword("password1243");


        String result = guestService.registrazioneGiocatore(inputGiocatore, "password1243");

        assertNull(inputGiocatore.getStato());


        assertEquals("email non valida", result);
    }


    @Test
    public void testRegistrazioneGiocatorePasswordNonCorrispondono() {

        Giocatore inputGiocatore = new Giocatore();
        inputGiocatore.setUsername("Annalisa_DeBonis");
        inputGiocatore.setNome("Annalisa");
        inputGiocatore.setCognome("DeBonis");
        inputGiocatore.setEmail("Annalisa.DeBonis18@studenti.unisa.it");
        inputGiocatore.setPassword("password18");


        String result = guestService.registrazioneGiocatore(inputGiocatore, "password17");

        assertNull(inputGiocatore.getStato());


        assertEquals("password non corrispondono", result);

    }


    @Test
    public void testRegistrazioneOrganizzatore() {

        Organizzatore inputOrganizzatore = new Organizzatore();
        inputOrganizzatore.setUsername("Ugo_Bronte");
        inputOrganizzatore.setNome("Ugo");
        inputOrganizzatore.setCognome("Bronte");
        inputOrganizzatore.setEmail("Ugo.bronte69@studenti.unica.it");
        inputOrganizzatore.setPassword("password1243");

        Richiesta mockRichiesta = new Richiesta();
        mockRichiesta.setOrganizzatore(inputOrganizzatore);


        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password1243");


        assertEquals(Stato.INVERIFICA, inputOrganizzatore.getStato());

        assertEquals("richiesta di registrazione inviata con successo", result);
    }


    @Test
    public void testRegistrazioneOrganizzatoreUsernameGiàEsistente() {



        Organizzatore inputOrganizzatore = new Organizzatore();
        inputOrganizzatore.setUsername("organizzatore");
        inputOrganizzatore.setNome("Ugo");
        inputOrganizzatore.setCognome("Vaccaro");
        inputOrganizzatore.setEmail("Ugo.vaccaro@studenti.unica.it");
        inputOrganizzatore.setPassword("dijsktra12");


        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "dijsktra12");

        assertNull(inputOrganizzatore.getStato());


        assertEquals("username già esistente", result);

    }


    @Test
    public void testRegistrazioneOrganizzatoreEmailNonValida() {

        Organizzatore inputOrganizzatore = new Organizzatore();
        inputOrganizzatore.setUsername("Ugo_Ferrari");
        inputOrganizzatore.setNome("Ugo");
        inputOrganizzatore.setCognome("Ferrari");
        inputOrganizzatore.setEmail("Ugo.ferrari");
        inputOrganizzatore.setPassword("password1243");

        Richiesta mockRichiesta = new Richiesta();
        mockRichiesta.setOrganizzatore(inputOrganizzatore);


        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password1243");

        assertNull(inputOrganizzatore.getStato());


        assertEquals("email non valida", result);
    }

    @Test
    public void testRegistrazioneOrganizzatorePasswordNonCorrispondono() {

        Organizzatore inputOrganizzatore = new Organizzatore();
        inputOrganizzatore.setUsername("Annalisa_DeBonis");
        inputOrganizzatore.setNome("Annalisa");
        inputOrganizzatore.setCognome("DeBonis");
        inputOrganizzatore.setEmail("Annalisa.DeBonis18@studenti.unisa.it");
        inputOrganizzatore.setPassword("password18");

        Richiesta mockRichiesta = new Richiesta();
        mockRichiesta.setOrganizzatore(inputOrganizzatore);


        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password17");

        assertNull(inputOrganizzatore.getStato());

        assertEquals("password non corrispondono", result);

    }

}
