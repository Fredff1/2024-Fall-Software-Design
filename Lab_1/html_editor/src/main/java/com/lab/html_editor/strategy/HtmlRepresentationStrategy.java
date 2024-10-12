package com.lab.html_editor.strategy;

import com.lab.html_editor.model.HtmlElement;

public interface HtmlRepresentationStrategy {
    String toStringRepresentation(HtmlElement element,int indent);
}
