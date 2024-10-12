package com.lab.html_editor.visitor.html_visitor;

public interface HtmlVisitable {
    void accept(HtmlVisitor visitor);
}
