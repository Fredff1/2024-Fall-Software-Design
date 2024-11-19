package com.lab.html_editor.model.htmlElement;

public enum HtmlTagNameEnum {
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

    public String tagString;

    HtmlTagNameEnum(String tagString){
        this.tagString = tagString;
    }

    

    
    public String toTagString() {
        return this.tagString;
    }

    public static HtmlTagNameEnum fromString(String tagString) {
        // 遍历所有的枚举值，查找匹配的 tagString
        for (HtmlTagNameEnum tag : HtmlTagNameEnum.values()) {
            if (tag.tagString.equalsIgnoreCase(tagString)) {
                return tag;
            }
        }
        // 如果没有找到匹配的，返回 CUSTOM 并设置自定义 tagString
        return HtmlTagNameEnum.CUSTOMCOMPOSITE;  // 用作自定义标签的默认标志;
    }
}
