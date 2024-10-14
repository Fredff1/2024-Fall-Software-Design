package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlDiv extends HtmlComposite{
    public HtmlDiv(String id){
        super(id,HtmlTagName.DIV);
    }

    public HtmlDiv(String id,HtmlText text){
        super(id,HtmlTagName.DIV,text);
    }
}
