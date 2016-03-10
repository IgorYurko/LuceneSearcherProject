package com.spdukraine.testtask.search.components;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class RequestComponentSearch implements Serializable
{
    private String pageNumber;

    public String getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber)
    {
        this.pageNumber = pageNumber;
    }
}
