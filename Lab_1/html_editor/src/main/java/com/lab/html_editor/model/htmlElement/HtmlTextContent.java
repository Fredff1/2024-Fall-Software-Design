package com.lab.html_editor.model.htmlElement;

public class HtmlTextContent {
    private String text;
    
    public HtmlTextContent(String text){
        this.text=text;
    }

    public void setText(String text){
        this.text=text;
    }

    public String getText(){
        return this.text;
    }

}
