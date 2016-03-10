package com.spdukraine.testtask.search.kernel.lucene;

import com.spdukraine.testtask.search.exceptions.DirectoryNotCreatedException;
import com.spdukraine.testtask.search.helpers.IndexSearcherHelper;
import com.spdukraine.testtask.search.helpers.PathHelper;
import com.spdukraine.testtask.search.kernel.builder.LuceneBuilder;
import com.spdukraine.testtask.search.kernel.pojo.JsoupParseResult;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class LuceneIndexer
{
    @Autowired
    private PathHelper pHelper;

    @Autowired
    private IndexSearcherHelper isHelper;

    private IndexWriter writer;

    private FSDirectory directory;

    private static final Logger LOGGER = LoggerFactory.getLogger(LuceneIndexer.class);

    public void index(Set<JsoupParseResult> setResult) throws IOException, DirectoryNotCreatedException
    {
        LOGGER.info("index()");

        makeIndexWriter();

        setResult.parallelStream()
                .filter(e ->
                        !e.getUrl().trim().isEmpty() ||
                                !e.getTitle().trim().isEmpty() ||
                                !e.getArticle().trim().isEmpty()
                ).forEach(e ->
        {
            try
            {
                addLuceneIndex(e.getUrl(), e.getTitle(), e.getArticle());
            } catch (IOException ex)
            {
                LOGGER.error(ex.getMessage());
            }
        });

        close();
    }

    private void makeIndexWriter() throws DirectoryNotCreatedException, IOException
    {
        IndexWriterConfig config = new IndexWriterConfig(isHelper.getAnalyzers());
        config.setOpenMode(getOpenMode());
        directory = FSDirectory.open(pHelper.getLucenePath());
        writer = new IndexWriter(directory, config);
    }

    private void addLuceneIndex(String url, String title, String text) throws IOException
    {
        Document document = new Document();
        document.add(new StringField(LuceneBuilder.URL, url, Field.Store.YES));
        document.add(new TextField(LuceneBuilder.TITLE, title, Field.Store.YES));
        document.add(new TextField(LuceneBuilder.ARTICLE, text, Field.Store.YES));
        document.add(new SortedDocValuesField(LuceneBuilder.TITLE, new BytesRef(title)));

        document.add(new TextField(LuceneBuilder.ENG_TITLE, title, Field.Store.NO));
        document.add(new TextField(LuceneBuilder.ENG_ARTICLE, text, Field.Store.NO));

        document.add(new TextField(LuceneBuilder.RUS_TITLE, title, Field.Store.NO));
        document.add(new TextField(LuceneBuilder.RUS_ARTICLE, text, Field.Store.NO));

        writer.addDocument(document);
    }

    private IndexWriterConfig.OpenMode getOpenMode()
    {
        return LuceneBuilder.OPEN_MODE;
    }

    private void close()
    {
        try
        {
            writer.commit();
            writer.close();
            directory.close();
        } catch (IOException ex)
        {
            LOGGER.error(ex.getMessage());
        }finally
        {
            writer = null;
            directory = null;
        }
    }
}
