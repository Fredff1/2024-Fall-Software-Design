package com.lab.html_editor.utils.command;



import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementSpellCheckVisitor;




public class ConsoleHtmlSpellCheckCommand implements ConsoleCommand{

    private final HtmlDocument document;
    private final SpellCheckService spellCheckService;

    public ConsoleHtmlSpellCheckCommand(HtmlDocument document,SpellCheckService spellCheckService){
        this.document=document;
        this.spellCheckService=spellCheckService;
    }

    @Override
    public boolean execute(){
        try{
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(spellCheckService);
            document.visitRoot(spellCheckVisitor);
            String concreteTexts=spellCheckVisitor.getErrorContents();
            if (concreteTexts.trim().isEmpty()) {

                document.notifyObservers(new StatusEvent("\n[Spell Check Result]\n\nNo spelling errors found.", true));
            }else{
                document.notifyObservers(new StatusEvent("\n[Spell Check Result]\n\n"+concreteTexts, true));
            }
            
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to do spell check because "+e.getMessage(), false));
            return false;
        }
        
    }

    @Override
    public boolean undo(){
        throw new RuntimeException("Cannot undo command");
       
    }

    @Override 
    public boolean redo(){
        throw new RuntimeException("Cannot redo command");
        
    }

    @Override
    public boolean supportsUndo(){
        return false;
    }
}
