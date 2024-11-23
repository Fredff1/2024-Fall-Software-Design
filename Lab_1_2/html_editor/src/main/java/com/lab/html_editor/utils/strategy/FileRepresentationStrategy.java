package com.lab.html_editor.utils.strategy;

import com.lab.html_editor.model.FileElement.AbstractFileNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;

public interface FileRepresentationStrategy {
    String toStringRepresentation(AbstractFileNode element);
}
