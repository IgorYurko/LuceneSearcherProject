package com.spdukraine.testtask.search.dao.impl;

import com.spdukraine.testtask.search.dao.intrf.LuceneDao;
import com.spdukraine.testtask.search.helpers.ComparatorsHelper;
import com.spdukraine.testtask.search.model.Lucene;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository("luceneDaoImp")
public class LuceneDaoImpl implements LuceneDao
{
    public static final Logger LOGGER = LoggerFactory.getLogger(LuceneDaoImpl.class);

    private Lucene lucene;

    @Autowired
    public void setLucene(Lucene lucene)
    {
        this.lucene = lucene;
    }

    @Override
    public Boolean dump()
    {
        if (!checkLuceneDir())
            return false;

        long timeMillisNow = System.currentTimeMillis();
        if (!checkLastModified(timeMillisNow))
            return false;

        return makeDump(timeMillisNow);
    }

    @Override
    public Boolean cleanDumpDirectory()
    {
        if (!checkLuceneDir())
            return false;

        if(!checkDumpDir())
            return false;

        List<String> innerDirectories = getInnerDirs();
        if (innerDirectories.size() <= lucene.getCountDumpDirs())
            return false;

        List<String> deleteDirs = getDeleteDirs(innerDirectories);
        try
        {
            deleteDirs(deleteDirs);
        } catch (IOException e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }

        return true;
    }

    private boolean checkLuceneDir()
    {
        if (!lucene.getLuceneDir().exists() || !lucene.getLuceneDir().canRead())
            return false;
        return true;
    }

    private boolean checkDumpDir()
    {
        if (!lucene.getLuceneDampDir().exists() || lucene.getLuceneDampDir().isFile())
        {
            if (!lucene.getLuceneDampDir().mkdirs())
                return false;
        }
        if (!lucene.getLuceneDampDir().canWrite())
        {
            if (!lucene.getLuceneDampDir().setWritable(true))
                return false;
        }
        return true;
    }

    private void deleteDirs(List<String> deleteDirs) throws IOException
    {
        for (String deleteDir : deleteDirs)
        {
            FileUtils.deleteDirectory(new File(lucene.getLuceneDampDir() + "/" + deleteDir));
        }
    }

    private List<String> getDeleteDirs(List<String> innerDirectories)
    {
        return innerDirectories.subList(lucene.getCountDumpDirs(),
                innerDirectories.size());
    }

    private List<String> getInnerDirs()
    {
        List<String> innerDirectories = Arrays.asList(lucene.getLuceneDampDir().list());
        Collections.sort(innerDirectories, ComparatorsHelper.getStringReversComparator());
        return innerDirectories;
    }

    private Boolean checkLastModified(long timeMillisNow)
    {
        long lastHour = timeMillisNow - lucene.getLuceneDir().lastModified();
        return lastHour > lucene.getPlaneDumpHour() || lastHour < lucene.getFixedRateHour();
    }

    private Boolean makeDump(long timeMillisNow)
    {
        File dumpDir = new File(getDumpDirName().toString());
        if (!dumpDir.mkdirs())
        {
            LOGGER.info("Can't create a directory" + dumpDir.getPath());
            return true;
        }
        try
        {
            FileUtils.copyDirectory(lucene.getLuceneDir(), dumpDir);
            if (!lucene.getLuceneDampDir().setLastModified(timeMillisNow - 1000))
            {
                LOGGER.info("Can't change last modifies a directory" + lucene.getLuceneDampDir().getPath());
                return false;
            }
        } catch (IOException e)
        {
            LOGGER.info(e.getMessage());
            return false;
        }

        return true;
    }

    private StringBuilder getDumpDirName()
    {
        return new StringBuilder(lucene.getLuceneDampDir().getPath())
                .append("/")
                .append(LocalDate.now())
                .append("_")
                .append(LocalTime.now());
    }
}
