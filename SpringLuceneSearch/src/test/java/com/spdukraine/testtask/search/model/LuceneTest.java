package com.spdukraine.testtask.search.model;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.model.Lucene;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class LuceneTest
{
    public Lucene lucene = new Lucene();

    @Test
    public void testGetLuceneDir() throws Exception
    {
        assertEquals(new File(LuceneBuilder.PATH_LUCENE), lucene.getLuceneDir());
    }

    @Test
    public void testGetLuceneDampDir() throws Exception
    {
        assertEquals(new File(LuceneBuilder.PATH_DUMP_LUCENE), lucene.getLuceneDampDir());
    }

    @Test
    public void testGetCountDumpDirs() throws Exception
    {
        assertEquals(new Integer(10), lucene.getCountDumpDirs());
    }
}