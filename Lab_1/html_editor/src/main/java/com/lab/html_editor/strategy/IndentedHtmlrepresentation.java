package com.lab.html_editor.strategy;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;


public class IndentedHtmlrepresentation implements HtmlRepresentationStrategy{
    
    
    
    @Override
    public String toStringRepresentation(HtmlElement element,int indentLevel){
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(indentLevel * 2);  // 缩进处理

        // 开始标签
        sb.append(indent).append("<").append(element.getTagName());

        // 添加属性
        if (!element.getAttributes().isEmpty()) {
            for (var entry : element.getAttributes()) {
                sb.append(" ").append(entry.getName()).append("=\"").append(entry.getValue()).append("\"");
            }
        }
        sb.append(">");

        sb.append(element.getTextContent().getText());

        // 递归生成子节点的 HTML 表示
        if (!element.getChildren().isEmpty()) {
            sb.append("\n");
            for (TreeNode child : element.getChildren()) {
                if (child instanceof HtmlElement) {
                    sb.append(((HtmlElement) child).getRepresentationStrategy().toStringRepresentation((HtmlElement) child, indentLevel + 1));
                }
            }
            sb.append(indent);  // 缩进结束标签与开始标签对齐
        }

        // 结束标签
        sb.append("</").append(element.getTagName()).append(">\n");

        return sb.toString();
    }
}
