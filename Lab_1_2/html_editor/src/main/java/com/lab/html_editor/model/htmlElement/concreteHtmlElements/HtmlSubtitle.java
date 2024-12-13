package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlLeaf;


public class HtmlSubtitle extends HtmlLeaf{

    public HtmlSubtitle(String id,String tagName,String text){
        super(id, tagName,text);
    }
    
    public HtmlSubtitle(String id,String tagName){
        super(id, tagName);
    }
}
