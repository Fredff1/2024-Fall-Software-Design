package com.lab.html_editor.model.htmlElement.concreteHtmlElements;


import com.lab.html_editor.model.htmlElement.HtmlLeaf;


public class HtmlParagraph extends HtmlLeaf{
    public HtmlParagraph(String id){
        super(id,  "p");
    }

    public HtmlParagraph(String id,String text){
        super(id,  "p",text);
    }
}
