package com.spdukraine.testtask.search.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testServlet.xml")
@WebAppConfiguration
public class IndexControllerTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testIndexGet() throws Exception
    {
        mvc.perform(get("/index").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("formComponent"))
                .andExpect(model().attributeExists("depthList"))
                .andExpect(model().attributeExists("alphabetic"))
                .andExpect(model().size(3))
                .andExpect(view().name("index"))
                .andExpect(forwardedUrl("/WEB-INF/views/test/jsp/index.jsp"));

        LOGGER.info("It's o'k!!!");
    }

    @Test
    public void testIndexPost() throws Exception
    {
        mvc.perform(post("/index")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("q", "http://some.ua")
                .param("depth", "2")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        LOGGER.info("It's o'k!!!");
    }
}