package com.lab.html_editor.utils.decorator;

import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.service.spellcheck.SpellCheckError;

public class HtmlElementDecorator {
    protected HtmlElement element;
    protected DecoratorType type;

    public HtmlElementDecorator(HtmlElement element,DecoratorType type){
        this.element=element;
        this.type=type;
    }

    public DecoratorType getType(){
        return type;
    }

    

    
}
