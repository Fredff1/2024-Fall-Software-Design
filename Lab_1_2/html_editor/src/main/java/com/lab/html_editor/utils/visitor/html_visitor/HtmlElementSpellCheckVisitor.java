package com.lab.html_editor.utils.visitor.html_visitor;

import com.lab.html_editor.model.htmlElement.HtmlComposite;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.model.htmlElement.HtmlLeaf;
import com.lab.html_editor.model.htmlElement.concreteHtmlElements.HtmlText;
import com.lab.html_editor.service.spellcheck.SpellCheckError;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.decorator.DecoratorType;
import com.lab.html_editor.utils.decorator.HtmlSpellCheckDecorator;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

/**
 * 进行spell-check的访问者
 */
public class HtmlElementSpellCheckVisitor implements HtmlVisitor{

    private final SpellCheckService spellCheckService;
    private final List<SpellCheckErrorInfo> spellCheckErrorInfos;
    private boolean visitChild=true;
    
    public HtmlElementSpellCheckVisitor(SpellCheckService spellCheckService){
        this.spellCheckService=spellCheckService;
        this.spellCheckErrorInfos=new ArrayList<>();
    }

    public HtmlElementSpellCheckVisitor(SpellCheckService spellCheckService,boolean visitChild){
        this(spellCheckService);
        this.visitChild=visitChild;
    }
    
    
    @Override
    public void visit(HtmlComposite composite){
        String text=composite.getText();
        if(text!=null&&text!=""){
            List<SpellCheckError> errors=null;
            try{
                errors=spellCheckService.checkSpellingErrors(text);
            }catch(IOException e){
                return;
            }
            HtmlSpellCheckDecorator spellcheckDecorator=(HtmlSpellCheckDecorator)composite.getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
            spellcheckDecorator.setSpellCheckErrors(errors);
            if(errors.size()>0){
                spellCheckErrorInfos.add(new SpellCheckErrorInfo(composite.getId(), errors));
            }
        }
        if(visitChild){
            for(int i=0;i<composite.getChildrenSize();i++){
                var child=(HtmlElement)composite.getChild(i);
                child.accept(this);
            }
        }
    }

    @Override
    public void visit(HtmlLeaf leaf){
        if(leaf instanceof HtmlText){
            return;
        }

        String text=leaf.getText();
        if(text!=null){
            List<SpellCheckError> errors=null;
            try{
                errors=spellCheckService.checkSpellingErrors(text);
            }catch(IOException e){
                return;
            }
            HtmlSpellCheckDecorator spellcheckDecorator=(HtmlSpellCheckDecorator)leaf.getDecorator(DecoratorType.HTML_SPELLCHECK_DECORATOR);
            spellcheckDecorator.setSpellCheckErrors(errors);
            if(errors.size()>0){
                spellCheckErrorInfos.add(new SpellCheckErrorInfo(leaf.getId(), errors));
            }
            
        }
    }

    public int getErrorsSize(){
        return spellCheckErrorInfos.size();
    }

    public String getErrorId(int index){
        if(index>=spellCheckErrorInfos.size()||index<0){
            throw new IllegalArgumentException("Invalid index");
        }
        return spellCheckErrorInfos.get(index).getId();
    }

    public String getErrorContents(){
        StringBuilder strb=new StringBuilder();
        for(int i=0;i<spellCheckErrorInfos.size();i++){
            strb.append(spellCheckErrorInfos.get(i).getErrorTexts());
        }
        return strb.toString();
        
    }

    

    class SpellCheckErrorInfo{
        private String id;
        private List<SpellCheckError> errors;

        public SpellCheckErrorInfo(String id,List<SpellCheckError> errors){
            this.id=id;
            this.errors=errors;
        }

        public String getId(){
            return id;
        }

        public List<SpellCheckError> getErrors(){
            return errors;
        }

        public String getErrorTexts(){
            StringBuilder strb=new StringBuilder();
            strb.append("[Id: "+id+"]\n");
            for(var error:errors){
                strb.append(error.toString()).append("\n");
            }
            strb.append("\n");
            return strb.toString();
        }
    }
}
