package com.lab.html_editor.utils.command.editor_command;

import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.exceptions.HtmlEditFailException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.service.spellcheck.SpellCheckService;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.visitor.html_visitor.HtmlElementSpellCheckVisitor;

public class ConsoleHtmlEditContentCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlDocument document;
    private final String targetId;
    private final String newContent;
    private final SpellCheckService spellCheckService;

    private String previousContentText;


    public ConsoleHtmlEditContentCommand(HtmlDocument document,String targetId,String newContent,SpellCheckService spellCheckService){
        this.document=document;
        this.targetId=targetId;
        this.newContent=newContent;
        this.spellCheckService=spellCheckService;
    }

    public boolean execute(){
        try{
            var oldElement=document.search(targetId);
            previousContentText=oldElement.getText();
            document.editContent(targetId, newContent);
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(spellCheckService,false);
            oldElement.accept(spellCheckVisitor);
            document.notifyObservers(new StatusEvent("Successfully edit content of "+targetId, true));
            return true;
        }catch(Exception e){
            String msg="Failed to edit content of "+targetId+" because "+e.getMessage();
            document.notifyObservers(new StatusEvent(msg, false,new HtmlEditFailException(msg)));
        
            return false;}
        
       
    }

    public boolean undo(){
        try{
            var oldElement=document.search(targetId);
            document.editContent(targetId, previousContentText);
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(spellCheckService,false);
            oldElement.accept(spellCheckVisitor);
            document.notifyObservers(new StatusEvent("Successfully undo edit-text command",true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to undo edit command"+" because "+e.getMessage(),false,e));
            return false;
        }
        
    }
    
    public boolean redo(){
        try{
            var oldElement=document.search(targetId);
            HtmlElementSpellCheckVisitor spellCheckVisitor=new HtmlElementSpellCheckVisitor(spellCheckService,false);
            oldElement.accept(spellCheckVisitor);
            document.editContent(targetId, newContent);
            document.notifyObservers(new StatusEvent("Successfully redo edit-text command", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to redo edit command"+" because "+e.getMessage(),false,e));
            return false;
        }
        
    }
}
