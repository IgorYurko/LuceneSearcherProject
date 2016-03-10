package com.spdukraine.testtask.search.controllers;

import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ErrorControllerTest
{
    @Test
    public void testError() throws Exception
    {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(404);
        when(request.getRequestURI()).thenReturn("http://some/uri");
        when(request.getAttribute("javax.servlet.error.message")).thenReturn("error message");
        ErrorController controller = new ErrorController();

        Model model = new RedirectAttributesModelMap();
        String viewName = controller.error(model, request);

        assertEquals("Return value", "error", viewName);
        assertEquals("Size map", 1, model.asMap().size());
    }
}