package com.spdukraine.testtask.search.components;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
public class FormComponentSwitcherSearch
{
    @NotBlank
    @Pattern(regexp = "true|false")
    private String switcher;

    public String getSwitcher()
    {
        return switcher;
    }

    public void setSwitcher(String switcher)
    {
        this.switcher = switcher;
    }
}
