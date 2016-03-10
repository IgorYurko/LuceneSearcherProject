package com.spdukraine.testtask.search.services.impl;

import com.spdukraine.testtask.search.components.FormComponentIndex;
import com.spdukraine.testtask.search.exceptions.DeadLinkException;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.lucene.LuceneIndexer;
import com.spdukraine.testtask.search.kernel.jsoup.impl.HtmlCrawlerManager;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import com.spdukraine.testtask.search.services.intrf.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class IndexServiceImpl implements IndexService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private FormComponentIndex iFormComponent;

    @Autowired
    private HtmlCrawlerManager crawlerManager;

    @Autowired
    private LuceneIndexer indexer;

    public FormComponentIndex getFormComponent()
    {
        return iFormComponent;
    }

    @Async
    public final void indexingUrl(String url, Integer deep) throws DeadLinkException, IOException,
            DirectoryNotCreatedException, InterruptedException
    {
        Set<JsoupParseResult> allLinks = crawlerManager.getAllLinks(url, deep);
        indexer.index(allLinks);
        LOGGER.info("Job done.");
    }

    @Async
    public final void indexingUrl(String url, Integer deep, Boolean asyncIndex) throws DeadLinkException, IOException,
            DirectoryNotCreatedException, InterruptedException
    {
        if (asyncIndex)
            crawlerManager.IndexAllLinks(url, deep);
        else
            indexingUrl(url, deep);
    }
}
