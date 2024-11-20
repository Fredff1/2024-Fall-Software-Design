package com.lab.html_editor.utils.strategy;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlFixedElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlIdAttribute;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.factory.adapter_Factory.IndentAdapterFactory;



/**
 * 缩进打印策略
 */
public class HtmlIndentedRepresentation extends IndentedRepresentation implements HtmlRepresentationStrategy{
    

    protected boolean isFixedInstance(HtmlElement element){
        if(element instanceof HtmlFixedElement){
            return true;
        }
        return false;
    }

    private String toStringCompositeHelpear(HtmlElement element,int indentLevel,int original_indentLevel){
        if(!(element instanceof HtmlComposite)){
            throw new IllegalArgumentException("Unsupported composite strategy");
        }
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(indentLevel);  // 缩进处理

        // 开始标签
        sb.append("\n").append(indent);
        sb.append("<").append(element.getTagName().getTagString());

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
            int childrenSize=((HtmlComposite)element).getChildrenSize();
            //boolean has_change_line=false;
            if (childrenSize>0) {
                for ( int i=0;i<childrenSize;i++) {
                    TreeNode child=((HtmlComposite)element).getChild(i);
                    if(child instanceof HtmlLeaf ){
                        
                        sb.append(((HtmlLeaf)child).toStringRepresentation(original_indentLevel+indentLevel));
                        sb.append(indent);
                    }else if (child instanceof HtmlComposite) {

                        //sb.append(toStringCompositeHelper((HtmlComposite)child, indentLevel+original_indentLevel, original_indentLevel));
                    }
                    if(i==childrenSize-1){
                        sb.append("\n").append(indent);  // 缩进结束标签与开始标签对齐
                    }
                }
                if(childrenSize>1|| !(((HtmlComposite)element).getChild(0) instanceof HtmlText)){
                    //sb.append("\n").append(indent);
                }
                
            }
        }
        
        // 结束标签

        sb.append("</").append(element.getTagName().getTagString()).append(">");

        return sb.toString();
    }
    
    @Override
    public String toStringRepresentation(HtmlElement element,int indentLevel){
        String str="";
        str=toString(IndentAdapterFactory.createAdapter(element), indentLevel);

        return str;
    }

    public String toStringRepresentationLeafa(HtmlElement element,int indentLevel){
        if (!(element instanceof HtmlLeaf)) {
            throw new IllegalArgumentException("Unsupported Leaf strategy");
        }
        
        // 缩进处理
        String indent = " ".repeat(indentLevel);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        // 开始标签
        sb.append(indent).append("<").append(element.getTagName().getTagString());

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
        sb.append("</").append(element.getTagName().getTagString()).append(">");

        return sb.toString();
    }
}

   
