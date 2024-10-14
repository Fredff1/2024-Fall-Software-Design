package com.lab.html_editor.model.htmlElement;


import java.util.ArrayList;

import java.util.List;

import com.lab.html_editor.factory.html_factory.BasicHtmlAttributeFactory;
import com.lab.html_editor.factory.html_factory.HtmlAttributeFactory;
import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;

import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.strategy.HtmlRepresentationStrategy;
import com.lab.html_editor.strategy.HtmlCompositeIndentedRepresentation;
import com.lab.html_editor.visitor.html_visitor.HtmlVisitable;
import com.lab.html_editor.visitor.html_visitor.HtmlVisitor;

//import java.util.List;

public abstract class HtmlComposite extends TreeComposite implements HtmlVisitable,HtmlElement{
    private HtmlTagName tagName;
    private List<HtmlAttribute> attributes=new ArrayList<>();;
    private HtmlRepresentationStrategy representationStrategy=new HtmlCompositeIndentedRepresentation();;
    private HtmlAttributeFactory attributeFactory=new BasicHtmlAttributeFactory();

    

    public HtmlComposite(String id,HtmlTagName tagName){
        super(id);
        this.setId(id);
        this.tagName=tagName;
    }

    public HtmlComposite(String id,HtmlTagName tagName,HtmlText textElement){
        super(id);
        this.setId(id);
        this.tagName=tagName;
        this.getChildren().add(0, textElement);
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

    public HtmlText getTextElement(){
        if(getChildren().isEmpty()){
            return null;
        }

        TreeNode first_child=getChild(0);
        if(first_child instanceof HtmlText){
            return ((HtmlText)first_child);
        }
        return null;
    }

    public void setTextElement(HtmlText textElement){
        if(getChildren().isEmpty()==false&&getChild(0 ) instanceof HtmlText){
            this.getChildren().set(0, textElement);
        }else{
            this.getChildren().add(0,textElement);
        }
    }

 
     // 获取所有属性
     @Override
     public List<HtmlAttribute> getAttributes() {
         return attributes;
     }

    // 设置某个属性
    @Override
    public void addAttribute(String name,String value,String... properties) {
        HtmlAttribute attribute=this.attributeFactory.createAttribute(name, value, properties);
        this.attributes.add(attribute);
    }

    @Override
    public void setAttributeFactory(HtmlAttributeFactory factory){
        this.attributeFactory=factory;
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

    /**
     * 得到对应子对象在列表中的index方便insert操作，若不是子对象，返回-1
     * @param target
     * @return 子对象的索引 不存在则为-1
     */
    public int getChildIndex(TreeNode target){
        for(int i=0;i<getChildren().size();i++){
            var child=getChild(i);
            if (child==target){
                return i;
            }
        }
        return -1;
    }

    public TreeNode findChild(String id) {
        // 遍历子元素
        for (var child : getChildren()) {
            // 跳过文本节点
            if (child instanceof HtmlText) {
                continue;
            }
            var target_id = child.getId();
            boolean is_id_same = target_id.equals(id);
            if (child instanceof HtmlComposite) {
                if (is_id_same) {
                    return (HtmlComposite)child; 
                }
                HtmlComposite compositeChild = (HtmlComposite) child;
                TreeNode found = compositeChild.findChild(id); 
                if (found instanceof HtmlComposite || found instanceof HtmlLeaf) {
                    return found; 
                }
            }else if (child instanceof HtmlLeaf) {
                if (is_id_same) {
                    return (HtmlLeaf)child; 
                }
            }
        }
        return null;
    }

    
    @Override
    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy){
        this.representationStrategy=strategy;
        for(var child:this.getChildren()){
            
            ((HtmlElement)child).setRepresentationStrategy(strategy);
        }

    }

    @Override
    public HtmlRepresentationStrategy getRepresentationStrategy(){
        return this.representationStrategy;
    }

    @Override
    public HtmlTagName getTagName(){
        return this.tagName;
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
