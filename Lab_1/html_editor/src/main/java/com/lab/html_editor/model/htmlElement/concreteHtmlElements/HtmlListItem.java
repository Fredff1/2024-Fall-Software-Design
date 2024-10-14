package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlListItem extends HtmlLeaf{
    public HtmlListItem(String id,String text){
        super(id, HtmlTagName.LISTITEM,text);
    }

    public HtmlListItem(String id){
        super(id, HtmlTagName.LISTITEM);
    }
}
