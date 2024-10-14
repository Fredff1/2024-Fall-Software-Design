package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlSubtitle extends HtmlLeaf{

    public HtmlSubtitle(String id,HtmlTagName tagName,String text){
        super(id, tagName,text);
    }
    
    public HtmlSubtitle(String id,HtmlTagName tagName){
        super(id, tagName);
    }
}
