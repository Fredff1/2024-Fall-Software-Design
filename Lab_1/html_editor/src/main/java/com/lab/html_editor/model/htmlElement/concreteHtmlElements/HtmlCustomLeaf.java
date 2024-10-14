package com.lab.html_editor.model.htmlElement.concreteHtmlElements;


import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlCustomLeaf extends HtmlLeaf{



    public HtmlCustomLeaf(String id,String customTagName){
        super(id, HtmlTagName.CUSTOMLEAF);
        getTagName().setCustomTagString(customTagName);
    }

    public HtmlCustomLeaf(String id,String customTagName,String text){
        super(id, HtmlTagName.CUSTOMLEAF,text);
        getTagName().setCustomTagString(customTagName);
    }

    public String toStringRepresentation(int indentLevel){
        return null;
    }

 

  
}
