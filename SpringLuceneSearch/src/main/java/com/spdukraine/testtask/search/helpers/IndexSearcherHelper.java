package com.spdukraine.testtask.search.helpers;

import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IndexSearcherHelper
{
    @Autowired
    @Qualifier("standardAnalyzer")
    private Analyzer stAnalyzer;

    @Autowired
    @Qualifier("englishAnalyzer")
    private Analyzer enAnalyzer;

    @Autowired
    @Qualifier("russianAnalyzer")
    private Analyzer ruAnalyzer;

    private final String splitQueryRegexp = "\\.?,?\\s+|\\+";

    public Analyzer getAnalyzers()
    {
        Map<String, Analyzer> analyzers = new HashMap<>();
        analyzers.put(LuceneBuilder.ENG_TITLE, enAnalyzer);
        analyzers.put(LuceneBuilder.ENG_ARTICLE, enAnalyzer);
        analyzers.put(LuceneBuilder.RUS_TITLE, ruAnalyzer);
        analyzers.put(LuceneBuilder.RUS_ARTICLE, ruAnalyzer);

        return new PerFieldAnalyzerWrapper(stAnalyzer, analyzers);
    }

    public List<String> getFields()
    {
        List<String> fields = new ArrayList<>();
        fields.add(LuceneBuilder.TITLE);
        fields.add(LuceneBuilder.ARTICLE);
        fields.add(LuceneBuilder.ENG_TITLE);
        fields.add(LuceneBuilder.ENG_ARTICLE);
        fields.add(LuceneBuilder.RUS_TITLE);
        fields.add(LuceneBuilder.RUS_ARTICLE);

        return fields;
    }

    public String[] getTitles()
    {
        return new String[]{
                LuceneBuilder.TITLE, LuceneBuilder.ENG_TITLE, LuceneBuilder.RUS_TITLE
        };
    }

    public String[] getArticles()
    {
        return new String[]{
                LuceneBuilder.ARTICLE, LuceneBuilder.ENG_ARTICLE, LuceneBuilder.RUS_ARTICLE
        };
    }

    private String getFinalQuery(String query)
    {
        String[] queryArr = query.split(splitQueryRegexp);
        int length = queryArr.length;
        double countGreatWords = Math.round(length * LuceneBuilder.PERCENT_GREAT_WORDS / 100D);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            if (i == 0)
            {
                res.append(queryArr[i])
                        .append("~")
                        .append(LuceneBuilder.COUNT_FUZZY_SEARCH)
                        .append("^3 ");
                continue;
            }

            if (i >= countGreatWords)
            {
                res.append(queryArr[i])
                        .append("~")
                        .append(LuceneBuilder.COUNT_FUZZY_SEARCH)
                        .append("^1 ");
                continue;
            }

            res.append(queryArr[i])
                    .append("~")
                    .append(LuceneBuilder.COUNT_FUZZY_SEARCH)
                    .append("^2 ");
        }

        return res.toString().trim();
    }

    public String getParseQuery(String query)
    {
        String finalQuery = getFinalQuery(query);

        StringBuilder str = new StringBuilder();
        getFields().stream()
                .forEach(e -> str.append(e)
                        .append(":")
                        .append("(")
                        .append(finalQuery)
                        .append(")")
                        .append(" OR ")
                );

        return str.delete(str.length() - 4, str.length()).toString();
    }
}
