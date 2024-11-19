package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;

public class HtmlAnchor extends HtmlComposite{
    public HtmlAnchor(String id) {
        super(id, "a");
    }

    public HtmlAnchor(String id,HtmlText text) {
        super(id, "a",text);
        
    }

   
    public void setHref(String href) {
        addAttribute("href", href);
    }

    public HtmlAttribute getHref() {
        return getAttribute("href");
    }
}
