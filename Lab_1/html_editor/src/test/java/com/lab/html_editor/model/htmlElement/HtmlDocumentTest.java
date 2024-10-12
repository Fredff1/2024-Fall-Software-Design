package com.lab.html_editor.model.htmlElement;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.factory.html_factory.HtmlDocumentFactory;
import com.lab.html_editor.factory.html_factory.HtmlElementFactory;
import com.lab.html_editor.strategy.TreeHtmlRepresentation;

public class HtmlDocumentTest {
    private HtmlDocumentFactory docuFactory;
    private HtmlElementFactory elemFactory;
    private HtmlDocument document;

    @Before
    public void setUp(){
        docuFactory=new HtmlDocumentFactory();
        elemFactory=new BasicHtmlFactory();
        document=docuFactory.createHtmlDocument("test","mytest");
    }

    @Test
    public void testInit(){
        assertEquals(document.getRoot().getId(), "root");
    }

    @Test
    public void testprint(){
        var output_indented=document.toHtmlString(0);
        System.out.println(output_indented);
        document.getRoot().setRepresentationStrategy(new TreeHtmlRepresentation());
        var output_tree=document.toHtmlString(0);
        System.out.println(output_tree);
        
    }
}
