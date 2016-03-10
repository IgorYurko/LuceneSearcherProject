package com.spdukraine.testtask.search.kernel.pojo;

import java.io.Serializable;

public class LuceneSearchResult implements Serializable
{
    private String url;

    private String highlightTitle;

    private String highlightArticle;


    public LuceneSearchResult(String url, String highlightTitle, String highlightArticle)
    {
        this.url = url;

        this.highlightTitle = highlightTitle;

        this.highlightArticle = highlightArticle;
    }

    public String getUrl()
    {
        return url;
    }


    public String getHighlightTitle()
    {
        return highlightTitle;
    }


    public String getHighlightArticle()
    {
        return highlightArticle;
    }


}
