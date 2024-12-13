package com.lab.html_editor.utils.command.editor_command;


import com.lab.html_editor.controller.events.StatusEvent;

import com.lab.html_editor.model.exceptions.HtmlDeleteException;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.model.htmlElement.HtmlElement;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;


public class ConsoleHtmlDeleteCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlDocument document;
    private final String id;
    private HtmlElement deleteTarget;
    

    private String parentId;
    private int deleteLocation;

    public ConsoleHtmlDeleteCommand(HtmlDocument document,String id){
        this.document=document;
        this.id=id;
        
    }

    
    public boolean execute(){
        try{
            deleteTarget=document.search(id);
            deleteLocation=document.getElementIndex(id);
            var parent=(HtmlElement)((HtmlElement)deleteTarget).getFather();
            parentId=parent.getId();
            document.delete(id);
            document.notifyObservers(new StatusEvent("Successfully delete element of id "+id, true));
            return true;
        }catch(Exception e){
            String msg="Failed to delete element of id "+id+" because "+e.getMessage();
            document.notifyObservers(new StatusEvent(msg, false,new HtmlDeleteException(msg)));
            return false;
        }
       
    }

    public boolean undo(){
        try{
            document.setElementAsChild(parentId,deleteTarget,deleteLocation);
            document.notifyObservers(new StatusEvent("Successfully undo delete command", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to redo undo command because "+e.getMessage(), false,e));
            return false;
        }
        
    }

    public boolean redo(){
        try{
            document.delete(id);
            document.notifyObservers(new StatusEvent("Successfully redo delete command", true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to redo redo command because "+e.getMessage(), false,e));
            return false;
        }
        
    }
}
