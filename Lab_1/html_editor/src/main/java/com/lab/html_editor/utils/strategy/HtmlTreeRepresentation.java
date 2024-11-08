package com.lab.html_editor.utils.strategy;

import com.lab.html_editor.model.htmlElement.*;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.utils.adapter.HtmlCompositeAdapter;
import com.lab.html_editor.utils.adapter.HtmlElementAdapter;
import com.lab.html_editor.utils.adapter.HtmlLeafAdapter;
import com.lab.html_editor.utils.adapter.TreeNodeAdapter;

import java.util.*;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeLeaf;
import com.lab.html_editor.model.TreeNode;

/**
 * 树状打印策略
 */
public class HtmlTreeRepresentation extends TreeRepresentation implements HtmlRepresentationStrategy {

    private boolean showId=true;

    public HtmlTreeRepresentation(){

    }

    public HtmlTreeRepresentation(boolean showId){
        this.showId=showId;
    }

    public void setIdPrensent(boolean flag){
        this.showId=flag;
    }

   @Override
    public String toStringRepresentation(HtmlElement element,int indent){
        StringBuilder sb = new StringBuilder();
        HtmlElementAdapter adapter=null;
        if(element instanceof HtmlComposite){
            adapter=new HtmlCompositeAdapter((HtmlComposite)element,showId);
        }else if(element instanceof HtmlLeaf){
            adapter=new HtmlLeafAdapter((HtmlLeaf)element,showId);
        }
        buildRepresentation(adapter, sb, new ArrayList<>(), true);
        return sb.toString();
    }

    

}
