package com.spdukraine.testtask.search.components;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ResponseComponentSearch implements Serializable
{
    private String[] pages;

    public String[] getPages()
    {
        return pages;
    }

    public void setPages(String[] pages)
    {
        this.pages = pages;
    }
}
