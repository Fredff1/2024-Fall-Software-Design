package com.lab.html_editor.utils.command.editor_command;

import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.strategy.HtmlTreeRepresentation;

public class ConsoleHtmlPrintTreeCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlDocument document;
    private boolean showId=true;

    public boolean isShowingId(){
        return showId;
    }


    public ConsoleHtmlPrintTreeCommand(HtmlDocument document){
        this.document=document;

    }

    public ConsoleHtmlPrintTreeCommand(HtmlDocument document,boolean showId){
        this.document=document;
        this.showId=showId;
    }

    @Override
    public boolean execute(){
        try{
            HtmlTreeRepresentation treeRepresentation=new HtmlTreeRepresentation();
            document.setRepresentationStrategy(treeRepresentation);
            document.notifyObservers(new StatusEvent("Successfully converted to tree representation", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to init document", false,e));
            return false;
        }
        
    }

    @Override
    public boolean undo(){
        
        throw new RuntimeException("Failed to undo");
        //controller.getDocument().notifyObservers(new StatusEvent("Failed to undo init document", false));
        
        
    }

    @Override 
    public boolean redo(){
       
        throw new RuntimeException("Failed to redo");
        //controller.getDocument().notifyObservers(new StatusEvent("Failed to redo init document", false));
        
        
    }

    @Override
    public boolean supportsUndo(){
        return false;
    }

    
}
