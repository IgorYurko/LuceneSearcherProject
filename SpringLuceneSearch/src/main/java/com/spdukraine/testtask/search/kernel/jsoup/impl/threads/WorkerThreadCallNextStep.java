package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import com.spdukraine.testtask.search.helpers.DummyHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;

@Component
@Scope("prototype")
public final class WorkerThreadCallNextStep extends AbstractWorkerCall implements Callable<Set<String>>
{
    private String url;

    @Override
    public final WorkerThreadCallNextStep setProperties(String url)
    {
        this.url = url;

        return this;
    }

    @Override
    public Set<String> call()
    {
        LOGGER.info(url);

        String html = getHtml(url);
        if (html.trim().isEmpty())
            return DummyHelper.getSetStringDummy();

        return getAllLinks(html, url);
    }

    private Set<String> getAllLinks(String html, String url)
    {
        return helper.extractLinks(html, url);
    }
}
