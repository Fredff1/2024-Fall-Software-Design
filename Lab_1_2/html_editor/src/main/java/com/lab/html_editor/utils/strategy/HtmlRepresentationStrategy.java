package com.lab.html_editor.utils.strategy;


import com.lab.html_editor.model.htmlElement.HtmlElement;

public interface HtmlRepresentationStrategy {
    String toStringRepresentation(HtmlElement element,int indent);
}
