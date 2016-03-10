package com.spdukraine.testtask.search.kernel.lucene;

import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.helpers.IndexSearcherHelper;
import com.spdukraine.testtask.search.helpers.PathHelper;
import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.kernel.pojo.LuceneSearchResult;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LuceneSearcher
{
    @Autowired
    private PathHelper pHelper;

    @Autowired
    private IndexSearcherHelper isHelper;

    private FSDirectory directory;

    private IndexReader reader;

    private Integer totalHits = LuceneBuilder.NUM_HITS;

    private Integer capacity = LuceneBuilder.NUM_HITS;

    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneSearcher.class);

    public List<LuceneSearchResult> search(String queryStr, Integer currentHits, Boolean alphabetic) throws ParseException,
            IOException, InvalidTokenOffsetsException, DirectoryNotCreatedException
    {
        QueryParser parser = new QueryParser(LuceneBuilder.TITLE, isHelper.getAnalyzers());
        Query query = parser.parse(isHelper.getParseQuery(queryStr));

        int numHits = LuceneBuilder.NUM_HITS + currentHits;
        TopFieldCollector collector = getCollector(alphabetic, numHits);
        IndexSearcher searcher = getIndexSearcher();
        searcher.search(query, collector);
        totalHits = collector.getTotalHits();

        ScoreDoc[] scoreDocs = getScoreDocs(collector, currentHits, numHits);
        List<LuceneSearchResult> list = new ArrayList<>(capacity);

        for (ScoreDoc scoreDoc : scoreDocs)
        {
            Document d = searcher.doc(scoreDoc.doc);
            String hTitle = highlightString(query, d.get(LuceneBuilder.TITLE), isHelper.getTitles());
            String hArticle = highlightString(query, d.get(LuceneBuilder.ARTICLE), isHelper.getArticles());

            list.add(addToList(d.get(LuceneBuilder.URL), hTitle, hArticle));
        }
        close();

        return list;
    }

    public Integer getTotalHits()
    {
        return totalHits;
    }

    private Sort getSort(Boolean alphabetic)
    {
        if (alphabetic)
            return new Sort(new SortField(LuceneBuilder.TITLE, SortField.Type.STRING));
        return Sort.RELEVANCE;
    }

    private IndexReader getReader() throws DirectoryNotCreatedException, IOException
    {
        directory = FSDirectory.open(pHelper.getLucenePath());

        return DirectoryReader.open(directory);
    }

    private IndexSearcher getIndexSearcher() throws DirectoryNotCreatedException, IOException
    {
        reader = getReader();

        return new IndexSearcher(reader);
    }

    private TopFieldCollector getCollector(Boolean alphabetic, Integer numHits) throws IOException
    {
        return TopFieldCollector.create(
                getSort(alphabetic),
                numHits, true, true, false);
    }

    private ScoreDoc[] getScoreDocs(TopFieldCollector collector, Integer currentHits, Integer numHits)
    {
        if (numHits < totalHits)
            return collector.topDocs(currentHits, numHits).scoreDocs;

        capacity = totalHits - currentHits;
        return collector.topDocs(currentHits, totalHits).scoreDocs;

    }

    private LuceneSearchResult addToList(String url, String highlightTitle, String highlightArticle)
    {
        return new LuceneSearchResult(url, highlightTitle, highlightArticle);
    }

    private String highlightString(Query parseQuery, String text, String[] fields) throws IOException,
            InvalidTokenOffsetsException
    {
        QueryScorer scorer = new QueryScorer(parseQuery);
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(LuceneBuilder.START_TAG, LuceneBuilder.FINISH_TAG);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer, LuceneBuilder.FRAGMENT_SIZE));

        for (String field : fields)
        {
            String highlighted = highlighter.getBestFragment(isHelper.getAnalyzers(), field, text);
            if (highlighted != null)
                return highlighted;
        }

        return text.length() > LuceneBuilder.FRAGMENT_SIZE
                ? text.substring(0, LuceneBuilder.FRAGMENT_SIZE) : text;
    }

    private void close()
    {
        try
        {
            reader.close();
            directory.close();
        } catch (IOException ex)
        {
            LOGGER.error(ex.getMessage());
        }finally
        {
            reader = null;
            directory = null;
        }
    }
}
