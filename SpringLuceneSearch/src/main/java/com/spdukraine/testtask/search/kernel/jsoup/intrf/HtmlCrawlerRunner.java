package com.spdukraine.testtask.search.kernel.jsoup.intrf;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface HtmlCrawlerRunner
{
    Set<? extends Serializable> crawlPage(List<String> innerLinks,
                                          Integer currentStep, Integer deep);
}
