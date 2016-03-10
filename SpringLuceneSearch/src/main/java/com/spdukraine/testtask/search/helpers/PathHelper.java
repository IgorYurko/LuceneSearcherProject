package com.spdukraine.testtask.search.helpers;


import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
public final class PathHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PathHelper.class);

    public Path getLucenePath() throws DirectoryNotCreatedException
    {
        LOGGER.info("getLucenePath()");
        return checkOrCreateDirectory(new File(LuceneBuilder.PATH_LUCENE));
    }

    private Path checkOrCreateDirectory(File file) throws DirectoryNotCreatedException
    {
        LOGGER.info("checkOrCreateDirectory()");
        if (file.exists() && file.isDirectory())
            return file.toPath();
        if (file.mkdirs())
            return file.toPath();
        else
            throw new DirectoryNotCreatedException(
                    "Can't create directory with name " + file.getPath());
    }
}
