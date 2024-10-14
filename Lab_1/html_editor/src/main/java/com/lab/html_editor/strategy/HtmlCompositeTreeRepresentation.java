package com.lab.html_editor.strategy;

import com.lab.html_editor.model.htmlElement.*;
import com.lab.html_editor.model.TreeNode;

public class HtmlCompositeTreeRepresentation implements HtmlRepresentationStrategy{
    @Override
    public String toStringRepresentation(HtmlElement element, int indentLevel) {
        if(!(element instanceof HtmlComposite)){
            throw new IllegalArgumentException("Unsupported composite strategy");
        }
       
        StringBuilder sb = new StringBuilder();
        boolean[] flag={false};
        String indent = buildIndent(indentLevel, flag);  // 根节点不需要竖线

        // 根节点处理
        
        sb.append(indent).append(element.getTagName().toTagString()).append("\n");

        // 递归处理子元素
        if(element instanceof HtmlComposite){
            var children=((HtmlComposite)element).getChildren();
            if (!children.isEmpty()) {
                int childCount = children.size();
                for (int i = 0; i < childCount; i++) {
                    TreeNode child = children.get(i);
                    boolean isLastChild = (i == childCount - 1);  // 判断是否是最后一个子元素
                    sb.append(buildChildRepresentation(child, indentLevel, isLastChild, new boolean[indentLevel]));
                }
            }
        }
        

        return sb.toString();
    }

    private String buildChildRepresentation(TreeNode child, int indentLevel, boolean isLastChild, boolean[] drawLineForParent) {
        
        
        StringBuilder sb = new StringBuilder();
        String indent = buildIndent(indentLevel, drawLineForParent);

        // 添加树形符号 ├── 或 └──
        sb.append(indent);
        sb.append(isLastChild ? "└── " : "├── ");

        // 显示子节点标签名和内容
        if (child instanceof HtmlComposite) {
            HtmlComposite childElement = (HtmlComposite) child;
            sb.append(childElement.getTagName().toTagString()).append("\n");

            // 显示子节点的文本内容
            // if (childElement.getTextContent() != null && !childElement.getTextContent().getText().isEmpty()) {
            //     sb.append(buildIndent(indentLevel + 1, drawLineForParent))
            //       .append("└── ").append(childElement.getTextContent().getText()).append("\n");
            // }

            // 递归处理该子节点的子元素
            if (!childElement.getChildren().isEmpty()) {
                int childCount = childElement.getChildren().size();
                for (int i = 0; i < childCount; i++) {
                    TreeNode grandChild = childElement.getChildren().get(i);
                    boolean isGrandChildLast = (i == childCount - 1);
                    boolean[] newDrawLineForParent = new boolean[indentLevel + 1];
                    System.arraycopy(drawLineForParent, 0, newDrawLineForParent, 0, indentLevel);
                    newDrawLineForParent[indentLevel] = !isLastChild;
                    sb.append(buildChildRepresentation(grandChild, indentLevel + 1, isGrandChildLast, newDrawLineForParent));
                }
            }
        }
        return sb.toString();
    }

    private String buildIndent(int indentLevel, boolean[] drawLineForParent) {
        StringBuilder indentBuilder = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            if (i < drawLineForParent.length && drawLineForParent[i]) {
                indentBuilder.append("│   ");  // 父节点继续的竖线
            } else {
                indentBuilder.append("    ");  // 空白缩进
            }
        }
        return indentBuilder.toString();
    }
    
}
