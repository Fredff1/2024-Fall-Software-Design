package com.lab.html_editor.utils.strategy;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.adapter.IndentHtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.IndentHtmlLeafAdapter;
import com.lab.html_editor.utils.adapter.provider.Adapter;
import com.lab.html_editor.utils.adapter.provider.IndentCompositeProvider;
import com.lab.html_editor.utils.adapter.provider.IndentLeafProvider;
import com.lab.html_editor.utils.adapter.provider.TreeCompositeProvider;
import com.lab.html_editor.utils.factory.adapter_Factory.IndentAdapterFactory;


public abstract class IndentedRepresentation implements RepresentationStrategy{
    protected boolean isFixedInstance(HtmlElement element){
        if(element instanceof HtmlFixedElement){
            return true;
        }
        return false;
    }

    protected String toStringCompositeHelper(Adapter element,int indentLevel,int original_indentLevel){
        
        IndentCompositeProvider adapter=(IndentCompositeProvider)element;
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(indentLevel);  // 缩进处理

        // 开始标签
        if(indent.isEmpty()==false){
            sb.append("\n").append(indent);
        }
        
        sb.append(adapter.getPrefix());
        


       var children=adapter.getChildren();
       

        // 递归生成子节点的 HTML 表示
        
        int childrenSize=children.size();
        //boolean has_change_line=false;
        if (childrenSize>0) {
            for ( int i=0;i<childrenSize;i++) {
                TreeNode child=children.get(i);
                var childAdapter=IndentAdapterFactory.createAdapter(child);
                if(childAdapter instanceof IndentLeafProvider){
                    var leafAdapter=(IndentLeafProvider)childAdapter;
                    if(leafAdapter.isTextNode()&&leafAdapter.getText().trim().isEmpty()){
                        continue;
                    }
                }
                if(child instanceof TreeLeaf ){
                    sb.append(toStringRepresentationLeaf(childAdapter, original_indentLevel+indentLevel));
                    sb.append(indent);
                }else if (child instanceof TreeComposite) {
                    sb.append(toStringCompositeHelper(childAdapter, indentLevel+original_indentLevel, original_indentLevel));
                }
                if(i==childrenSize-1&&adapter.getSuffix().isEmpty()==false){
                    sb.append("\n").append(indent);  // 缩进结束标签与开始标签对齐
                }
            }
        }
        
        
        // 结束标签

        sb.append(adapter.getSuffix());

        return sb.toString();
    }
    
    
    protected String toString(Adapter element,int indentLevel){
        String str="";
        if(element instanceof IndentCompositeProvider){
            str=toStringCompositeHelper(element, 0, indentLevel);
        }else if(element instanceof IndentLeafProvider){
            str=toStringRepresentationLeaf(element, indentLevel);
        }

        return str;
    }

    protected String toStringRepresentationLeaf(Adapter element,int indentLevel){
        
        
        // 缩进处理
        IndentLeafProvider adapter=(IndentLeafProvider)element;
        String indent = " ".repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(indent);
        if(adapter.isTextNode()){
            var text=adapter.getText();
        if(text!=null){
            sb.append(text);
        }
        }else{
            sb.append(adapter.getPrefix());
            // 添加叶节点的文本内容（如果有）
            var text=adapter.getText();
            if(text!=null){
                sb.append(text);
            }

            // 结束标签
            sb.append(adapter.getSuffix());
            }
        // 开始标签
        

        return sb.toString();
    }
}
