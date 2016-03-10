package com.spdukraine.testtask.search.helpers;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class JsoupHelper
{
    public final String downloadHtml(String url) throws IOException
    {
        Document document = Jsoup.connect(url)
                .ignoreHttpErrors(LuceneBuilder.IGNORED_HTTP_ERROR)
                .followRedirects(LuceneBuilder.FOLLOW_REDIRECT)
                .userAgent(LuceneBuilder.USER_AGENT)
                .timeout(LuceneBuilder.TIMEOUT_DOWNLOAD)
                .method(Connection.Method.GET)
                .get();

        return document.html();
    }

    public final Set<String> extractLinks(String html, String base)
    {
        return Jsoup.parse(html, base).select("a[href]").parallelStream()
                .map(e -> e.attr("abs:href")
                        .trim()
                        .toLowerCase())
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toSet());
    }

    public final String getTitle(Document html)
    {
        return html.select(LuceneBuilder.JSOUP_SELECT_TITLE).text();
    }

    public final String getArticle(Document html)
    {
        return html.select(LuceneBuilder.JSOUP_SELECT_ARTICLE).text();
    }

    public final Document parse(String html)
    {
        return Jsoup.parse(html);
    }


}
