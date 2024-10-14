package com.lab.html_editor.strategy;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;

public class HtmlLeafTreeRepresentationStrategy implements HtmlRepresentationStrategy{
    @Override
    public String toStringRepresentation(HtmlElement element,int indentLevel){
        if(!(element instanceof HtmlLeaf)){
            throw new IllegalArgumentException("Unsupported Leaf strategy");
        }
        return ((HtmlLeaf)element).getText();
    }
}
