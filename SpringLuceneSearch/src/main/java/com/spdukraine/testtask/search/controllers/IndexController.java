package com.spdukraine.testtask.search.controllers;

import com.spdukraine.testtask.search.components.FormComponentIndex;
import com.spdukraine.testtask.search.exceptions.DeadLinkException;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.services.intrf.IndexService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class IndexController extends AbstractController {
    public static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private IndexService iService;

    @Autowired
    public void setIService(IndexService iService)
    {
        this.iService = iService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexGet(Model model) {
        LOGGER.info("indexGet()");

        model.addAttribute("formComponent", iService.getFormComponent());
        model.addAttribute("depthList", LuceneBuilder.DEPTH);

        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String indexPost(@Valid @ModelAttribute("formComponent") FormComponentIndex iFormComponent,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes)
            throws IOException, ParseException, URISyntaxException, DirectoryNotCreatedException, DeadLinkException, InterruptedException
    {
        if (bindingResult.hasErrors())
        {
            List<String> errors = getErrors(bindingResult);
            redirectAttributes.addFlashAttribute("errors", errors);

            return "redirect:/index";
        }
        LOGGER.info("indexPost()");
        iService.indexingUrl(iFormComponent.getQ(), Integer.parseInt(iFormComponent.getDepth()),
                LuceneBuilder.ASYNC_INDEX);

        return "redirect:/index";
    }
}
