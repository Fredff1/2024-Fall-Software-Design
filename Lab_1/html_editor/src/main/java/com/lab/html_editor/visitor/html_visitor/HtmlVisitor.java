package com.lab.html_editor.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;

public interface HtmlVisitor {
    void visit(HtmlComposite element);
    
} 
