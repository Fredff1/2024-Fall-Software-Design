package com.lab.html_editor.utils.factory.html_factory;



import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.HtmlTagNameEnum;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.*;

import com.lab.html_editor.utils.factory.TreeNodeFactory;

/**
 * 创建Html元素的工厂
 */
public class BasicHtmlFactory implements TreeNodeFactory{

    public BasicHtmlFactory(){

    }
    /**
     * 
     * @param args[0] id
     * @param args[1] tagName
     * @param args[2] text
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public TreeNode createComponent(String... args) throws IllegalArgumentException{
        if(args.length<2){
            throw new RuntimeException("id and tagname cannot be empty");
        }
        String id=args[0];
        String tagName=args[1];
        String text=null;
        if(args.length>=3){
            text=args[2];
        }
        
        if(id.equals("")){
            throw new RuntimeException("Id cannot be empty");
        }

        HtmlTagNameEnum htmlTagNameEnum=HtmlTagNameEnum.fromString(tagName);

        switch(htmlTagNameEnum){
            case DIV:
            return createDiv(id,text);
            case ANCHOR:
            return createAnchor(id,text);
            case PARAGRAPH:
            return createParagraph(id,text);
            case SPAN:
            return createSpan(id,text);
            case TEXT:
            return createText(text);
            case BODY:
            return createBody();
            case HEAD:
            return createHead();
            case TITLE:
            return createTitle(text);
            case HTMLTOP:
            return createHtmlTop();
            case H1,H2,H3,H4,H5,H6:
            return createSubtitle(id, tagName,text);
            case UNORDEREDLIST:
            return createUnorderedList(id, text);
            case LISTITEM:
            return createListItem(id, text);
            case CUSTOMCOMPOSITE:
            return createCustomComposite(id,tagName,text);
            case CUSTOMLEAF:
            return null;
        }
        return null;
    }

    public TreeNode createCustomComposite(String id,String tagNameString,String text){

        
        
        HtmlCustomComposite new_element=null;
        if(text!=null){
            new_element=new HtmlCustomComposite(id, tagNameString,createText(text));
        }else{
            new_element=new HtmlCustomComposite(id, tagNameString);
        }

        return new_element;

        
    }

    public HtmlListItem createListItem(String id,String text){

       
        HtmlListItem new_element=null;
        if(text!=null){
            new_element=new HtmlListItem(id,text);
        }else{
            new_element=new HtmlListItem(id);
        }
        return new_element;

    }

    public HtmlUnorderedList createUnorderedList(String id,String text){

        
        HtmlUnorderedList new_element=null;
        if(text!=null){
            new_element=new HtmlUnorderedList(id,createText(text));
        }else{
            new_element=new HtmlUnorderedList(id);
        }

        return new_element;
    }

    public HtmlSubtitle createSubtitle(String id,String tagName,String text){

        HtmlSubtitle new_element=null;
        if(text!=null){
            new_element=new HtmlSubtitle(id, tagName,text);
        }else{
            new_element=new HtmlSubtitle(id, tagName);
        }

        return new_element;
    }

    
    public HtmlAnchor createAnchor(String id,String text){

        
        HtmlAnchor new_element=null;
        if(text!=null){
            new_element=new HtmlAnchor(id,createText(text));
        }else{
            new_element=new HtmlAnchor(id);
        }

        return new_element;
    }

    
    public HtmlDiv createDiv(String id,String text){

        
        HtmlDiv new_element=null;
        if(text!=null){
            new_element=new HtmlDiv(id,createText(text));
        }else{
            new_element=new HtmlDiv(id);
        }

        return new_element;
    }

    
    public HtmlParagraph createParagraph(String id,String text){

       
        HtmlParagraph new_element=null;
        if(text!=null){
            new_element=new HtmlParagraph(id,text);
        }else{
            new_element=new HtmlParagraph(id);
        }

        return new_element;
    }

   

    
    public HtmlSpan createSpan(String id,String text){
        
        HtmlSpan new_element= null;
        if(text!=null){
            new_element=new HtmlSpan(id,createText(text));
        }else{
            new_element=new HtmlSpan(id);
            return new_element;
        }
        return new_element;
    }

    
    public HtmlText createText(String text) throws IllegalArgumentException{
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

    
    public HtmlTitle createTitle(String text){
        
        if(text==null){
            throw new IllegalArgumentException("Empty title is not permitted");
        }
        var new_element=new HtmlTitle(text);
        return new_element;
    }
    
}