package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlSpan extends HtmlComposite{
    public HtmlSpan(String id){
        super(id,  HtmlTagName.SPAN);
    }

    public HtmlSpan(String id,HtmlText text){
        super(id,HtmlTagName.SPAN,text);
    }
}
