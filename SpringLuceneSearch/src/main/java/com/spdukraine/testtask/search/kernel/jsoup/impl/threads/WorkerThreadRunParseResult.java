package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Component
@Scope("prototype")
public final class WorkerThreadRunParseResult extends AbstractWorkerRun implements Runnable
{
    private String url;

    private Set<JsoupParseResult> results;

    @Override
    @SuppressWarnings("unchecked")
    public final WorkerThreadRunParseResult setProperties(String url, Collection<? extends Serializable> results)
    {
        this.url = url;
        this.results = (Set<JsoupParseResult>) results;

        return this;
    }

    @Override
    public void run()
    {
        LOGGER.info(url);

        String html = getHtml(url);
        if (html.trim().isEmpty())
            return;

        JsoupParseResult result = getParseResult(url, html);
        results.add(result);
    }

    private JsoupParseResult getParseResult(String url, String html)
    {
        Document parse = helper.parse(html);

        return new JsoupParseResult(url, helper.getTitle(parse), helper.getArticle(parse));
    }
}
