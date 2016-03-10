package com.spdukraine.testtask.search.controllers;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractController
{
    @ModelAttribute("alphabetic")
    private Boolean getAlphabetic()
    {
        return LuceneBuilder.SORT_ALPHABETIC;
    }

    protected List<String> getErrors(BindingResult bindingResult)
    {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream().map(FieldError::getField).collect(Collectors.toList());
    }
}
