package com.lab.html_editor.model.htmlElement.concreteHtmlElements;


import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

/**
 * 文本节点与其父节点绑定，没有单独的id和tag，如果存在的话，永远为父节点的第一个子元素
 */
public class HtmlText extends HtmlLeaf{


    public HtmlText(String textContent) {
        super(null,null,textContent);  // 文本节点没有标签名
    }
    
    @Override
    public HtmlTagName getTagName(){
        return null;
    }

    @Override
    public void setId(String id){
        return;
    }

    @Override
    public String getId(){
        return null;
    }

    @Override
    public String toStringRepresentation(int indentLevel) {
        if(getText().length()<1){
            return getText();
        }
        String indent=" ".repeat(indentLevel);
        return "\n"+indent+getText();  // 文本节点直接返回内容
    }

    

}
