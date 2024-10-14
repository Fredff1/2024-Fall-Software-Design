package com.lab.html_editor.strategy;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlTitle;



public class HtmlCompositeIndentedRepresentation implements HtmlRepresentationStrategy{
    

    private boolean isFixedInstance(HtmlElement element){
        if(element instanceof HtmlFixedElement){
            return true;
        }
        return false;
    }
    
    @Override
    public String toStringRepresentation(HtmlElement element,int indentLevel){
        if(!(element instanceof HtmlComposite)){
            throw new IllegalArgumentException("Unsupported composite strategy");
        }
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(indentLevel * 2);  // 缩进处理

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


       

        // 递归生成子节点的 HTML 表示
        if(element instanceof HtmlComposite){
            var chileren=((HtmlComposite)element).getChildren();
            boolean has_change_line=false;
            
            if (!chileren.isEmpty()) {
                for ( int i=0;i<chileren.size();i++) {
                    TreeNode child=chileren.get(i);
                    if(child instanceof HtmlLeaf ){
                        sb.append(((HtmlLeaf)child).toStringRepresentation(indentLevel+1));
                        if(i==chileren.size()-1){
                            sb.append("\n");
                        }
                        sb.append(indent);
                    }else if (child instanceof HtmlComposite) {
                        if(has_change_line==false){
                            sb.append("\n");
                            has_change_line=true;
                        }

                        sb.append(((HtmlComposite) child).toStringRepresentation(indentLevel + 1));
                        if(i==chileren.size()-1){
                            sb.append(indent);  // 缩进结束标签与开始标签对齐
                        }
                        
                    }
                }
                if(chileren.size()>1|| !(chileren.get(0) instanceof HtmlText)){
                    //sb.append("\n").append(indent);
                }
                
            }
        }
        

        // 结束标签

        sb.append("</").append(element.getTagName().toTagString()).append(">\n");

        return sb.toString();
    }
}

   
