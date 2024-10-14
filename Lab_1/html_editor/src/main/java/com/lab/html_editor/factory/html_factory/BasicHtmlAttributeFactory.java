package com.lab.html_editor.factory.html_factory;

import java.util.HashMap;
import java.util.Map;

import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;

public class BasicHtmlAttributeFactory implements HtmlAttributeFactory{
    private Map<String,HtmlAttribute> exitstingAttributes;

    public BasicHtmlAttributeFactory(){
        this.exitstingAttributes=new HashMap<>();
    }
    @Override
    public HtmlAttribute createAttribute(String name,String value,String... properties){
        if(exitstingAttributes.containsKey(name)){
            throw new IllegalArgumentException("The name of attribute already exists!");
        }
        HtmlAttribute attribute=null;
        switch(name.toLowerCase()){
            case "id":
            attribute=new HtmlIdAttribute(value);
            break;
            default:
            attribute=new HtmlAttribute(name, value);
            break;
        }
        
        this.exitstingAttributes.put(name, attribute);
        return attribute; 
    }
}
