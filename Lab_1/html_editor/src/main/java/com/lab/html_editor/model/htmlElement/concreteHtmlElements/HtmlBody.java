package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlBody extends HtmlComposite implements HtmlFixedElement{
    public HtmlBody(){
        super("body", HtmlTagName.BODY);
    }


}
