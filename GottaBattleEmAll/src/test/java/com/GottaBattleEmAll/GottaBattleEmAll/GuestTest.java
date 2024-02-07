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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GuestTest {

    @InjectMocks
    private GuestServiceImpl guestService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private GiocatoreRepository giocatoreRepository;
    @Mock
    private OrganizzatoreRepository organizzatoreRepository;
    @Mock
    private RichiestaRepository richiestaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(giocatoreRepository.findByUsername(anyString())).thenReturn(null);
        when(organizzatoreRepository.findByUsername(anyString())).thenReturn(null);


        // Configura il comportamento del mock passwordEncoder
        when(passwordEncoder.encode(any(CharSequence.class))).thenAnswer(invocation -> {
            CharSequence rawPassword = invocation.getArgument(0);
            return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
        });
    }

    @Test
    public void testRegistrazioneGiocatore() {

        Giocatore inputGiocatore = new Giocatore();
        inputGiocatore.setUsername("Ugo_Bronte");
        inputGiocatore.setNome("Ugo");
        inputGiocatore.setCognome("Bronte");
        inputGiocatore.setEmail("Ugo.bronte69@studenti.unisa.it");
        inputGiocatore.setPassword("password1243");

        when(giocatoreRepository.findByUsername("Ugo_Bronte")).thenReturn(null);

        String result = guestService.registrazioneGiocatore(inputGiocatore, "password1243");

        ArgumentCaptor<Giocatore> argument = ArgumentCaptor.forClass(Giocatore.class);
        verify(giocatoreRepository).save(argument.capture());
        assertTrue(BCrypt.checkpw("password1243", argument.getValue().getPassword()));

        assertEquals(Stato.ATTIVO, inputGiocatore.getStato());


        assertEquals("registrazione avvenuta con successo", result);
    }

    @Test
    public void testRegistrazioneGiocatoreUsernameGiàEsistente() {

        Giocatore mockGiocatore = new Giocatore();
        mockGiocatore.setUsername("Ugo_Vaccaro");

        Giocatore inputGiocatore = new Giocatore();
        inputGiocatore.setUsername("Ugo_Vaccaro");
        inputGiocatore.setNome("Ugo");
        inputGiocatore.setCognome("Vaccaro");
        inputGiocatore.setEmail("Ugo.vaccaro@studenti.unica.it");
        inputGiocatore.setPassword("dijkstra12");

        when(giocatoreRepository.findByUsername("Ugo_Vaccaro")).thenReturn(mockGiocatore);

        String result = guestService.registrazioneGiocatore(inputGiocatore, "dijkstra12");

        assertNull(inputGiocatore.getStato());

        verify(giocatoreRepository, times(0)).save(inputGiocatore);

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

        when(giocatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(null);

        String result = guestService.registrazioneGiocatore(inputGiocatore, "password1243");

        assertNull(inputGiocatore.getStato());

        verify(giocatoreRepository, times(0)).save(inputGiocatore);

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

        when(giocatoreRepository.findByUsername("Annalisa_DeBonis")).thenReturn(null);

        String result = guestService.registrazioneGiocatore(inputGiocatore, "password17");

        assertNull(inputGiocatore.getStato());

        verify(giocatoreRepository, times(0)).save(inputGiocatore);

        assertEquals("password non corrispondono", result);

    }


    //Ri-Testare tutto registrazione siccome è stata aggiunta richiesta e hashPassword
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



        when(organizzatoreRepository.findByUsername("Ugo_Bronte")).thenReturn(null);

        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password1243");

        ArgumentCaptor<Organizzatore> argument = ArgumentCaptor.forClass(Organizzatore.class);
        verify(organizzatoreRepository).save(argument.capture());
        assertTrue(BCrypt.checkpw("password1243", argument.getValue().getPassword()));

        assertEquals(Stato.INVERIFICA, inputOrganizzatore.getStato());

        assertEquals("richiesta di registrazione inviata con successo", result);
    }

    @Test
    public void testRegistrazioneOrganizzatoreUsernameGiàEsistente() {

        Organizzatore mockOrganizzatore = new Organizzatore();
        mockOrganizzatore.setUsername("Ugo_Vaccaro");

        Richiesta mockRichiesta = new Richiesta();
        mockRichiesta.setOrganizzatore(mockOrganizzatore);

        Organizzatore inputOrganizzatore = new Organizzatore();
        inputOrganizzatore.setUsername("Ugo_Vaccaro");
        inputOrganizzatore.setNome("Ugo");
        inputOrganizzatore.setCognome("Vaccaro");
        inputOrganizzatore.setEmail("Ugo.vaccaro@studenti.unica.it");
        inputOrganizzatore.setPassword("dijsktra12");

        when(organizzatoreRepository.findByUsername("Ugo_Vaccaro")).thenReturn(mockOrganizzatore);

        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "dijsktra12");

        assertNull(inputOrganizzatore.getStato());

        verify(organizzatoreRepository, times(0)).save(inputOrganizzatore);

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

        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(null);

        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password1243");

        assertNull(inputOrganizzatore.getStato());

        verify(organizzatoreRepository, times(0)).save(inputOrganizzatore);

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

        when(organizzatoreRepository.findByUsername("Annalisa_DeBonis")).thenReturn(null);

        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password17");

        assertNull(inputOrganizzatore.getStato());

        verify(organizzatoreRepository, times(0)).save(inputOrganizzatore);

        assertEquals("password non corrispondono", result);

    }


}
