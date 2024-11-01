package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;


public class HtmlDiv extends HtmlComposite{
    public HtmlDiv(String id){
        super(id,"div");
    }

    public HtmlDiv(String id,HtmlText text){
        super(id,"div",text);
    }
}
