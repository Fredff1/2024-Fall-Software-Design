package com.lab.html_editor.model.htmlElement.concreteHtmlElements;


import com.lab.html_editor.model.htmlElement.HtmlLeaf;


public class HtmlCustomLeaf extends HtmlLeaf{



    public HtmlCustomLeaf(String id,String customTagName){
        super(id, customTagName);
        
    }

    public HtmlCustomLeaf(String id,String customTagName,String text){
        super(id, customTagName,text);
        
    }

    public String toStringRepresentation(int indentLevel){
        return null;
    }

 

  
}
