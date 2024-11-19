package com.lab.html_editor.utils.factory.html_factory;


import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;

public class BasicHtmlAttributeFactory implements HtmlAttributeFactory{
    

    public BasicHtmlAttributeFactory(){
        
    }
    @Override
    public HtmlAttribute createAttribute(String name,String value,String... properties){
       
        HtmlAttribute attribute=null;
        switch(name.toLowerCase()){
            case "id":
            attribute=new HtmlIdAttribute(value);
            break;
            default:
            attribute=new HtmlAttribute(name, value);
            break;
        }
        
        return attribute; 
    }
}
