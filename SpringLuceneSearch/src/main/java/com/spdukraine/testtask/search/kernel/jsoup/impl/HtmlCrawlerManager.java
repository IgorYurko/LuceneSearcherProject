package com.spdukraine.testtask.search.kernel.jsoup.impl;

import com.spdukraine.testtask.search.kernel.jsoup.intrf.HtmlCrawlerRunner;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class HtmlCrawlerManager
{
                            /*Callable variant*/
    @Autowired
    @Qualifier("htmlCrawlerRunnerCallImpl")
    private HtmlCrawlerRunner runner;

    @Autowired
    @Qualifier("asyncHtmlCrawlerRunnerCallImpl")
    private HtmlCrawlerRunner asyncRunner;

                            /*Runnable variant*/
//    @Autowired
//    @Qualifier("htmlCrawlerRunnerImpl")
//    private HtmlCrawlerRunner runner;
//
//    @Autowired
//    @Qualifier("asyncHtmlCrawlerRunnerImpl")
//    private HtmlCrawlerRunner asyncRunner;

    @SuppressWarnings("unchecked")
    public final Set<JsoupParseResult> getAllLinks(String url, Integer deep) throws IOException
    {
        List<String> urlList = Collections.singletonList(url);

        return (Set<JsoupParseResult>) runner.crawlPage(urlList, 0, deep);
    }

    public final void IndexAllLinks(String url, Integer deep) throws IOException
    {
        List<String> urlList = Collections.singletonList(url);
        asyncRunner.crawlPage(urlList, 0, deep);
    }
}
