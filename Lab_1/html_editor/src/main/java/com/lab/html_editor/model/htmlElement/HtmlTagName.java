package com.lab.html_editor.model.htmlElement;

public enum HtmlTagName {
    DIV("div"), 
    ANCHOR("a"), 
    PARAGRAPH("p"), 
    SPAN("span"), 
    TEXT("text"), 
    BODY("body"), 
    HEAD("head"), 
    TITLE("title"), 
    HTMLTOP("html"),
    UNORDEREDLIST("ul"),
    LISTITEM("li"),
    H1("h1"),
    H2("h2"),
    H3("h3"),
    H4("h4"),
    H5("h5"),
    H6("h6"),
    CUSTOMLEAF("custom_leaf"),
    CUSTOMCOMPOSITE("custom_composite");  

    private String tagString;

    HtmlTagName(String tagString){
        this.tagString = tagString;
    }

    public void setCustomTagString(String customTag) {
        if (this == CUSTOMLEAF || this==CUSTOMCOMPOSITE) {
            this.tagString = customTag;
        } else {
            throw new UnsupportedOperationException("Cannot set custom tag for predefined HtmlTagName.");
        }
    }

    
    public String toTagString() {
        return this.tagString;
    }

    public static HtmlTagName fromString(String tagString) {
        // 遍历所有的枚举值，查找匹配的 tagString
        for (HtmlTagName tag : HtmlTagName.values()) {
            if (tag.tagString.equalsIgnoreCase(tagString)) {
                return tag;
            }
        }
        // 如果没有找到匹配的，返回 CUSTOM 并设置自定义 tagString
        HtmlTagName customTag = HtmlTagName.CUSTOMCOMPOSITE;
        customTag.setCustomTagString(tagString);
        return customTag;
    }

    
}
