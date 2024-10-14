package com.lab.html_editor.factory.html_factory;


import java.util.HashMap;

import java.util.Map;

import com.lab.html_editor.factory.TreeNodeFactory;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlTagName;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.*;


public class BasicHtmlFactory implements TreeNodeFactory{

    private Map<String, HtmlElement> elementMap;



    public BasicHtmlFactory(){
        this.elementMap=new HashMap<>();
    }

   /**
     * 检查id是否已经被创建过
     * 如果已存在，返回false
     * 如果不存在，返回true
     * @param id 目标的id
     * @return boolean
     */
    

    private boolean validateId(String id,String exceptionMsg) {
        if(exceptionMsg==null){
            exceptionMsg="Cannot create element. ID '" + id + "' is invalid or already exists.";
        }
        var flag=!elementMap.containsKey(id); 
        if(!flag){
            throw new IllegalArgumentException(exceptionMsg);
        } 
        return flag; // 使用Map检查ID是否唯一
    }


   

    private String ifTextExsist(String... args){
        if(args.length>0){
            return args[0];
        }
        return null;
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
        validateId(id,null);
        var tagNameString=ifTextExsist(args);
        String text=null;
        if(args.length>1){
            text=args[1];
        }
        TreeNode new_element=null;
        if(text!=null){
            new_element=new HtmlCustomComposite(id, tagNameString,createText(text));
        }else{
            new_element=new HtmlCustomComposite(id, tagNameString);
        }
        if(new_element instanceof HtmlElement){
            elementMap.put(id, (HtmlElement)new_element);
        }
        return new_element;
    }

    public HtmlListItem createListItem(String id,String... args){
        validateId(id,null);
        var text=ifTextExsist(args);
        HtmlListItem new_element=null;
        if(text!=null){
            new_element=new HtmlListItem(id,text);
        }else{
            new_element=new HtmlListItem(id);
        }
        elementMap.put(id, new_element);
        return new_element;
    }

    public HtmlUnorderedList createUnorderedList(String id,String... args){
        validateId(id,null);
        var text=ifTextExsist(args);
        HtmlUnorderedList new_element=null;
        if(text!=null){
            new_element=new HtmlUnorderedList(id,createText(text));
        }else{
            new_element=new HtmlUnorderedList(id);
        }
        elementMap.put(id, new_element);
        return new_element;
    }

    public HtmlSubtitle createSubtitle(String id,HtmlTagName tagName,String... args){
        validateId(id,null);
        HtmlSubtitle new_element=null;
        var text=ifTextExsist(args);
        if(text!=null){
            new_element=new HtmlSubtitle(id, tagName,text);
        }else{
            new_element=new HtmlSubtitle(id, tagName);
        }
        elementMap.put(id, new_element);
        return new_element;
    }

    
    public HtmlAnchor createAnchor(String id,String... args){
        validateId(id,null);
        var text=ifTextExsist(args);
        HtmlAnchor new_element=null;
        if(text!=null){
            new_element=new HtmlAnchor(id,createText(text));
        }else{
            new_element=new HtmlAnchor(id);
        }
        elementMap.put(id, new_element);
        return new_element;
    }

    
    public HtmlDiv createDiv(String id,String... args){
        validateId(id,null);
        var text=ifTextExsist(args);
        HtmlDiv new_element=null;
        if(text!=null){
            new_element=new HtmlDiv(id,createText(text));
        }else{
            new_element=new HtmlDiv(id);
        }
        elementMap.put(id, new_element);
        return new_element;
    }

    
    public HtmlParagraph createParagraph(String id,String... args){
        validateId(id,null);
        var text=ifTextExsist(args);
        HtmlParagraph new_element=null;
        if(text!=null){
            new_element=new HtmlParagraph(id,text);
        }else{
            new_element=new HtmlParagraph(id);
        }
        elementMap.put(id, new_element);
        return new_element;
    }

   

    
    public HtmlSpan createSpan(String id,String... args){
        validateId(id,null);
        var text=ifTextExsist(args);
        HtmlSpan new_element= null;
        if(text!=null){
            new_element=new HtmlSpan(id,createText(text));
        }else{
            new_element=new HtmlSpan(id);
            return new_element;
        }
        elementMap.put(id, new_element);
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
        elementMap.put("html", new_element);
        return new_element;
    }

    
    public HtmlBody createBody(){
        var new_element=new HtmlBody();
        elementMap.put("default", new_element);
        return new_element;
    }

    
    public HtmlHead createHead(){
        var new_element=new HtmlHead();
        elementMap.put("head", new_element);
        return new_element;
    }

    
    public HtmlTitle createTitle(String... args){
        var titleText=ifTextExsist(args);
        if(titleText==null){
            throw new IllegalArgumentException("Empty title is not permitted");
        }
        var new_element=new HtmlTitle(titleText);
        elementMap.put("title", new_element);
        return new_element;
    }
    
}