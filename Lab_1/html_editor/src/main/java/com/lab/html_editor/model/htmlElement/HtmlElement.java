package com.lab.html_editor.model.htmlElement;

import java.util.ArrayList;

import java.util.List;


import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.strategy.HtmlRepresentationStrategy;
import com.lab.html_editor.strategy.IndentedHtmlrepresentation;
import com.lab.html_editor.visitor.html_visitor.HtmlVisitable;
import com.lab.html_editor.visitor.html_visitor.HtmlVisitor;

//import java.util.List;

public abstract class HtmlElement extends TreeNode implements HtmlVisitable{
    private String tagName;
    private List<HtmlAttribute> attributes;
    private HtmlTextContent textContent;
    private HtmlRepresentationStrategy representationStrategy;


    

    public HtmlElement(String id,String tagName){
        super(id);
        this.tagName=tagName;
        this.attributes=new ArrayList<>();
        this.representationStrategy=new IndentedHtmlrepresentation();
        this.textContent=new HtmlTextContent("");
    }

    public HtmlElement(String id,String tagName,String textContent){
        super(id);
        this.tagName=tagName;
        this.attributes=new ArrayList<>();
        this.representationStrategy=new IndentedHtmlrepresentation();
        this.textContent=new HtmlTextContent(textContent);
    }

    // 获取所有属性
    public List<HtmlAttribute> getAttributes() {
        return attributes;
    }

    public HtmlTextContent getTextContent(){
        return this.textContent;
    }

    public void setTextContent(String text){
        this.textContent.setText(text);
    }


    // 设置某个属性
    public void addAttribute(HtmlAttribute attribute) {
        attributes.add(attribute);
        var attr_ite=this.attributes.iterator();
        while(attr_ite.hasNext()){
            var current_attribute=attr_ite.next();
            if(current_attribute.getName().equals(attribute.getName())){
                attr_ite.remove();
            }
        }
        this.attributes.add(attribute);
    }

    // 获取某个属性
    public HtmlAttribute getAttribute(String name) {
        for(var attr:this.attributes){
            if(attr.getName().equals(name)){
                return attr;
            }
        }
        return null;
    }

    // 移除某个属性
    public boolean removeAttribute(String name) {
        var attr_ite=this.attributes.iterator();
        while(attr_ite.hasNext()){
            var current_attribute=attr_ite.next();
            if(current_attribute.getName().equals(name)){
                attr_ite.remove();
                return true;
            }
        }
        return false;
    }

    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy){
        this.representationStrategy=strategy;
        for(var child:this.getChildren()){
            ((HtmlElement)child).setRepresentationStrategy(strategy);
        }

    }

    public HtmlRepresentationStrategy getRepresentationStrategy(){
        return this.representationStrategy;
    }

    public String getTagName(){
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

     /**
     * 生成元素的 HTML 字符串表示，递归子节点，使用策略模式切换不同的表示策略
     */
    

    @Override
    public String toStringRepresentation(int indentLevel) {
       return this.representationStrategy.toStringRepresentation(this, indentLevel);
    }

    @Override
    public void accept(HtmlVisitor visitor){
        visitor.visit(this);
    }


}
