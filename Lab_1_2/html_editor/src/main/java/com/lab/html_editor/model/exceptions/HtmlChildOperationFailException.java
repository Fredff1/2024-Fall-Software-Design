package com.lab.html_editor.model.exceptions;

import com.lab.html_editor.model.htmlElement.HtmlElement;

public class HtmlChildOperationFailException extends RuntimeException{
    private HtmlElement child;
    public HtmlChildOperationFailException(String message,HtmlElement child){
        super(message);
        this.child=child;
    }

    public HtmlElement getChild(){
        return this.child;
    }

}
