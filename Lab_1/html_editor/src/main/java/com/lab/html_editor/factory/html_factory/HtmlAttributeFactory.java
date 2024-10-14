package com.lab.html_editor.factory.html_factory;

import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;

public interface HtmlAttributeFactory {
    public HtmlAttribute createAttribute(String name,String value,String... properties);
}
