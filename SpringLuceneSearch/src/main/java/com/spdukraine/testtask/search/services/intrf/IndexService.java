package com.spdukraine.testtask.search.services.intrf;

import com.spdukraine.testtask.search.exceptions.DeadLinkException;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;

import java.io.IOException;

public interface IndexService extends Service
{
    void indexingUrl(String url, Integer deep) throws DeadLinkException, IOException, DirectoryNotCreatedException, InterruptedException;

    void indexingUrl(String url, Integer deep, Boolean asyncIndex) throws DeadLinkException, IOException,
            DirectoryNotCreatedException, InterruptedException;
}
