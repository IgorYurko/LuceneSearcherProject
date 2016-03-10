package com.spdukraine.testtask.search.model;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;

@Component("lucene")
public class Lucene implements Serializable
{
    private final File luceneDir = new File(LuceneBuilder.PATH_LUCENE);

    private final File luceneDampDir = new File(LuceneBuilder.PATH_DUMP_LUCENE);

    private final Integer countDumpDirs = LuceneBuilder.COUNT_LAST_DUMP_DIRS;

    private final Long planeDumpHour = LuceneBuilder.PLAN_DUMP_HOUR;

    private final Long fixedRateHour = LuceneBuilder.FIXED_RATE_HOUR;



    public File getLuceneDir()
    {
        return luceneDir;
    }

    public File getLuceneDampDir()
    {
        return luceneDampDir;
    }

    public Integer getCountDumpDirs()
    {
        return countDumpDirs;
    }

    public Long getPlaneDumpHour()
    {
        return planeDumpHour;
    }

    public Long getFixedRateHour()
    {
        return fixedRateHour;
    }
}
