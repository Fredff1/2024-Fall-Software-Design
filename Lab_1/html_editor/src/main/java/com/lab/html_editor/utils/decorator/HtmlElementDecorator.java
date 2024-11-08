package com.lab.html_editor.utils.decorator;

import java.util.ArrayList;
import java.util.List;

import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.service.spellcheck.SpellCheckError;

public class HtmlElementDecorator {
    private HtmlElement element;
    private List<SpellCheckError> spellCheckErrors=new ArrayList<>();

    public HtmlElementDecorator(HtmlElement element){
        this.element=element;
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
