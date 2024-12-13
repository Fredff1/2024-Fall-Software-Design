package com.lab.html_editor.utils.decorator;

import com.lab.html_editor.model.htmlElement.HtmlElement;

public class HtmlShowIdDecorator extends HtmlElementDecorator{
    private boolean showId;

    public HtmlShowIdDecorator(HtmlElement element,boolean flag){
        super(element, DecoratorType.HTML_SHOWID_DECORATOR);
        showId=flag;
    }

    public boolean isShowId(){
        return showId;
    }

    public void setShowId(boolean flag){
        showId=flag;
    }
}
