package com.lab.html_editor.utils.visitor.html_visitor;

public interface HtmlVisitable {
    void accept(HtmlVisitor visitor);
}
