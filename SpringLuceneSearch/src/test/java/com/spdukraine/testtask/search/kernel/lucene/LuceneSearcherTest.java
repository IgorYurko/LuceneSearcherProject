package com.spdukraine.testtask.search.kernel.lucene;

import com.spdukraine.testtask.search.kernel.pojo.LuceneSearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testServlet.xml")
@WebAppConfiguration
public class LuceneSearcherTest
{
    //First test LuceneIndexTest
    @Autowired
    private LuceneSearcher searcher;

    @Test
    public void testSearch_Sort_relevant() throws Exception
    {
        List<LuceneSearchResult> title = searcher.search("title", 0, false);
        assertEquals(5, title.size());
        assertTrue(title.get(0).getUrl().startsWith("http://some/url"));
        assertTrue(title.get(0).getHighlightTitle().contains("[start]"));
        assertTrue(title.get(0).getHighlightTitle().contains("[end]"));
    }

    @Test
    public void testSearch_Sort_alphabetical() throws Exception
    {
        List<LuceneSearchResult> title = searcher.search("title", 0, true);
        assertEquals(5, title.size());
        assertTrue(title.get(0).getUrl().startsWith("http://some/url/1"));
    }
}