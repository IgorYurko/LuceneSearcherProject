package com.spdukraine.testtask.search.components;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Component
public final class FormComponentIndex implements Serializable
{
    @NotBlank
    @URL
    private String q;

    @NotBlank
    @Pattern(regexp = "[0-9]+")
    private String depth;

    public String getDepth()
    {
        return depth;
    }

    public void setDepth(String depth)
    {
        this.depth = depth;
    }

    public String getQ()
    {
        return q;
    }

    public void setQ(String q)
    {
        this.q = q;
    }
}
