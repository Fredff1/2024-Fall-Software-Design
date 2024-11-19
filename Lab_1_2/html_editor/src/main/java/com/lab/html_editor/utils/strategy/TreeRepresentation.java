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
import com.lab.html_editor.utils.adapter.TreeHtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.TreeHtmlLeafAdapter;

public abstract class TreeRepresentation implements RepresentationStrategy{
    

    protected void buildRepresentation(TreeNode element, StringBuilder sb, List<Boolean> isLastList, boolean isRoot) {
        if (!isRoot) {
            sb.append(buildIndent(isLastList));
        }

        if (element instanceof TreeComposite) {
            TreeComposite composite = (TreeComposite) element;
            var compositeAdapter=new TreeHtmlCompositeAdapter((HtmlComposite)composite, isRoot);
           
            sb.append(compositeAdapter.getFeature());

                    
            sb.append("\n");   

            List<TreeNode> children = compositeAdapter.getChildren();


            for (int i = 0; i < children.size(); i++) {
                TreeNode child = children.get(i);
                
                boolean isLast = (i == children.size() - 1);
                isLastList.add(isLast);
                buildRepresentation(child, sb, isLastList, false);
                isLastList.remove(isLastList.size() - 1);
            }
        } else if (element instanceof TreeLeaf) {
            TreeLeaf leaf = (TreeLeaf) element;
            var leafAdapter=new TreeHtmlLeafAdapter((HtmlLeaf)leaf, isRoot);
            
            if(leafAdapter.isTextNode()){
                 
                String text=leafAdapter.getText().trim();
                if(text!=null&&!text.isEmpty()){
                    sb.append(text);
                }
                
                sb.append("\n");
                
                
            }else{
                sb.append(leafAdapter.getFeature());
                sb.append("\n");  
                if (leafAdapter.getText() != null && !leafAdapter.getText().trim().isEmpty()) {
                    isLastList.add(true);
                    sb.append(buildIndent(isLastList))
                            .append(leafAdapter.getText().trim())
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