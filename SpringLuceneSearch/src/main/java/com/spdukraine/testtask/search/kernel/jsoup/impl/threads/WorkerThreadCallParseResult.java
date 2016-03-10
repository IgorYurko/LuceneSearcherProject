package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import com.spdukraine.testtask.search.helpers.DummyHelper;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
@Scope("prototype")
public final class WorkerThreadCallParseResult extends AbstractWorkerCall
        implements Callable<JsoupParseResult>
{
    private String url;

    @Override
    public final WorkerThreadCallParseResult setProperties(String url)
    {
        this.url = url;

        return this;
    }

    @Override
    public JsoupParseResult call()
    {
        LOGGER.info(url);

        String html = getHtml(url);
        if (html.trim().isEmpty())
            return DummyHelper.getObjectDummy();

        return getParseResult(url, html);
    }

    private JsoupParseResult getParseResult(String url, String html)
    {
        Document parse = helper.parse(html);

        return new JsoupParseResult(url, helper.getTitle(parse), helper.getArticle(parse));
    }
}
