package com.lab.html_editor.utils.strategy;

import com.lab.html_editor.model.htmlElement.*;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;

import java.util.*;

import com.lab.html_editor.model.TreeNode;

/**
 * 树状打印策略
 */
public class HtmlTreeRepresentation implements HtmlRepresentationStrategy {

    private boolean showId=true;

    public HtmlTreeRepresentation(){

    }

    public HtmlTreeRepresentation(boolean showId){
        this.showId=showId;
    }

    public void setIdPrensent(boolean flag){
        this.showId=flag;
    }

    @Override
    public String toStringRepresentation(HtmlElement element, int indent) {
        StringBuilder sb = new StringBuilder();
        buildRepresentation(element, sb, new ArrayList<>(), true);
        return sb.toString();
    }

    private void buildRepresentation(HtmlElement element, StringBuilder sb, List<Boolean> isLastList, boolean isRoot) {

        if (!isRoot) {
            sb.append(buildIndent(isLastList));
        }

        if (element instanceof HtmlComposite) {
            HtmlComposite composite = (HtmlComposite) element;
            
            sb.append(composite.getTagName().getTagString());
            if(composite.hasSpellCheckErrors()){
                sb.append("[X]");
            }
            if(showId){
                sb.append(" #").append(composite.getId());
            }
                    
            sb.append("\n");   

            List<TreeNode> nonEmptyChildren = new ArrayList<>();
            int childrenSize = composite.getChildrenSize();
            for (int i = 0; i < childrenSize; i++) {
                TreeNode child = composite.getChild(i);
                if (child instanceof HtmlText && ((HtmlText) child).getText().trim().isEmpty()) {
                    continue;
                }
                nonEmptyChildren.add(child);
            }

            for (int i = 0; i < nonEmptyChildren.size(); i++) {
                TreeNode child = nonEmptyChildren.get(i);
                boolean isLast = (i == nonEmptyChildren.size() - 1);
                isLastList.add(isLast);
                buildRepresentation((HtmlElement) child, sb, isLastList, false);
                isLastList.remove(isLastList.size() - 1);
            }
        } else if (element instanceof HtmlLeaf) {
            HtmlLeaf leaf = (HtmlLeaf) element;
            if (leaf instanceof HtmlText) {
                String text = leaf.getText().trim();
                if (!text.isEmpty()) {
                    sb.append(text).append("\n");
                }
            } else {
                
                sb.append(leaf.getTagName().getTagString());
                if(leaf.hasSpellCheckErrors()){
                    sb.append("[X]");
                }
                if(showId){
                    sb.append(" #").append(leaf.getId());
                }
                        
                sb.append("\n");      

                if (leaf.getText() != null && !leaf.getText().trim().isEmpty()) {
                    isLastList.add(true);
                    sb.append(buildIndent(isLastList))
                            .append(leaf.getText().trim())
                            .append("\n");
                    isLastList.remove(isLastList.size() - 1);
                }
            }
        }
    }

    private String buildIndent(List<Boolean> isLastList) {
        StringBuilder indentBuilder = new StringBuilder();
        for (int i = 0; i < isLastList.size() - 1; i++) {
            if (isLastList.get(i)) {
                indentBuilder.append("    ");
            } else {
                indentBuilder.append("│   ");
            }
        }
        if (!isLastList.isEmpty()) {
            if (isLastList.get(isLastList.size() - 1)) {
                indentBuilder.append("└── ");
            } else {
                indentBuilder.append("├── ");
            }
        }
        return indentBuilder.toString();
    }

}
