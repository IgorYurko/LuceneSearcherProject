package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import com.spdukraine.testtask.search.helpers.JsoupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractWorkerCall
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWorkerCall.class);

    protected JsoupHelper helper;

    public abstract AbstractWorkerCall setProperties(String url);

    @Autowired
    protected final void setHelper(JsoupHelper helper)
    {
        this.helper = helper;
    }

    protected final String getHtml(String url)
    {
        if(url.trim().isEmpty())
            return "";

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
