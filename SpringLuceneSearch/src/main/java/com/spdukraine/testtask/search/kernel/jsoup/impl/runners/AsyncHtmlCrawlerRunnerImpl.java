package com.spdukraine.testtask.search.kernel.jsoup.impl.runners;

import com.spdukraine.testtask.search.helpers.ComparatorsHelper;
import com.spdukraine.testtask.search.helpers.DummyHelper;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component("asyncHtmlCrawlerRunnerImpl")
public class AsyncHtmlCrawlerRunnerImpl extends HtmlCrawlerRunnerImpl
{
    @Autowired
    private AsyncLuceneIndexerRunner indexerRunner;

    private final TreeSet<JsoupParseResult> results = new TreeSet<>(ComparatorsHelper.getJsoupParseComparator());

    @Override
    public Set<JsoupParseResult> crawlPage(List<String> innerLinks, Integer currentStep, Integer deep)
    {
        innerLinks = cleaningLinks(innerLinks);
        if (innerLinks.isEmpty())
        {
            clear();
            return DummyHelper.getSetObjectDummy();
        }


        Set<JsoupParseResult> resultSet = getParseResults(innerLinks);
        waitAndIndex(resultSet);

        if (currentStep + 1 == deep)
        {
            clear();
            return DummyHelper.getSetObjectDummy();
        }

        List<String> nextStepLinks = waitAndGetNextStepLinks(innerLinks);
        addVisitLinks(innerLinks);

        if (currentStep + 1 < deep)
        {
            crawlPage(nextStepLinks, currentStep + 1, deep);
        }

        return DummyHelper.getSetObjectDummy();
    }

    @SuppressWarnings("unchecked")
    private void waitAndIndex(Set<JsoupParseResult> resultsSet)
    {
        waitTermination();
        results.addAll(resultsSet);
        indexerRunner.indexPage(
                (Set<JsoupParseResult>) results.clone(),
                (Set<String>) visitLinks.clone()
        );
    }

    private List<String> waitAndGetNextStepLinks(List<String> innerLinks)
    {
        List<String> nextStepLinks = getNextStep(innerLinks);
        waitTermination();

        return nextStepLinks;
    }

    @Override
    protected void clear()
    {
        super.clear();
        results.clear();
    }
}

