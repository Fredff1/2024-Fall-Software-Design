package com.lab.html_editor.utils.adapter;

import com.lab.html_editor.model.TreeComposite;
import com.lab.html_editor.model.TreeNode;
import com.lab.html_editor.model.htmlElement.HtmlElement;

public abstract class HtmlElementAdapter extends TreeNodeAdapter{
    protected boolean showId=true;

    public HtmlElementAdapter(HtmlElement element){
        super(element);
    }

    public HtmlElementAdapter(HtmlElement element,boolean showId){
        super(element);
        this.showId=showId;
    }

    public boolean isShowId(){
        return this.showId;
    }

    

    

    

    
}
