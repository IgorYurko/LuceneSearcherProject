package com.spdukraine.testtask.search.kernel.jsoup.impl.runners;

import com.spdukraine.testtask.search.helpers.DummyHelper;
import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.kernel.jsoup.impl.threads.WorkersFactory;
import com.spdukraine.testtask.search.kernel.jsoup.intrf.HtmlCrawlerRunner;
import com.spdukraine.testtask.search.helpers.ComparatorsHelper;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("htmlCrawlerRunnerImpl")
public class HtmlCrawlerRunnerImpl implements HtmlCrawlerRunner
{
    @Autowired
    protected WorkersFactory workersFactory;

    protected ArrayDeque<ExecutorService> executorServicesDeque = new ArrayDeque<>();

    protected HashSet<String> visitLinks = new HashSet<>();

    protected Pattern patternHtml;

    protected static final Logger LOGGER = LoggerFactory.getLogger(HtmlCrawlerRunnerImpl.class);

    @Override
    public Set<JsoupParseResult> crawlPage(List<String> innerLinks, Integer currentStep, Integer deep)
    {
        innerLinks = cleaningLinks(innerLinks);
        if (innerLinks.isEmpty())
        {
            clear();
            return DummyHelper.getSetObjectDummy();

        }

        Set<JsoupParseResult> results = getParseResults(innerLinks);

        if (currentStep + 1 == deep)
        {
            waitTermination();
            clear();
            return results;
        }

        List<String> nextStepLinks = getNextStep(innerLinks);
        waitTermination();
        addVisitLinks(innerLinks);

        if (currentStep + 1 < deep)
        {
            Set<JsoupParseResult> resultSet = crawlPage(nextStepLinks, currentStep + 1, deep);
            waitTermination();
            results.addAll(resultSet);
        }

        return results;
    }

    protected void addVisitLinks(List<String> innerLinks)
    {
        visitLinks.addAll(innerLinks);
    }

    protected ExecutorService getExecutorService(Integer nThreads)
    {
        return Executors.newFixedThreadPool(nThreads);
    }

    protected List<String> cleaningLinks(List<String> innerLinks)
    {
        if (patternHtml == null)
            patternHtml = Pattern.compile("^" + innerLinks.get(0) + ".*");

        return innerLinks.parallelStream()
                .filter(e -> !visitLinks.contains(e) && patternHtml.matcher(e).find())
                .collect(Collectors.toList());
    }

    protected Set<JsoupParseResult> getParseResults(List<String> innerLinks)
    {
        ExecutorService executorService = getExecutorService(LuceneBuilder.NUMBER_THREADS);
        Set<JsoupParseResult> results = new ConcurrentSkipListSet<>(ComparatorsHelper.getJsoupParseComparator());
        executorServicesDeque.addFirst(executorService);

        makeThreads(innerLinks, executorService, results);
        executorService.shutdown();

        return results;
    }

    protected List<String> getNextStep(List<String> innerLinks)
    {
        ExecutorService executorService = getExecutorService(LuceneBuilder.NUMBER_THREADS);
        List<String> nextStepLinks = new CopyOnWriteArrayList<>();
        executorServicesDeque.addFirst(executorService);

        makeThreads(innerLinks, executorService, nextStepLinks);

        executorService.shutdown();

        return nextStepLinks;
    }

    protected void waitTermination()
    {
        ExecutorService executorService = executorServicesDeque.pollFirst();

        while (!executorService.isTerminated()){}
    }

    protected void makeThreads(List<String> innerLinks, ExecutorService executorService,
                               Set<JsoupParseResult> results)
    {
        innerLinks.parallelStream()
                .forEach(e -> executorService.execute(workersFactory.getWorker(e, results)));
    }

    protected void makeThreads(List<String> innerLinks, ExecutorService executorService,
                               List<String> nextStepLinks)
    {
        innerLinks.parallelStream()
                .forEach(e -> executorService.execute(workersFactory.getWorker(e, nextStepLinks)));

    }

    protected void clear()
    {
        visitLinks.clear();
        patternHtml = null;
    }
}
