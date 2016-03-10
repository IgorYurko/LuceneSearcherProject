package com.spdukraine.testtask.search.services.impl;

import com.spdukraine.testtask.search.dao.intrf.LuceneDao;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.services.intrf.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SchedulerServiceImpl implements SchedulerService
{
    @Autowired
    @Qualifier("luceneDaoImp")
    private LuceneDao lDao;

    public static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Scheduled(fixedRate = 60_000)
    public final void dumpLucene()
    {
        if (!lDao.dump())
            LOGGER.info("dumpLucene() : I do nothing.");
        else
            LOGGER.info("dumpLucene() : I made a dump.");
    }

    @Scheduled(fixedRate = 120_000)
    public final void cleanDumpLucene()
    {
        if (!lDao.cleanDumpDirectory())
            LOGGER.info("cleanDumpLucene() : I do nothing.");
        else
            LOGGER.info("cleanDumpLucene() : I cleaned directory.");
    }

}
