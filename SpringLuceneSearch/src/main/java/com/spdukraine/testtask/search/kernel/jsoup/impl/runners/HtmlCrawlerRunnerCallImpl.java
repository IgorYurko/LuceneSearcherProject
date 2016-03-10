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
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("htmlCrawlerRunnerCallImpl")
public class HtmlCrawlerRunnerCallImpl implements HtmlCrawlerRunner
{
    @Autowired
    protected WorkersFactory workersFactory;

    protected NavigableMap<ExecutorService, List<Future<JsoupParseResult>>> exSrcFtrDeque =
            new TreeMap<>(ComparatorsHelper.getExSrcDequeMapComparator());

    protected HashSet<String> visitLinks = new HashSet<>();

    protected Pattern patternHtml;

    protected static final Logger LOGGER = LoggerFactory.getLogger(HtmlCrawlerRunnerCallImpl.class);

    @Override
    public Set<JsoupParseResult> crawlPage(List<String> innerLinks, Integer currentStep, Integer deep)
    {
        innerLinks = cleaningLinks(innerLinks);
        if (innerLinks.isEmpty())
        {
            clear();
            return DummyHelper.getSetObjectDummy();
        }

        makeParseResultFutures(innerLinks);

        if (currentStep + 1 == deep)
        {
            clear();
            return getParseResults();
        }

        List<String> nextStepLinks = getNextStep(innerLinks);
        addVisitLinks(innerLinks);
        Set<JsoupParseResult> results = new TreeSet<>(ComparatorsHelper.getJsoupParseComparator());

        if (currentStep + 1 < deep)
        {
            Set<JsoupParseResult> resultSet = crawlPage(nextStepLinks, currentStep + 1, deep);
            results.addAll(resultSet);
        }
        results.addAll(getParseResults());

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

    protected void makeParseResultFutures(List<String> innerLinks)
    {
        ExecutorService executorService = getExecutorService(LuceneBuilder.NUMBER_THREADS);
        List<Future<JsoupParseResult>> futures = makeThreads(innerLinks, executorService);
        exSrcFtrDeque.put(executorService, futures);
    }

    protected List<String> getNextStep(List<String> innerLinks)
    {
        ExecutorService executorService = getExecutorService(LuceneBuilder.NUMBER_THREADS);
        List<Future<Set<String>>> futures = makeThreads(innerLinks, executorService, true);

        List<String> nextStep = futures.parallelStream()
                .map(f -> {
                    Set<String> result = null;
                    try
                    {
                        result = f.get();
                    } catch (InterruptedException | ExecutionException ex)
                    {
                        result = DummyHelper.getSetStringDummy();
                        LOGGER.error("getNextStep() " + ex.getMessage());
                    }

                    return result;
                })
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
        executorService.shutdown();

        return nextStep;
    }

    protected Set<JsoupParseResult> getParseResults()
    {
        Map.Entry<ExecutorService, List<Future<JsoupParseResult>>> entry = exSrcFtrDeque.pollFirstEntry();
        List<Future<JsoupParseResult>> futures = entry.getValue();

        Set<JsoupParseResult> results = futures.parallelStream()
                .map(f -> {
                    JsoupParseResult result = null;
                    try
                    {
                        result = f.get();
                    } catch (InterruptedException | ExecutionException ex)
                    {
                        result = DummyHelper.getObjectDummy();
                        LOGGER.error("getParseResults() " + ex.getMessage());
                    }

                    return result;
                })
                .collect(Collectors.toSet());
        entry.getKey().shutdown();

        return results;
    }

    protected List<Future<JsoupParseResult>> makeThreads(List<String> innerLinks, ExecutorService executorService)
    {
        List<Callable<JsoupParseResult>> callables = new CopyOnWriteArrayList<>();
        List<Future<JsoupParseResult>> futures;

        innerLinks.parallelStream()
                .forEach(e -> callables.add(workersFactory.getWorker(e)));

        try
        {
            futures = executorService.invokeAll(callables);
        } catch (InterruptedException ex)
        {
            LOGGER.error(ex.getMessage());
            return Collections.emptyList();
        }

        return futures;
    }

    protected List<Future<Set<String>>> makeThreads(List<String> innerLinks, ExecutorService executorService,
                                                    Boolean nextStepLinks)
    {
        List<Callable<Set<String>>> callables = new CopyOnWriteArrayList<>();
        List<Future<Set<String>>> futures;

        innerLinks.parallelStream()
                .forEach(e -> callables.add(workersFactory.getWorker(e, nextStepLinks)));

        try
        {
            futures = executorService.invokeAll(callables);
        } catch (InterruptedException ex)
        {
            LOGGER.error(ex.getMessage());
            return Collections.emptyList();
        }

        return futures;
    }

    protected void clear()
    {
        visitLinks.clear();
        patternHtml = null;
    }
}
