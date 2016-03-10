package com.spdukraine.testtask.search.impl;

import com.spdukraine.testtask.search.kernel.pojo.LuceneSearchResult;
import com.spdukraine.testtask.search.services.impl.SearchServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchServiceImplTest
{

    @Test
    public void testListToArray() throws Exception
    {
        List<LuceneSearchResult> list = new ArrayList<>();
        list.add(new LuceneSearchResult("http;//some/url_1", "title_1", "article_1"));
        list.add(new LuceneSearchResult("http;//some/url_2", "title_2", "article_2"));
        list.add(new LuceneSearchResult("http;//some/url_3", "title_3", "article_3"));
        list.add(new LuceneSearchResult("http;//some/url_4", "title_4", "article_4"));

        SearchServiceImpl service = new SearchServiceImpl();

        String[] array = service.listToArray(list);

        assertEquals(4, array.length);
        assertTrue(array[0].contains("<b>title_1</b>"));
        assertTrue(array[1].contains("<b>title_2</b>"));
        assertTrue(array[0].contains("<a href='http;//some/url_1'>http;//some/url_1</a>"));
        assertFalse(array[0].contains("<b>title_2</b>"));
    }
}