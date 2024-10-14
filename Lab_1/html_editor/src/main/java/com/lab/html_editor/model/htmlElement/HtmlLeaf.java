package com.lab.html_editor.model.htmlElement;

import java.util.List;
import java.util.ArrayList;

import com.lab.html_editor.factory.html_factory.BasicHtmlAttributeFactory;
import com.lab.html_editor.factory.html_factory.HtmlAttributeFactory;
import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;
import com.lab.html_editor.strategy.HtmlRepresentationStrategy;

import com.lab.html_editor.strategy.HtmlLeafIndentedRepresenationStrategy;

public abstract class HtmlLeaf extends TreeLeaf implements HtmlElement{

    private HtmlTagName tagName;
    private List<HtmlAttribute> attributes=new ArrayList<>();;
    private HtmlRepresentationStrategy representationStrategy=new HtmlLeafIndentedRepresenationStrategy();;
    private HtmlAttributeFactory attributeFactory=new BasicHtmlAttributeFactory();
    
    private String text;

    public HtmlLeaf(String id,HtmlTagName tagName){
        super(id);
        this.setId(id);
        this.tagName=tagName;
        this.text="";
    }

    public HtmlLeaf(String id,HtmlTagName tagName,String textContent){
        super(id);
        this.setId(id);
        this.tagName=tagName;
        this.text=textContent;
    }

    @Override
    public HtmlTagName getTagName(){
        return this.tagName;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text=text;
    }

    @Override
    public void setId(String id) throws IllegalArgumentException{
        if(id==null){
            throw new IllegalArgumentException("Invalid Id");
        }
        this.id=id;
        HtmlAttribute idAttribute=getAttribute("id");
        if(idAttribute!=null){
            removeAttribute("id");
        }
        addAttribute("id", id);
    }

    @Override
    public String getId(){
        return this.id;
    }

        // 获取所有属性
    @Override
    public List<HtmlAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributeFactory(HtmlAttributeFactory factory){
        this.attributeFactory=factory;
    }


    // 设置某个属性
    @Override
    public void addAttribute(String name,String value,String... properties) {
        HtmlAttribute attribute=this.attributeFactory.createAttribute(name, value, properties);
        this.attributes.add(attribute);
    }

    // 获取某个属性
    @Override
    public HtmlAttribute getAttribute(String name) {
        for(var attr:this.attributes){
            if(attr.getName().equals(name)){
                return attr;
            }
        }
        return null;
    }

    // 移除某个属性
    @Override
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

    @Override
    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy){
        this.representationStrategy=strategy;
    }

    @Override
    public HtmlRepresentationStrategy getRepresentationStrategy(){
        return this.representationStrategy;
    }

    @Override
    public String toStringRepresentation(int indentLevel){
        return this.representationStrategy.toStringRepresentation(this, indentLevel);
    }



}
