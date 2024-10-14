package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlTagName;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;

public class HtmlAnchor extends HtmlComposite{
    public HtmlAnchor(String id) {
        super(id, HtmlTagName.ANCHOR);
    }

    public HtmlAnchor(String id,HtmlText text) {
        super(id, HtmlTagName.ANCHOR,text);
        
    }

    // 添加超链接
    public void setHref(String href) {
        addAttribute("href", href);
    }

    public HtmlAttribute getHref() {
        return getAttribute("href");
    }
}
