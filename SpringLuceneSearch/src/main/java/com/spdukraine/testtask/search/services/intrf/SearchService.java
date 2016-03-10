package com.spdukraine.testtask.search.services.intrf;

import com.spdukraine.testtask.search.components.FormComponentSwitcherSearch;
import com.spdukraine.testtask.search.components.ResponseComponentSearch;
import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.kernel.pojo.LuceneSearchResult;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface SearchService extends Service
{
    List<LuceneSearchResult> searchByWords(String searchWords, Integer currentHits, Boolean alphabetic)
            throws ParseException, DirectoryNotCreatedException, IOException, InvalidTokenOffsetsException;

    Number countArticle();

    String[] listToArray(List<LuceneSearchResult> results);

    ResponseComponentSearch getResponseComponentSearch();

    FormComponentSwitcherSearch getSwitcherFormComponent();

    void fillModel(Model model, HttpServletRequest request);
}
