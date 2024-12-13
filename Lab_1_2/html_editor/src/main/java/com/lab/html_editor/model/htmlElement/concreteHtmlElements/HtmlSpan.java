package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;


public class HtmlSpan extends HtmlComposite{
    public HtmlSpan(String id){
        super(id,  "span");
    }

    public HtmlSpan(String id,HtmlText text){
        super(id,"span",text);
    }
}
