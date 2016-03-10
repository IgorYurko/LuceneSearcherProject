package com.spdukraine.testtask.search.services.impl;

import com.spdukraine.testtask.search.components.FormComponentSearch;
import com.spdukraine.testtask.search.components.FormComponentSwitcherSearch;
import com.spdukraine.testtask.search.components.ResponseComponentSearch;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.pojo.LuceneSearchResult;
import com.spdukraine.testtask.search.kernel.lucene.LuceneSearcher;
import com.spdukraine.testtask.search.services.intrf.SearchService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService
{
    @Autowired
    private FormComponentSearch sFormComponent;

    @Autowired
    private FormComponentSwitcherSearch swFormComponent;

    @Autowired
    private LuceneSearcher searcher;

    @Autowired
    private ResponseComponentSearch responseComponentSearch;

    public FormComponentSearch getFormComponent()
    {
        return sFormComponent;
    }

    public FormComponentSwitcherSearch getSwitcherFormComponent()
    {
        return swFormComponent;
    }



    public List<LuceneSearchResult> searchByWords(String searchWords, Integer currentHits, Boolean alphabetic) throws ParseException,
            DirectoryNotCreatedException, IOException, InvalidTokenOffsetsException
    {
        return searcher.search(searchWords, currentHits, alphabetic);
    }

    public Double countArticle()
    {
        return Math.ceil(searcher.getTotalHits().doubleValue() / 10);
    }

    public String[] listToArray(List<LuceneSearchResult> results)
    {
        int size = results.size();
        String[] array = new String[size];
        for (int i = 0; i < size; i++)
        {
            LuceneSearchResult result = results.get(i);
            array[i] = "<b>" + result.getHighlightTitle() + "</b>" + "<br/>" +
                    "<a href='" + result.getUrl() + "'>" + result.getUrl() + "</a>" + "<br/>" + "<br/>" +
                    "<p>" + result.getHighlightArticle() + "</p>";
        }

        return array;
    }

    public ResponseComponentSearch getResponseComponentSearch()
    {
        return responseComponentSearch;
    }

    public void fillModel(Model model, HttpServletRequest request)
    {
        model.addAttribute("formComponent", sFormComponent);
        model.addAttribute("swFormComponent", swFormComponent);
        model.addAttribute("request", request);
        model.addAttribute("count", countArticle());
    }
}
