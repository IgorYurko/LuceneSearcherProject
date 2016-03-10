package com.spdukraine.testtask.search.controllers;

import com.spdukraine.testtask.search.components.FormComponentSearch;
import com.spdukraine.testtask.search.components.FormComponentSwitcherSearch;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.services.intrf.SearchService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Controller
@SessionAttributes("alphabetic")
public class SearchController extends AbstractController
{
    public static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    SearchService sService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String searchIndex(Model model)
    {
        LOGGER.info("searchGet()");
        model.addAttribute("formComponent", sService.getFormComponent());

        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchPost(@Valid @ModelAttribute("formComponent") FormComponentSearch sFormComponent,
                             BindingResult bindingResult,
                             @ModelAttribute("alphabetic") Boolean alphabetic,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request) throws ParseException, DirectoryNotCreatedException, IOException, InvalidTokenOffsetsException
    {
        if (bindingResult.hasErrors())
        {
            List<String> errors = getErrors(bindingResult);
            redirectAttributes.addFlashAttribute("errors", errors);

            return "redirect:" + request.getHeader("referer")
                    .replace(request.getHeader("origin"), "");
        }
        LOGGER.info("searchPost()");

        Cookie cookie = new Cookie("query", URLEncoder.encode(sFormComponent.getQ(), "UTF-8"));
        cookie.setMaxAge(LuceneBuilder.COOKIE_SET_MAX_AGE);
        redirectAttributes.addFlashAttribute("redirectCookie", cookie);

        return "redirect:/search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchGet(@ModelAttribute("alphabetic") Boolean alphabetic,
                            @CookieValue(value = "query", defaultValue = "", required = false)
                            String query, HttpServletRequest request,
                            HttpServletResponse response, Model model) throws
            ParseException,
            DirectoryNotCreatedException,
            IOException, InvalidTokenOffsetsException
    {
        if (model.containsAttribute("redirectCookie"))
        {
            Cookie redirectCookie = (Cookie) model.asMap().getOrDefault("redirectCookie", "");
            String decodeCookieValue = URLDecoder.decode(redirectCookie.getValue(), "UTF-8");
            response.addCookie(redirectCookie);

            model.addAttribute("results", sService.searchByWords(decodeCookieValue, 0, alphabetic));
            sService.fillModel(model, request);

            return "result";
        }
        if (query.trim().isEmpty())
        {
            sService.fillModel(model, request);

            return "result";
        }

        model.addAttribute("results", sService.searchByWords(query, 0, alphabetic));
        sService.fillModel(model, request);

        return "result";
    }

    @RequestMapping(value = "/search/switch", method = RequestMethod.POST)
    public String alphabeticSwitchPost(@ModelAttribute("switcher") FormComponentSwitcherSearch swFormComponent,
                                       RedirectAttributes attributes)
    {
        LOGGER.info("alphabeticSwitchPost()");
        attributes.addFlashAttribute("alphabetic",
                Boolean.valueOf(swFormComponent.getSwitcher()));

        return "redirect:/search";
    }
}
