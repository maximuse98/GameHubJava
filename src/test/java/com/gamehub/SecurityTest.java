package com.gamehub;

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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Database must have user:
 *     login = TEST_LOGIN value
 *     password = any
 *     role = 'ROLE_USER'
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/spring-security-config.xml"})
@WebAppConfiguration
public class SecurityTest {
    private UserDetailsService userDetailsService;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private final String TEST_LOGIN = "example";

    @Autowired
    public void prepare(UserDetailsService detailsService){
        this.userDetailsService = detailsService;
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testAccess() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_LOGIN);

        this.mockMvc.perform(get("/games").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testInvalidAccess() throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_LOGIN);

        this.mockMvc.perform(get("/admin").with(user(userDetails)))
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
