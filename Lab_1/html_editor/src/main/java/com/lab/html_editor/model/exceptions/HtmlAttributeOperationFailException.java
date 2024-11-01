package com.lab.html_editor.model.exceptions;

import com.lab.html_editor.model.htmlElement.HtmlAttributes.HtmlAttribute;

public class HtmlAttributeOperationFailException extends RuntimeException{
    private HtmlAttribute attribute;

    public HtmlAttributeOperationFailException(String message,HtmlAttribute attribute){
        super(message);
        this.attribute=attribute;
    }

    public HtmlAttribute getFailedAttribute(){
        return attribute;
    }

}
