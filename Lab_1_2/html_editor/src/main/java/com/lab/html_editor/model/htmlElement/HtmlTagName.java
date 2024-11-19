package com.lab.html_editor.model.htmlElement;

public class HtmlTagName {
    private final HtmlTagNameEnum tagName;
    private final String tagString;

    HtmlTagName(String tagString){
        this.tagString = tagString;
        this.tagName=HtmlTagNameEnum.fromString(tagString);
    }

    

    public String getTagString(){
        return tagString;
    }

    public HtmlTagNameEnum getTagNameEnum(){
        return tagName;
    }

    

    
}
