package com.lab.html_editor.utils.decorator;

import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.service.spellcheck.SpellCheckError;

public class HtmlSpellCheckDecorator extends HtmlElementDecorator{
    private List<SpellCheckError> spellCheckErrors=new ArrayList<>();

    public HtmlSpellCheckDecorator(HtmlElement element){
        super(element,DecoratorType.HTML_SPELLCHECK_DECORATOR);
    }

    public List<SpellCheckError> getSpellCheckErrors(){
        return this.spellCheckErrors;
    }

    public boolean hasSpellCheckErrors(){
        if(spellCheckErrors.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public void setSpellCheckErrors(List<SpellCheckError> errors){
        this.spellCheckErrors=errors;
    }
}
