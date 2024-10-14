package com.lab.html_editor.strategy;

import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;

public class HtmlLeafIndentedRepresenationStrategy implements HtmlRepresentationStrategy{
    @Override
    public String toStringRepresentation(HtmlElement element,int indentLevel){
        if (!(element instanceof HtmlLeaf)) {
            throw new IllegalArgumentException("Unsupported Leaf strategy");
        }
        
        // 缩进处理
        String indent = " ".repeat(indentLevel * 2);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        // 开始标签
        sb.append(indent).append("<").append(element.getTagName().toTagString());

        // 添加属性
        if (!element.getAttributes().isEmpty()) {
            for (var entry : element.getAttributes()) {
                if(element instanceof HtmlFixedElement && entry instanceof HtmlIdAttribute){
                    continue; // title html head body 不显示默认的id
                }
                sb.append(" ").append(entry.toString());
            }
        }
        sb.append(">");

        // 添加叶节点的文本内容（如果有）
        var text=((HtmlLeaf)element).getText();
        if(text!=null){
            sb.append(text);
        }

        // 结束标签
        sb.append("</").append(element.getTagName().toTagString()).append(">");

        return sb.toString();
    }
}

