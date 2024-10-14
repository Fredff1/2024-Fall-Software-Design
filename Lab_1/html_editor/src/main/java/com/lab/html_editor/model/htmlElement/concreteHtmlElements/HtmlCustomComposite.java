package com.lab.html_editor.model.htmlElement.concreteHtmlElements;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlTagName;

public class HtmlCustomComposite extends HtmlComposite{
    public HtmlCustomComposite(String id,String customTagName){
        super(id, HtmlTagName.CUSTOMCOMPOSITE);
        getTagName().setCustomTagString(customTagName);
    }

    public HtmlCustomComposite(String id,String customTagName,HtmlText text){
        super(id, HtmlTagName.CUSTOMCOMPOSITE,text);
        getTagName().setCustomTagString(customTagName);
    }


}
