package com.lab.html_editor.utils.command.editor_command;



import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementSpellCheckVisitor;




public class ConsoleHtmlSpellCheckCommand extends ConsoleEditorCommand {

    private final HtmlDocument document;
    private final SpellCheckService spellCheckService;
    private boolean visitChild=true;
    private String targetId=null;

    public ConsoleHtmlSpellCheckCommand(HtmlDocument document,SpellCheckService spellCheckService){
        this.document=document;
        this.spellCheckService=spellCheckService;
    }

    public ConsoleHtmlSpellCheckCommand(HtmlDocument document,SpellCheckService spellCheckService,boolean visitChild,String targetId){
        this.document=document;
        this.spellCheckService=spellCheckService;
        this.visitChild=visitChild;
        this.targetId=targetId;
    }

    @Override
    public boolean execute(){
        try{
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(spellCheckService,visitChild);
            if(targetId==null){
                document.visitRoot(spellCheckVisitor);
            }else{
                document.visitElement(spellCheckVisitor, targetId);
            }
            
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
