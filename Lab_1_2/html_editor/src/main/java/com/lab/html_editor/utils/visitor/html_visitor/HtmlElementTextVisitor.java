package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;

import java.util.*;

/**
 * 得到文本内容的访问者
 */
public class HtmlElementTextVisitor implements HtmlVisitor{
    private final List<NodeTextInfo> textContents=new ArrayList<>();

    @Override
    public void visit(HtmlComposite composite){
        
       String text=composite.getText();
       String id=composite.getId();
       textContents.add(new NodeTextInfo(id, text));
       for(int i=0;i<composite.getChildrenSize();i++){
          var child=(HtmlElement)composite.getChild(i);
          child.accept(this);
       }
    }

    @Override
    public void visit(HtmlLeaf leaf){
        String text=leaf.getText();
        String id=leaf.getId();
        if(id==null){
            var father=leaf.getFather();
            if(father!=null){
                id=((HtmlElement)father).getId()+" Text";
            }
        }
        if(text!=null&&id!=null){
            textContents.add(new NodeTextInfo(id, text));
        }
        
    }

    public int getTextSize(){
        return textContents.size();
    }

    public String getIdOfText(int index){
        if(index>=textContents.size()||index<0){
            throw new IllegalArgumentException("Invalid index");
        }
        return textContents.get(index).getId();
    }

    public String getText(int index){
        if(index>=textContents.size()||index<0){
            throw new IllegalArgumentException("Invalid index");
        }
        return textContents.get(index).getText();
    }
}

class NodeTextInfo {
    private String id;
    private String text;

    public NodeTextInfo(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() { return id; }
    public String getText() { return text; }
}
