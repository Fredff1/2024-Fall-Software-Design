package com.lab.html_editor.factory.html_factory;


import java.util.HashMap;

import java.util.Map;

import com.lab.html_editor.factory.TreeNodeFactory;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagName;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.*;
import com.lab.html_editor.service.IDManager;


public class BasicHtmlFactory implements TreeNodeFactory{


    


    public BasicHtmlFactory(){

    }

   /**
     * 检查id是否已经被创建过
     * 如果已存在，返回false
     * 如果不存在，返回true
     * @param id 目标的id
     * @return boolean
     */
    

   


   

    private String ifTextExsist(String... args){
        if(args.length>0){
            return args[0];
        }
        return null;
    }

    
    

    

    public void editElementText(HtmlElement element,String new_text){
        if(new_text==null){
            new_text="";
        }

        if(element instanceof HtmlComposite){
            ((HtmlComposite)element).setTextElement(createText(new_text));
        }else if(element instanceof HtmlLeaf){
            ((HtmlLeaf)element).setText(new_text);
        }else{
            throw new IllegalArgumentException("Invalid html element to eidt text");
        }
    }


    @Override
    public TreeNode createComponent(String id,HtmlTagName tagName,String... args) throws IllegalArgumentException{
        switch(tagName){
            case DIV:
            return createDiv(id,args);
            case ANCHOR:
            return createAnchor(id,args);
            case PARAGRAPH:
            return createParagraph(id,args);
            case SPAN:
            return createSpan(id,args);
            case TEXT:
            return createText(args);
            case BODY:
            return createBody();
            case HEAD:
            return createHead();
            case TITLE:
            return createTitle(args);
            case HTMLTOP:
            return createHtmlTop();
            case H1,H2,H3,H4,H5,H6:
            return createSubtitle(id, tagName);
            case UNORDEREDLIST:
            return createUnorderedList(id, args);
            case LISTITEM:
            return createListItem(id, args);
            case CUSTOMCOMPOSITE:
            return createCustomComposite(id,args);
            case CUSTOMLEAF:
            return null;
        }
        return null;
    }

    public TreeNode createCustomComposite(String id,String... args){

        var tagNameString=ifTextExsist(args);
        String text=null;
        if(args.length>1){
            text=args[1];
        }
        HtmlCustomComposite new_element=null;
        if(text!=null){
            new_element=new HtmlCustomComposite(id, tagNameString,createText(text));
        }else{
            new_element=new HtmlCustomComposite(id, tagNameString);
        }

        return new_element;

        
    }

    public HtmlListItem createListItem(String id,String... args){

        var text=ifTextExsist(args);
        HtmlListItem new_element=null;
        if(text!=null){
            new_element=new HtmlListItem(id,text);
        }else{
            new_element=new HtmlListItem(id);
        }
        return new_element;

    }

    public HtmlUnorderedList createUnorderedList(String id,String... args){

        var text=ifTextExsist(args);
        HtmlUnorderedList new_element=null;
        if(text!=null){
            new_element=new HtmlUnorderedList(id,createText(text));
        }else{
            new_element=new HtmlUnorderedList(id);
        }

        return new_element;
    }

    public HtmlSubtitle createSubtitle(String id,HtmlTagName tagName,String... args){

        HtmlSubtitle new_element=null;
        var text=ifTextExsist(args);
        if(text!=null){
            new_element=new HtmlSubtitle(id, tagName,text);
        }else{
            new_element=new HtmlSubtitle(id, tagName);
        }

        return new_element;
    }

    
    public HtmlAnchor createAnchor(String id,String... args){

        var text=ifTextExsist(args);
        HtmlAnchor new_element=null;
        if(text!=null){
            new_element=new HtmlAnchor(id,createText(text));
        }else{
            new_element=new HtmlAnchor(id);
        }

        return new_element;
    }

    
    public HtmlDiv createDiv(String id,String... args){

        var text=ifTextExsist(args);
        HtmlDiv new_element=null;
        if(text!=null){
            new_element=new HtmlDiv(id,createText(text));
        }else{
            new_element=new HtmlDiv(id);
        }

        return new_element;
    }

    
    public HtmlParagraph createParagraph(String id,String... args){

        var text=ifTextExsist(args);
        HtmlParagraph new_element=null;
        if(text!=null){
            new_element=new HtmlParagraph(id,text);
        }else{
            new_element=new HtmlParagraph(id);
        }

        return new_element;
    }

   

    
    public HtmlSpan createSpan(String id,String... args){
        var text=ifTextExsist(args);
        HtmlSpan new_element= null;
        if(text!=null){
            new_element=new HtmlSpan(id,createText(text));
        }else{
            new_element=new HtmlSpan(id);
            return new_element;
        }
        return new_element;
    }

    
    public HtmlText createText(String... args) throws IllegalArgumentException{
        if(ifTextExsist(args)==null){
            throw new IllegalArgumentException("text node must have text content");
        }
        var new_element=new HtmlText(args[0]);
        return new_element;
    }

    public HtmlText createText(String text){
        if(text==null){
            throw new IllegalArgumentException("text node must have text content");
        }
        var new_element=new HtmlText(text);
        return new_element;
    }

    
    public HtmlTop createHtmlTop(){
        var new_element=new HtmlTop();
        return new_element;
    }

    
    public HtmlBody createBody(){
        var new_element=new HtmlBody();
        return new_element;

    }

    
    public HtmlHead createHead(){
        var new_element=new HtmlHead();
        return new_element;
    }

    
    public HtmlTitle createTitle(String... args){
        var titleText=ifTextExsist(args);
        if(titleText==null){
            throw new IllegalArgumentException("Empty title is not permitted");
        }
        var new_element=new HtmlTitle(titleText);
        return new_element;
    }
    
}