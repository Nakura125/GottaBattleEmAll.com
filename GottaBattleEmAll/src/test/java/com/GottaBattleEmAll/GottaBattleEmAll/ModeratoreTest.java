package com.GottaBattleEmAll.GottaBattleEmAll;


import com.GottaBattleEmAll.GottaBattleEmAll.entity.Moderatore;
import com.GottaBattleEmAll.GottaBattleEmAll.repository.ModeratoreRepository;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreService;
import com.GottaBattleEmAll.GottaBattleEmAll.service.ModeratoreServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModeratoreTest {

    @InjectMocks
    private ModeratoreServiceImpl moderatoreService;
    @Mock
    private ModeratoreRepository moderatoreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(moderatoreRepository.findByUsername(anyString())).thenReturn(null);
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


}
