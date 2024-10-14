package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlTop extends HtmlComposite implements HtmlFixedElement{
    public HtmlTop(){
        super("html",  HtmlTagName.HTMLTOP);
    }
}
