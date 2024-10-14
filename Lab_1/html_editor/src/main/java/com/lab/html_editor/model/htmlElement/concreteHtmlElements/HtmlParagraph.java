package com.lab.html_editor.model.htmlElement.concreteHtmlElements;


import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlParagraph extends HtmlLeaf{
    public HtmlParagraph(String id){
        super(id,  HtmlTagName.PARAGRAPH);
    }

    public HtmlParagraph(String id,String text){
        super(id,  HtmlTagName.PARAGRAPH,text);
    }
}
