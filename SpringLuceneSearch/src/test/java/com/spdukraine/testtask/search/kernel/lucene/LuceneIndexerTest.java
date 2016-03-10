package com.spdukraine.testtask.search.kernel.lucene;

import com.spdukraine.testtask.search.helpers.ComparatorsHelper;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.TreeSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testServlet.xml")
@WebAppConfiguration
public class LuceneIndexerTest
{
    @Autowired
    private LuceneIndexer indexer;

    private TreeSet<JsoupParseResult> set =
            new TreeSet<>(ComparatorsHelper.getJsoupParseComparator());

    @Before
    public void setup()
    {

        set.add(new JsoupParseResult("http://some/url/1", "Title 1", "Article 1"));
        set.add(new JsoupParseResult("http://some/url/2", "Title 2", "Article 2"));
        set.add(new JsoupParseResult("http://some/url/3", "Title 3", "Article 3"));
        set.add(new JsoupParseResult("http://some/url/4", "Title 4", "Article 4"));
        set.add(new JsoupParseResult("http://some/url/5", "Title 5", "Article 5"));
    }

    @Test
    public void testIndex() throws Exception
    {
        indexer.index(set);
    }
}