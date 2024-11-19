package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlLeaf;


public class HtmlListItem extends HtmlLeaf{
    public HtmlListItem(String id,String text){
        super(id, "li",text);
    }

    public HtmlListItem(String id){
        super(id, "li");
    }
}
