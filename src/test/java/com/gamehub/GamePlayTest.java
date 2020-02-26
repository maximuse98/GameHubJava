package com.gamehub;

import com.gamehub.dao.GameDAO;
import com.gamehub.entity.GameEntity;
import com.gamehub.service.SessionService;
import com.gamehub.view.SessionView;
import com.gamehub.view.View;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/spring-security-config.xml"})
@WebAppConfiguration
public class GamePlayTest {
    private UserDetailsService userDetailsService;
    private MockMvc mockMvc;
    private GameDAO gameDAO;
    private SessionService sessionService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    public void prepare(GameDAO gameDAO, UserDetailsService userDetailsService, SessionService sessionService){
        this.gameDAO = gameDAO;
        this.userDetailsService = userDetailsService;
        this.sessionService = sessionService;
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    public void createSessionAndConnect() throws Exception {
        String TEST_LOGIN_1 = "example";
        UserDetails user1 = userDetailsService.loadUserByUsername(TEST_LOGIN_1);

        String TEST_LOGIN_2 = "example10";
        UserDetails user2 = userDetailsService.loadUserByUsername(TEST_LOGIN_2);

        List<GameEntity> gameEntities = gameDAO.listGames();
        GameEntity gameEntity = gameEntities.get(0);

        this.mockMvc.perform(get("/start/"+gameEntity.getId()).with(user(user1)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        List<View> sessionViews = sessionService.getSessionViews();
        SessionView view = (SessionView) sessionViews.get(0);

        this.mockMvc.perform(get("/connect/"+view.getId()).with(user(user2)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/game"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
