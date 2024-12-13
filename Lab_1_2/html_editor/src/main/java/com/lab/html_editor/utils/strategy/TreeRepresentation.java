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
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.TreeCompositeProvider;
import com.lab.html_editor.utils.adapter.provider.TreeLeafProvider;
import com.lab.html_editor.utils.factory.adapter_Factory.TreeAdapterFactory;

public abstract class TreeRepresentation implements RepresentationStrategy{
    

    protected void buildRepresentation(Adapter element, StringBuilder sb, List<Boolean> isLastList, boolean isRoot) {
        if (!isRoot) {
            sb.append(buildIndent(isLastList));
        }

        if (element instanceof TreeCompositeProvider) {
            
            var compositeAdapter=(TreeCompositeProvider)element;
           
            sb.append(compositeAdapter.getFeature());

                    
            sb.append("\n");   

            List<TreeNode> children = compositeAdapter.getChildren();


            for (int i = 0; i < children.size(); i++) {
                TreeNode child = children.get(i);
                Adapter childAdapter=TreeAdapterFactory.createAdapter(child);
                if(childAdapter instanceof TreeLeafProvider){
                    var leafAdapter=(TreeLeafProvider)childAdapter;
                    if(leafAdapter.isTextNode()&&leafAdapter.getText().trim().isEmpty()){
                        continue;
                    }
                }
                boolean isLast = (i == children.size() - 1);
                isLastList.add(isLast);
                buildRepresentation(childAdapter, sb, isLastList, false);
                isLastList.remove(isLastList.size() - 1);
            }
        } else if (element instanceof TreeLeafProvider) {
            var leafAdapter=(TreeLeafProvider)element;
            
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