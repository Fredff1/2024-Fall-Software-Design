package com.lab.html_editor.model.htmlElement.concreteHtmlElements;



import com.lab.html_editor.model.htmlElement.HtmlElement;

public class TextElement extends HtmlElement{


    public TextElement(String id,  String textContent) {
        super(id,  null,textContent);  // 文本节点没有标签名

    }
  

    @Override
    public String toStringRepresentation(int indentLevel) {
        
        return getTextContent().getText();  // 文本节点直接返回内容
    }

    

}
