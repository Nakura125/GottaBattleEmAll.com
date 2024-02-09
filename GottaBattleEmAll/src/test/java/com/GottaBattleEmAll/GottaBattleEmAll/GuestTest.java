package com.GottaBattleEmAll.GottaBattleEmAll;


import com.GottaBattleEmAll.GottaBattleEmAll.entity.*;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.GiocatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.OrganizzatoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.RichiestaRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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



        Giocatore inputGiocatore = Mockito.mock(Giocatore.class);
        when(inputGiocatore.getUsername()).thenReturn("Ugo_Bronte");
        when(inputGiocatore.getNome()).thenReturn("Ugo");
        when(inputGiocatore.getCognome()).thenReturn("Bronte");
        when(inputGiocatore.getEmail()).thenReturn("Ugo.bronte69@studenti.unisa.it");
        when(inputGiocatore.getPassword()).thenReturn("password1243");



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

        Giocatore mockGiocatore = Mockito.mock(Giocatore.class);
        when(mockGiocatore.getUsername()).thenReturn("Ugo_Vaccaro");


        Giocatore inputGiocatore = Mockito.mock(Giocatore.class);
        when(inputGiocatore.getUsername()).thenReturn("Ugo_Vaccaro");
        when(inputGiocatore.getNome()).thenReturn("Ugo");
        when(inputGiocatore.getCognome()).thenReturn("Vaccaro");
        when(inputGiocatore.getEmail()).thenReturn("Ugo.vaccaro@studenti.unica.it");
        when(inputGiocatore.getPassword()).thenReturn("dijkstra12");



        when(giocatoreRepository.findByUsername("Ugo_Vaccaro")).thenReturn(mockGiocatore);

        String result = guestService.registrazioneGiocatore(inputGiocatore, "dijkstra12");

        assertNull(inputGiocatore.getStato());

        verify(giocatoreRepository, times(0)).save(inputGiocatore);

        assertEquals("username già esistente", result);
    }

    @Test
    public void testRegistrazioneGiocatoreEmailNonValida() {

        Giocatore inputGiocatore = Mockito.mock(Giocatore.class);
        when(inputGiocatore.getUsername()).thenReturn("Ugo_Ferrari");
        when(inputGiocatore.getNome()).thenReturn("Ugo");
        when(inputGiocatore.getCognome()).thenReturn("Ferrari");
        when(inputGiocatore.getEmail()).thenReturn("Ugo.ferrari");
        when(inputGiocatore.getPassword()).thenReturn("password1243");

        when(giocatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(null);

        String result = guestService.registrazioneGiocatore(inputGiocatore, "password1243");

        assertNull(inputGiocatore.getStato());

        verify(giocatoreRepository, times(0)).save(inputGiocatore);

        assertEquals("email non valida", result);
    }

    @Test
    public void testRegistrazioneGiocatorePasswordNonCorrispondono() {

        Giocatore inputGiocatore = Mockito.mock(Giocatore.class);
        when(inputGiocatore.getUsername()).thenReturn("Annalisa_DeBonis");
        when(inputGiocatore.getNome()).thenReturn("Annalisa");
        when(inputGiocatore.getCognome()).thenReturn("DeBonis");
        when(inputGiocatore.getEmail()).thenReturn("Annalisa.DeBonis18@studenti.unisa.it");
        when(inputGiocatore.getPassword()).thenReturn("password18");

        when(giocatoreRepository.findByUsername("Annalisa_DeBonis")).thenReturn(null);


        String result = guestService.registrazioneGiocatore(inputGiocatore, "password17");

        assertNull(inputGiocatore.getStato());

        verify(giocatoreRepository, times(0)).save(inputGiocatore);

        assertEquals("password non corrispondono", result);

    }



    @Test
    public void testRegistrazioneOrganizzatore() {

        Organizzatore inputOrganizzatore = Mockito.mock(Organizzatore.class);
        when(inputOrganizzatore.getUsername()).thenReturn("Ugo_Bronte");
        when(inputOrganizzatore.getNome()).thenReturn("Ugo");
        when(inputOrganizzatore.getCognome()).thenReturn("Bronte");
        when(inputOrganizzatore.getEmail()).thenReturn("Ugo.bronte69@studenti.unica.it");
        when(inputOrganizzatore.getPassword()).thenReturn("password1243");

        Richiesta mockRichiesta = Mockito.mock(Richiesta.class);
        when(mockRichiesta.getOrganizzatore()).thenReturn(inputOrganizzatore);


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

        Organizzatore mockOrganizzatore = Mockito.mock(Organizzatore.class);
        when(mockOrganizzatore.getUsername()).thenReturn("Ugo_Vaccaro");


        Richiesta mockRichiesta = Mockito.mock(Richiesta.class);
        when(mockRichiesta.getOrganizzatore()).thenReturn(mockOrganizzatore);


        Organizzatore inputOrganizzatore = Mockito.mock(Organizzatore.class);
        when(inputOrganizzatore.getUsername()).thenReturn("Ugo_Vaccaro");
        when(inputOrganizzatore.getNome()).thenReturn("Ugo");
        when(inputOrganizzatore.getCognome()).thenReturn("Vaccaro");
        when(inputOrganizzatore.getEmail()).thenReturn("Ugo.vaccaro@studenti.unica.it");
        when(inputOrganizzatore.getPassword()).thenReturn("dijsktra12");

        when(organizzatoreRepository.findByUsername("Ugo_Vaccaro")).thenReturn(mockOrganizzatore);


        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "dijsktra12");

        assertNull(inputOrganizzatore.getStato());

        verify(organizzatoreRepository, times(0)).save(inputOrganizzatore);

        assertEquals("username già esistente", result);

    }

    @Test
    public void testRegistrazioneOrganizzatoreEmailNonValida() {

        Organizzatore inputOrganizzatore = Mockito.mock(Organizzatore.class);
        when(inputOrganizzatore.getUsername()).thenReturn("Ugo_Ferrari");
        when(inputOrganizzatore.getNome()).thenReturn("Ugo");
        when(inputOrganizzatore.getCognome()).thenReturn("Ferrari");
        when(inputOrganizzatore.getEmail()).thenReturn("Ugo.ferrari");
        when(inputOrganizzatore.getPassword()).thenReturn("password1243");

        Richiesta mockRichiesta = Mockito.mock(Richiesta.class);
        when(mockRichiesta.getOrganizzatore()).thenReturn(inputOrganizzatore);



        when(organizzatoreRepository.findByUsername("Ugo_Ferrari")).thenReturn(null);

        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password1243");

        assertNull(inputOrganizzatore.getStato());

        verify(organizzatoreRepository, times(0)).save(inputOrganizzatore);

        assertEquals("email non valida", result);
    }

    @Test
    public void testRegistrazioneOrganizzatorePasswordNonCorrispondono() {

        Organizzatore inputOrganizzatore = Mockito.mock(Organizzatore.class);
        when(inputOrganizzatore.getUsername()).thenReturn("Annalisa_DeBonis");
        when(inputOrganizzatore.getNome()).thenReturn("Annalisa");
        when(inputOrganizzatore.getCognome()).thenReturn("DeBonis");
        when(inputOrganizzatore.getEmail()).thenReturn("Annalisa.DeBonis18@studenti.unisa.it");
        when(inputOrganizzatore.getPassword()).thenReturn("password18");

        Richiesta mockRichiesta = Mockito.mock(Richiesta.class);
        when(mockRichiesta.getOrganizzatore()).thenReturn(inputOrganizzatore);



        when(organizzatoreRepository.findByUsername("Annalisa_DeBonis")).thenReturn(null);

        String result = guestService.registrazioneOrganizzatore(inputOrganizzatore, "password17");

        assertNull(inputOrganizzatore.getStato());

        verify(organizzatoreRepository, times(0)).save(inputOrganizzatore);

        assertEquals("password non corrispondono", result);

    }


}
