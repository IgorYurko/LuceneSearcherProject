package com.spdukraine.testtask.search.components;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Component
public final class FormComponentSearch implements Serializable
{
    @NotBlank
    @Size(min = 3)
    private String q;

    public String getQ()
    {
        return q;
    }

    public void setQ(String q)
    {
        this.q = q;
    }
}
