package com.spdukraine.testtask.search.controllers;

import com.spdukraine.testtask.search.components.RequestComponentPagination;
import com.spdukraine.testtask.search.components.RequestComponentSearch;
import com.spdukraine.testtask.search.components.ResponseComponentSearch;
import com.spdukraine.testtask.search.exceptions.BadPageValueException;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@SessionAttributes("alphabetic")
public class PaginationController extends AbstractController
{
    public static final Logger LOGGER = LoggerFactory.getLogger(PaginationController.class);

    @Autowired
    SearchService sService;

    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public String paginationGet(@PathVariable("page") RequestComponentPagination page,
                                @ModelAttribute("alphabetic") Boolean alphabetic,
                                @CookieValue(value = "query", defaultValue = "", required = false)
                                String query, HttpServletRequest request, Model model) throws ParseException,
            DirectoryNotCreatedException,
            IOException,
            InvalidTokenOffsetsException, BadPageValueException
    {
        Integer pageInt = page.getPage();
        if (pageInt == null)
        {
            LOGGER.error("paginationGet()" + "page value");
            throw new BadPageValueException("page value mast be integer");
        }
        sService.fillModel(model, request);
        model.addAttribute(
                "results",
                sService.searchByWords(query, (pageInt - 1) * LuceneBuilder.NUM_HITS, alphabetic)
        );

        return "result";
    }

    @RequestMapping(value = "/search/rest", method = RequestMethod.POST, produces = "application/json",
            consumes = "application/json")
    public
    @ResponseBody
    ResponseComponentSearch searchRest(@RequestBody RequestComponentSearch requestComponent,
                                       @ModelAttribute("alphabetic") Boolean alphabetic,
                                       @CookieValue(value = "query", defaultValue = "", required = false)
                                       String query) throws ParseException, DirectoryNotCreatedException, IOException, InvalidTokenOffsetsException
    {
        LOGGER.info("searchRest()");
        String[] results = sService.listToArray(
                sService.searchByWords(
                        query,
                        (Integer.parseInt(requestComponent.getPageNumber()) - 1) * LuceneBuilder.NUM_HITS,
                        alphabetic
                )
        );

        ResponseComponentSearch responseComponent = sService.getResponseComponentSearch();
        responseComponent.setPages(results);

        return responseComponent;
    }
}
