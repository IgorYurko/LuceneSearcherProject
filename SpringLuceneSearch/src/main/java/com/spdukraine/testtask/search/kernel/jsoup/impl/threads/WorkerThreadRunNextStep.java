package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Scope("prototype")
public final class WorkerThreadRunNextStep extends AbstractWorkerRun implements Runnable
{
    private String url;

    private List<String> nextStepLinks;

    @Override
    @SuppressWarnings("unchecked")
    public final WorkerThreadRunNextStep setProperties(String url, Collection<? extends Serializable> some)
    {
        this.url = url;
        this.nextStepLinks = (List<String>) some;

        return this;
    }

    @Override
    public void run()
    {
        LOGGER.info(url);

        String html = getHtml(url);
        if (html.trim().isEmpty())
            return;

        Set<String> allLinks = getAllLinks(html, url);
        nextStepLinks.addAll(allLinks);
    }

    private Set<String> getAllLinks(String html, String url)
    {
        return helper.extractLinks(html, url);
    }
}
