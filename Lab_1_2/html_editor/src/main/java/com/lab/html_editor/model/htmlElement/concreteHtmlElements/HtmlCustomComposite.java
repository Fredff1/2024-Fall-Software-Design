package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;


public class HtmlCustomComposite extends HtmlComposite{
    public HtmlCustomComposite(String id,String customTagName){
        super(id, customTagName);
        
    }

    public HtmlCustomComposite(String id,String customTagName,HtmlText text){
        super(id, customTagName,text);
        
    }


}
