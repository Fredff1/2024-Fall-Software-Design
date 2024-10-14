package com.lab.html_editor.model.htmlElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.factory.html_factory.HtmlDocumentFactory;
import com.lab.html_editor.strategy.HtmlCompositeTreeRepresentation;

public class HtmlDocumentTest {
    private HtmlDocumentFactory docuFactory;
    private BasicHtmlFactory elemFactory;
    private HtmlDocument document;

    @Before
    public void setUp(){
        docuFactory=new HtmlDocumentFactory();
        elemFactory=new BasicHtmlFactory();
        document=docuFactory.createHtmlDocument("test","mytest");
    }

    

    @Test
    public void testprint(){
        var output_indented=document.toHtmlString(0);
        System.out.println(output_indented);
        document.getRoot().setRepresentationStrategy(new HtmlCompositeTreeRepresentation());
        var output_tree=document.toHtmlString(0);
        System.out.println(output_tree);
    }

    @Test 
    public void testAddElement(){
        document.append("h1","sub_title_1","body","This is the title");
        document.append("p","descreption","body","this is a paragraph");
        document.append("ul", "list_1", "body", "This is a list");
        document.append("li", "li_it1", "list_1", "first");
        document.append("li", "li_it2", "list_1", "second");
        System.out.println(document.toHtmlString(0));
    }
}
