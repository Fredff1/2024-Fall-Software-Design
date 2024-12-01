package com.lab.html_editor.model.htmlElement;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.exceptions.HtmlAttributeOperationFailException;
import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;
import com.lab.html_editor.service.spellcheck.SpellCheckError;
import com.lab.html_editor.utils.decorator.*;
import com.lab.html_editor.utils.factory.html_factory.BasicHtmlAttributeFactory;
import com.lab.html_editor.utils.factory.html_factory.HtmlAttributeFactory;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;

import com.lab.html_editor.utils.strategy.HtmlRepresentationStrategy;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitable;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlVisitor;

import java.util.*;

public abstract class HtmlElement implements HtmlVisitable,TreeNode,Decorative{
    private String id;
    private HtmlTagName tagName;
    
    private TreeComposite father;
    private HtmlRepresentationStrategy representationStrategy=new HtmlIndentedRepresentation();
    private final List<HtmlAttribute> attributes=new ArrayList<>();
    private final HtmlAttributeFactory attributeFactory=new BasicHtmlAttributeFactory();

    private final Map<DecoratorType,HtmlElementDecorator> decorators=new HashMap<>();
    

     public HtmlElement(String id,String tagName){
        this.setId(id);
        this.tagName=new HtmlTagName(tagName);

    }

   
   
    public HtmlTagName getTagName(){
        return this.tagName;
    }
    public List<HtmlAttribute> getAttributes(){
        return attributes;
    }

    public void addDecorator(Decorator decorator){
        decorators.put(decorator.getType(), (HtmlElementDecorator)decorator);
    }

    public void removeDEcorator(DecoratorType type){
        decorators.remove(type);
    }

    public HtmlElementDecorator getDecorator(DecoratorType type){
        return decorators.get(type);
    }

    /**
     * 设置某个属性
     * */
    public void addAttribute(String name,String value,String... properties)throws HtmlAttributeOperationFailException {
        HtmlAttribute prev=getAttribute(name);
        if(prev!=null){
            prev.setValue(value);
            throw new HtmlAttributeOperationFailException("The arrtibute of name "+name+" already exists,auto set the attribute to new value", prev);
        }
        HtmlAttribute attribute=this.attributeFactory.createAttribute(name, value, properties);
        this.attributes.add(attribute);
    }

    
    /**
     * 获取某个属性
     * */
    public HtmlAttribute getAttribute(String name){
        for(var attr:this.attributes){
            if(attr.getName().equals(name)){
                return attr;
            }
        }
        return null;
    }

    /**
     * 移除某个属性
     * */
    public boolean removeAttribute(String name) throws HtmlAttributeOperationFailException{
        var attr_ite=this.attributes.iterator();
        while(attr_ite.hasNext()){
            var current_attribute=attr_ite.next();
            if(current_attribute.getName().equals(name)){
                attr_ite.remove();
                return true;
            }
        }
        throw new HtmlAttributeOperationFailException("The attribute of name "+name+" does not exist", null);
    }

    

    
    /**
     * 设置id
     * @param id
     * @throws HtmlAttributeOperationFailException
     */
    public void setId(String id) throws HtmlAttributeOperationFailException{
        if(id==null){
            throw new HtmlAttributeOperationFailException("Invalid Id",null);
        }
        this.id=id;
        HtmlAttribute idAttribute=getAttribute("id");
        if(idAttribute!=null){
            removeAttribute("id");
        }
        addAttribute("id", id);
    }

    public TreeComposite getFather(){
        return (TreeComposite)this.father;
    }
    
    public void setFather(TreeComposite father){
        this.father=father;
    }

    

    public String getId(){
        return this.id;
    }

    

    public abstract void setText(String text);

    public abstract String getText();

    

    public HtmlRepresentationStrategy getRepresentationStrategy(){
        return this.representationStrategy;
    }

    /**
     * 设置表示方式
     * @param strategy
     */
    public void setRepresentationStrategy(HtmlRepresentationStrategy strategy){
        this.representationStrategy=strategy;
    }

    /**
     * 根据表示的strtegy得到对应的字符串
     * @param indentLevel
     * @return
     */
    public String toStringRepresentation(int indentLevel){
        return this.representationStrategy.toStringRepresentation(this, indentLevel);
    }

    

    

   
}





    

    



    
   


    

   

   

    

