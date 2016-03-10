package com.spdukraine.testtask.search.kernel.jsoup.impl.threads;

import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.List;
import java.util.Set;

@Component
public final class WorkersFactory
{
    private Provider<WorkerThreadRunParseResult> parseResultRunProvider;

    private Provider<WorkerThreadRunNextStep> nextStepRunProvider;

    private Provider<WorkerThreadCallParseResult> parseResultCallProvider;

    private Provider<WorkerThreadCallNextStep> nextStepCallProvider;

    @Inject
    private void setParseResultRunProvider(Provider<WorkerThreadRunParseResult> parseResultRunProvider)
    {
        this.parseResultRunProvider = parseResultRunProvider;
    }

    @Inject
    private void setNextStepRunProvider(Provider<WorkerThreadRunNextStep> nextStepRunProvider)
    {
        this.nextStepRunProvider = nextStepRunProvider;
    }

    @Inject
    private void setParseResultCallProvider(Provider<WorkerThreadCallParseResult> parseResultCallProvider)
    {
        this.parseResultCallProvider = parseResultCallProvider;
    }

    @Inject
    private void setNextStepCallProvider(Provider<WorkerThreadCallNextStep> nextStepCallProvider)
    {
        this.nextStepCallProvider = nextStepCallProvider;
    }

    public final WorkerThreadRunParseResult getWorker(String url, Set<JsoupParseResult> results)
    {
        return parseResultRunProvider.get().setProperties(url, results);
    }

    public final WorkerThreadRunNextStep getWorker(String url, List<String> nextStepLinks)
    {
        return nextStepRunProvider.get().setProperties(url, nextStepLinks);
    }

    public final WorkerThreadCallParseResult getWorker(String url)
    {
        return parseResultCallProvider.get().setProperties(url);
    }

    public final WorkerThreadCallNextStep getWorker(String url, Boolean nextStep)
    {
        return nextStepCallProvider.get().setProperties(url);
    }
}
