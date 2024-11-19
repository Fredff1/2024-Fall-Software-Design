package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;

public interface HtmlVisitor {
    void visit(HtmlComposite composite);
    void visit(HtmlLeaf leaf);
    
} 
