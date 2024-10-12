package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlAttribute;
import com.lab.html_editor.model.htmlElement.HtmlElement;

public class AnchorElement extends HtmlElement{
    public AnchorElement(String id) {
        super(id, "a");
    }

    // 添加超链接
    public void setHref(String href) {
        addAttribute(new HtmlAttribute("href", href));
    }

    public HtmlAttribute getHref() {
        return getAttribute("href");
    }
}
