package com.spdukraine.testtask.search.dao.impl;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.model.Lucene;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import sun.util.resources.cldr.ti.LocaleNames_ti;

import javax.sound.midi.Patch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LuceneDaoImplTest
{
    //Step by step
    public Lucene getLuceneMock(File luceneDir, File luceneDumpDir)
    {
        Lucene lucene = mock(Lucene.class);
        when(lucene.getLuceneDir()).thenReturn(luceneDir);
        when(lucene.getLuceneDampDir()).thenReturn(luceneDumpDir);
        when(lucene.getPlaneDumpHour()).thenReturn(LuceneBuilder.PLAN_DUMP_HOUR);
        when(lucene.getFixedRateHour()).thenReturn(LuceneBuilder.FIXED_RATE_HOUR);
        when(lucene.getCountDumpDirs()).thenReturn(LuceneBuilder.COUNT_LAST_DUMP_DIRS);

        return lucene;
    }

    public LuceneDaoImpl getLuceneDao(Lucene lucene)
    {
        LuceneDaoImpl luceneDao = new LuceneDaoImpl();
        luceneDao.setLucene(lucene);
        return luceneDao;
    }

    @Test
    public void notExistsTwoDirectories()
    {
        Lucene lucene = getLuceneMock(new File("not_exist_dir"), new File("not_exist_dump_dir"));

        LuceneDaoImpl luceneDao = getLuceneDao(lucene);
        Boolean dump = luceneDao.dump();

        assertFalse(dump);
    }

    @Test
    public void existsDumpDirectory()
    {
        //If have not index at all
        File file = new File(LuceneBuilder.PATH_DUMP_LUCENE);
        file.mkdirs();
        Lucene lucene = getLuceneMock(new File("not_exist_dir"), file);

        LuceneDaoImpl luceneDao = getLuceneDao(lucene);
        Boolean dump = luceneDao.dump();

        assertFalse(dump);
    }

    @Test
    public void existsLuceneDirectory()
    {
        //Existence of the folder "dump" does not necessarily
        File file = new File(LuceneBuilder.PATH_LUCENE);
        file.mkdirs();
        try
        {
            FileUtils.writeStringToFile(new File(LuceneBuilder.PATH_LUCENE + "/test.txt"), "Hello lucene");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Lucene lucene = getLuceneMock(file, new File(LuceneBuilder.PATH_DUMP_LUCENE));

        LuceneDaoImpl luceneDao = getLuceneDao(lucene);
        Boolean dump = luceneDao.dump();

        assertTrue(dump);
    }


    @Test
    public void testCleanDumpDirectory() throws Exception
    {
        createDirectory();

        Lucene lucene = getLuceneMock(new File(LuceneBuilder.PATH_LUCENE),
                new File(LuceneBuilder.PATH_DUMP_LUCENE));

        LuceneDaoImpl luceneDao = getLuceneDao(lucene);
        Boolean cleanDumpDirectory = luceneDao.cleanDumpDirectory();

        assertTrue(cleanDumpDirectory);
        assertTrue(new File(LuceneBuilder.PATH_DUMP_LUCENE).list().length == 10);
    }

    public void createDirectory()
    {
        for (int i = 0; i < 20; i++)
        {
            try
            {
                Files.createDirectory(Paths.get(LuceneBuilder.PATH_DUMP_LUCENE + "/" +
                        LocalDate.now() + "_" + LocalTime.now()));
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}