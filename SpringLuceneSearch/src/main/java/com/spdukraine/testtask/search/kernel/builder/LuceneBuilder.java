package com.spdukraine.testtask.search.kernel.builder;

import org.apache.lucene.index.IndexWriterConfig;

public class LuceneBuilder
{
    public static final Integer DEPTH = 3;
    public static final Integer NUM_HITS = 10;
    public static final Integer PERCENT_GREAT_WORDS = 70;
    public static final Double COUNT_FUZZY_SEARCH = 0.7;
    public static final Integer FRAGMENT_SIZE = 400;
    public static final Integer NUMBER_THREADS = 5;
    public static final Integer COOKIE_SET_MAX_AGE = 86400;
    public static final Long FIXED_RATE_HOUR = (long)(1/60D * 3_600_000);
    public static final Long PLAN_DUMP_HOUR =  (long)(24D * 3_600_000);
    public static final Boolean ASYNC_INDEX = true;
    public static final Boolean SORT_ALPHABETIC = false;
    public static final String PATH_LUCENE = "test_dir/lucene";
    public static final String PATH_DUMP_LUCENE = "test_dir/dump/lucene";
    public static final Integer COUNT_LAST_DUMP_DIRS = 10;
    public static final Integer TIMEOUT_DOWNLOAD = 3000;
    public static final Boolean FOLLOW_REDIRECT = true;
    public static final Boolean IGNORED_HTTP_ERROR = true;
    public static final String JSOUP_SELECT_TITLE = "title";
    public static final String JSOUP_SELECT_ARTICLE = "body";
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36…L, like Gecko)" +
            "Chrome/48.0.2564.103 Safari/537.36";
    public static final IndexWriterConfig.OpenMode OPEN_MODE = IndexWriterConfig.OpenMode.CREATE_OR_APPEND;
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String ARTICLE = "article";
    public static final String RUS_TITLE = "rutitle";
    public static final String RUS_ARTICLE = "ruarticle";
    public static final String ENG_TITLE = "entitle";
    public static final String ENG_ARTICLE = "enarticle";
    public static final String START_TAG = "[start]";
    public static final String FINISH_TAG = "[end]";
}
