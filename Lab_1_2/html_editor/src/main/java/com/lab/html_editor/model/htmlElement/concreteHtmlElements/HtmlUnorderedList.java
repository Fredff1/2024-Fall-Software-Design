package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;


public class HtmlUnorderedList extends HtmlComposite{
    public HtmlUnorderedList(String id){
        super(id,"ul");
    }

    public HtmlUnorderedList(String id,HtmlText text){
        super(id,"ul",text);
    }

    @Override
    public boolean addChild(TreeNode node,int index) throws IllegalArgumentException{
        if(!(node instanceof HtmlListItem)){
            throw new IllegalArgumentException("Invalid child for list node");
        }
        try{
            children.add(index, (HtmlElement)node);
            node.setFather(this);
            return true;
        }catch(Exception e){
            System.out.println("Failed to add a child node for Node "+this.getId()+" because of exception "+e.getMessage());
            return false;
        }
    }
}
