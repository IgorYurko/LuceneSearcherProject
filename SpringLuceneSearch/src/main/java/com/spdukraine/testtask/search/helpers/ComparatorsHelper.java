package com.spdukraine.testtask.search.helpers;

import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;

public class ComparatorsHelper
{
    public static class InnerComparators
    {
        public static final Comparator<JsoupParseResult> JSOUP_PARSE_COMPARATOR = (aObj, bObj) ->
        {
            if (aObj == bObj) return 0;
            return aObj.getTitle().compareTo(bObj.getTitle());
        };

        public static final Comparator<ExecutorService> DEQUE_MAP_COMPARATOR = (aObj, bObj) -> -1;

        public static final Comparator<String> STRING_REVERS_COMPARATOR = (aStr, bStr) ->
                bStr.compareTo(aStr);
    }

    public static Comparator<JsoupParseResult> getJsoupParseComparator()
    {
        return InnerComparators.JSOUP_PARSE_COMPARATOR;
    }

    public static Comparator<ExecutorService> getExSrcDequeMapComparator()
    {
        return InnerComparators.DEQUE_MAP_COMPARATOR;
    }

    public static Comparator<String> getStringReversComparator()
    {
        return InnerComparators.STRING_REVERS_COMPARATOR;
    }
}
