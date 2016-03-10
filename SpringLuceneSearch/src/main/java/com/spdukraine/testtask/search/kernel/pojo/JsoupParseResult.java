package com.spdukraine.testtask.search.kernel.pojo;

import java.io.Serializable;

public class JsoupParseResult implements Serializable
{
    private String url;

    private String title;

    private String article;

    public JsoupParseResult(String url, String title, String article)
    {
        this.url = url;
        this.title = title;
        this.article = article;
    }

    public String getUrl()
    {
        return url;
    }

    public String getTitle()
    {
        return title;
    }

    public String getArticle()
    {
        return article;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsoupParseResult that = (JsoupParseResult) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return title != null ? title.equals(that.title) : that.title == null;

    }

    @Override
    public int hashCode()
    {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
