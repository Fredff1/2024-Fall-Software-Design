package com.lab;

import com.lab.html_editor.factory.html_factory.BasicHtmlFactory;
import com.lab.html_editor.factory.html_factory.HtmlDocumentFactory;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.strategy.HtmlCompositeTreeRepresentation;

public class Main {

    private HtmlDocumentFactory docuFactory;
    private BasicHtmlFactory elemFactory;
    private HtmlDocument document;

    public Main(){
        docuFactory=new HtmlDocumentFactory();
        elemFactory=new BasicHtmlFactory();
        document=docuFactory.createHtmlDocument("test","mytest");
    }

    public void testprint(){
        var output_indented=document.toHtmlString(0);
        System.out.println(output_indented);
        document.getRoot().setRepresentationStrategy(new HtmlCompositeTreeRepresentation());
        var output_tree=document.toHtmlString(0);
        System.out.println(output_tree);
    }
    public static void main(String[] args) {
        Main main=new Main();
        main.testprint();
    }
}