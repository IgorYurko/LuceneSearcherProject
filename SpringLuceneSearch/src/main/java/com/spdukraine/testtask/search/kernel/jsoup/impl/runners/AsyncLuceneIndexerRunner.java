package com.spdukraine.testtask.search.kernel.jsoup.impl.runners;

import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.lucene.LuceneIndexer;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

@Component
public class AsyncLuceneIndexerRunner
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncLuceneIndexerRunner.class);

    private final Semaphore semaphore = new Semaphore(1);

    @Autowired
    private LuceneIndexer indexer;

    @Async
    public void indexPage(Set<JsoupParseResult> setResult, Set<String> visitLinks)
    {
        acquire();
        LOGGER.info("Start job.");

        index(getActualLinks(setResult, visitLinks));
        semaphore.release();

        LOGGER.info("Done job.");
    }

    private void acquire()
    {
        try
        {
            semaphore.acquire();
        } catch (InterruptedException e)
        {
            semaphore.release();
            LOGGER.error(e.getMessage());
        }
    }

    private void index(Set<JsoupParseResult> setResult)
    {
        try
        {
            indexer.index(setResult);
        } catch (DirectoryNotCreatedException | IOException e)
        {
            semaphore.release();
            LOGGER.error(e.getMessage());
        }
    }

    private Set<JsoupParseResult> getActualLinks(Set<JsoupParseResult> setResult, Set<String> visitLinks)
    {
        return setResult.parallelStream()
                .filter(e -> !visitLinks.contains(e.getUrl()))
                .collect(Collectors.toSet());
    }
}
