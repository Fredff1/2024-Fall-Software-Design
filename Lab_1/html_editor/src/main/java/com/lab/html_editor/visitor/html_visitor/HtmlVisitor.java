package com.lab.html_editor.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlElement;

public interface HtmlVisitor {
    void visit(HtmlElement element);
    
} 
