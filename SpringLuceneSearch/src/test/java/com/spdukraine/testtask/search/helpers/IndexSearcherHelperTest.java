package com.spdukraine.testtask.search.helpers;

import com.spdukraine.testtask.search.helpers.IndexSearcherHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndexSearcherHelperTest
{

    public IndexSearcherHelper getHelper()
    {
        return new IndexSearcherHelper();
    }

    @Test
    public void testGetFields() throws Exception
    {

        assertEquals(6, getHelper().getFields().size());
        assertTrue(getHelper().getFields().contains("entitle"));
    }

    @Test
    public void testGetTitles() throws Exception
    {
        assertTrue(getHelper().getFields().contains("rutitle"));
    }

    @Test
    public void testGetArticles() throws Exception
    {
        assertTrue(getHelper().getFields().contains("article"));
    }
}