package com.spdukraine.testtask.search.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController
{
    public static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = {"/error"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String error(Model model, HttpServletRequest request)
    {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        LOGGER.error(request.getRequestURI() + " "
                + status + " "
                + request.getAttribute("javax.servlet.error.message"));

        model.addAttribute("status", status);

        return "error";
    }
}
