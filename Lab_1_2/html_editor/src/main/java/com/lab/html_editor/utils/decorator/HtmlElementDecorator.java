package com.lab.html_editor.utils.decorator;

import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.service.spellcheck.SpellCheckError;

public class HtmlElementDecorator extends Decorator{
    protected HtmlElement element;
    

    public HtmlElementDecorator(HtmlElement element,DecoratorType type){
        super(type);
        this.element=element;

    }

    

    

    
}
