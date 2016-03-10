package com.spdukraine.testtask.search.components;

import org.springframework.stereotype.Component;

@Component
public class RequestComponentPagination
{
    Integer page;

    public RequestComponentPagination(String pageString)
    {
        if (pageString.matches("[0-9]+"))
            page = Integer.parseInt(pageString);
    }

    public RequestComponentPagination()
    {
    }

    public Integer getPage()
    {
        return page;
    }
}
