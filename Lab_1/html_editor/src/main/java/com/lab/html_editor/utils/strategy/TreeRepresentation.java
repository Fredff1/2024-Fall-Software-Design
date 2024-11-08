package com.lab.html_editor.utils.strategy;

import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.adapter.TreeCompositeAdapter;
import com.lab.html_editor.utils.adapter.TreeLeafAdapter;
import com.lab.html_editor.utils.adapter.TreeNodeAdapter;

public abstract class TreeRepresentation implements RepresentationStrategy{
    

    protected void buildRepresentation(TreeNodeAdapter element, StringBuilder sb, List<Boolean> isLastList, boolean isRoot) {
        if (!isRoot) {
            sb.append(buildIndent(isLastList));
        }

        if (element instanceof TreeCompositeAdapter) {
            TreeCompositeAdapter composite = (TreeCompositeAdapter) element;
           
            sb.append(composite.getFeature());

                    
            sb.append("\n");   

            List<TreeNodeAdapter> children = composite.getChildren();


            for (int i = 0; i < children.size(); i++) {
                TreeNodeAdapter child = children.get(i);
                boolean isLast = (i == children.size() - 1);
                isLastList.add(isLast);
                buildRepresentation(child, sb, isLastList, false);
                isLastList.remove(isLastList.size() - 1);
            }
        } else if (element instanceof TreeLeafAdapter) {
            TreeLeafAdapter leaf = (TreeLeafAdapter) element;

            if(leaf.isTextNode()){
                String text=leaf.getText().trim();
                sb.append(text);
            }else{
                sb.append(leaf.getFeature());
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

    protected String buildIndent(List<Boolean> isLastList) {
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