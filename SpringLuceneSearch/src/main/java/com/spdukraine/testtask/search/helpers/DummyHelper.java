package com.spdukraine.testtask.search.helpers;

import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;

import java.util.Collections;
import java.util.Set;

public class DummyHelper
{
    private static class InnerDummyHelper
    {
        private static final JsoupParseResult OBJECT_DUMMY = new JsoupParseResult("", "", "");

        private static final Set<String> SET_STRING_DUMMY = Collections.emptySet();

        private static final Set<JsoupParseResult> SET_OBJECT_DUMMY = Collections.emptySet();

    }

    public static JsoupParseResult getObjectDummy()
    {
        return InnerDummyHelper.OBJECT_DUMMY;
    }

    public static Set<String> getSetStringDummy()
    {
        return InnerDummyHelper.SET_STRING_DUMMY;
    }

    public static Set<JsoupParseResult> getSetObjectDummy()
    {
        return InnerDummyHelper.SET_OBJECT_DUMMY;
    }
}
