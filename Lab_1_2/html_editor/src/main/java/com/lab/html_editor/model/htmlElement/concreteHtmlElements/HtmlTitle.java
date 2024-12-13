package com.lab.html_editor.model.htmlElement.concreteHtmlElements;



import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;


public class HtmlTitle extends HtmlLeaf implements HtmlFixedElement{

    public HtmlTitle(String title){
        super("title","title",title);
    }

}
