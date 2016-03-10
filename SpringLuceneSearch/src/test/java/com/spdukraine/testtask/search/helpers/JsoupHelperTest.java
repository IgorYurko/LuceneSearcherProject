package com.spdukraine.testtask.search.helpers;

import com.spdukraine.testtask.search.helpers.JsoupHelper;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsoupHelperTest
{

    public JsoupHelper helper = new JsoupHelper();

    public String base = "http://some.ua/site/";

    public String html = "<html>\n" +
            "<head>\n" +
            "    <title>Test title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>\n" +
            "    <a href='http://some.ua/site/one'>Go one</a>\n" +
            "    <a href='http://another.ua/site/two'>Go two</a>\n" +
            "    <a href='some/site/two'>Go three</a>\n" +
            "    Some text...\n" +
            "</h1>\n" +
            "</body>\n" +
            "</html>";


    @Test
    public void testExtractLinks() throws Exception
    {
        assertEquals(3, helper.extractLinks(html, base).size());
        Set<String> stringSet = helper.extractLinks(html, base);
        Set<String> collect = stringSet.parallelStream()
                .filter(e -> e.startsWith(base))
                .collect(Collectors.toSet());
        System.out.println(collect);
        assertTrue(helper.extractLinks(html, base).contains("http://another.ua/site/two"));
    }

    @Test
    public void testGetTitle() throws Exception
    {
        Document parse = helper.parse(html);
        String title = helper.getTitle(parse);

        assertEquals("Test title" ,title);
    }

    @Test
    public void testGetArticle() throws Exception
    {
        Document parse = helper.parse(html);
        String article = helper.getArticle(parse);

        assertTrue(article.contains("Some text..."));
        assertTrue(article.contains("Go one"));
    }
}