package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;


public class HtmlHead extends HtmlComposite implements HtmlFixedElement{
    public HtmlHead(){
        super("head", "head");
    }
}
