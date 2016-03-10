package com.spdukraine.testtask.search.controllers;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testServlet.xml")
@WebAppConfiguration
public class SearchControllerTest
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
    public void testSearchIndex() throws Exception
    {
        mvc.perform(get("/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("formComponent"))
                .andExpect(model().attributeExists("alphabetic"))
                .andExpect(model().attribute("alphabetic", LuceneBuilder.SORT_ALPHABETIC))
                .andExpect(model().size(2))
                .andExpect(view().name("search"))
                .andExpect(forwardedUrl("/WEB-INF/views/test/jsp/search.jsp"));

        LOGGER.info("It's o'k!!!");
    }

    @Test
    public void testSearchPost() throws Exception
    {
        mvc.perform(post("/search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("q", "s")
        )
                .andExpect(flash().attributeExists("redirectCookie"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/search"));

        LOGGER.info("It's o'k!!!");
    }

    @Test
    public void testSearchGet_CookieValue_is_missing() throws Exception
    {
        mvc.perform(get("/search").contentType(MediaType.TEXT_HTML)
                .cookie(new Cookie("query", ""))
                .sessionAttr("alphabetic", false)
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("request"))
                .andExpect(model().attributeExists("alphabetic"))
                .andExpect(model().attributeExists("formComponent"))
                .andExpect(model().attribute("alphabetic", LuceneBuilder.SORT_ALPHABETIC))
                .andExpect(model().size(5))
                .andExpect(view().name("result"))
                .andExpect(forwardedUrl("/WEB-INF/views/test/jsp/result.jsp"));

        LOGGER.info("It's o'k!!!");
    }

    @Test
    public void testSearchGet_CookieValue_is_present() throws Exception
    {
        mvc.perform(get("/search").contentType(MediaType.TEXT_HTML)
                .cookie(new Cookie("query", "some word"))
                .sessionAttr("alphabetic", false)
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("results"))
                .andExpect(model().size(6))
                .andExpect(view().name("result"))
                .andExpect(forwardedUrl("/WEB-INF/views/test/jsp/result.jsp"));

        LOGGER.info("It's o'k!!!");
    }

    @Test
    public void testSearchGet_Redirect_attribute() throws Exception
    {
        mvc.perform(get("/search").contentType(MediaType.TEXT_HTML)
                .flashAttr("redirectCookie", new Cookie("query", "some word"))
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("results"))
                .andExpect(model().attributeExists("redirectCookie"))
                .andExpect(model().size(7))
                .andExpect(view().name("result"))
                .andExpect(forwardedUrl("/WEB-INF/views/test/jsp/result.jsp"));

        LOGGER.info("It's o'k!!!");
    }

    @Test
    public void testAlphabeticSwitchPost() throws Exception
    {
        mvc.perform(post("/search/switch").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("switcher", "true")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("alphabetic"))
                .andExpect(flash().attribute("alphabetic", true))
                .andExpect(redirectedUrl("/search"));

        LOGGER.info("It's o'k!!!");
    }
}