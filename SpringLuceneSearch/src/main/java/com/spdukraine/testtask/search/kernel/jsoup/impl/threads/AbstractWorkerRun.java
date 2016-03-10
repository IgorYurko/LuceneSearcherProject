package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import com.spdukraine.testtask.search.helpers.JsoupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractWorkerRun
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWorkerRun.class);

    protected JsoupHelper helper;

    public abstract AbstractWorkerRun setProperties(String url, Collection<? extends Serializable> some);

    @Autowired
    protected final void setHelper(JsoupHelper helper)
    {
        this.helper = helper;
    }

    protected final String getHtml(String url)
    {
        try
        {
            return helper.downloadHtml(url);
        } catch (IOException e)
        {
            LOGGER.info(e.getMessage());
            return "";
        }
    }
}
