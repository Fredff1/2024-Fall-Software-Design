package com.lab.html_editor.strategy;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;

public interface HtmlRepresentationStrategy {
    String toStringRepresentation(HtmlElement element,int indentLevel);
}
