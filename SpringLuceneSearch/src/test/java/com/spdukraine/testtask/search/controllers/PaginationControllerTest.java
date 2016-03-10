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

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testServlet.xml")
@WebAppConfiguration
public class PaginationControllerTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPaginationGet_Bad_value() throws Exception
    {
        mvc.perform(get("/search/{page}", "word").contentType(MediaType.TEXT_HTML)
            .cookie(new Cookie("query", "some word"))
        )
                .andExpect(status().is4xxClientError());

        LOGGER.info("It's not o'k!!!");
    }

    @Test
    public void testPaginationGet_Good_value() throws Exception
    {
        mvc.perform(get("/search/{page}", 2).contentType(MediaType.TEXT_HTML)
                .cookie(new Cookie("query", "some word"))
        )
                .andExpect(status().isOk());

        LOGGER.info("It's o'k!!!");
    }


    @Test
    public void testSearchRest() throws Exception
    {

    }
}