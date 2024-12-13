package com.lab.html_editor.model.htmlElement.HtmlAttributes;


/**
 * Html属性
 */
public class HtmlAttribute {
    private String name;
    private String value;

    public HtmlAttribute(String name, String value){
        this.name=name;
        this.value=value;
    }

    public String getName(){
        return this.name;
    }

    public String getValue(){
        return this.value;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setValue(String value){
        this.value=value;
    }

    public String toString() {
        return name + "=\"" + value + "\"";
    }
}
