package com.lab.html_editor.utils.command.editor_command;

import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.exceptions.HtmlEditFailException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;


public class ConsoleHtmlEditIdCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlDocument document;
    private final String previousId;
    private final String newId;


    public ConsoleHtmlEditIdCommand(HtmlDocument document,String previousId,String newId){
        this.document=document;
        this.previousId=previousId;
        this.newId=newId;
    
    }

    public boolean execute(){
        try{
            document.editID(previousId, newId); 
            document.notifyObservers(new StatusEvent("Successfully edit id", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to edit id"+" because "+e.getMessage(),false,e));
            return false;
            }
       
      
    }

    public boolean undo(){
        try{
            document.editID(newId, previousId); 
            document.notifyObservers(new StatusEvent("Successfully undo edit-id command", true));
            return true;
        }catch(Exception e){
            String msg="Failed to undo"+" because "+e.getMessage();
            document.notifyObservers(new StatusEvent(msg,false,new HtmlEditFailException(msg)));
            return false;
        }
        
    }
    
    public boolean redo(){
        try{
            document.editID(previousId, newId); 
            document.notifyObservers(new StatusEvent("Successfully redo edit-id command", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to redo"+" because "+e.getMessage(),false,e));
            return false;
        }
        
    }
}
