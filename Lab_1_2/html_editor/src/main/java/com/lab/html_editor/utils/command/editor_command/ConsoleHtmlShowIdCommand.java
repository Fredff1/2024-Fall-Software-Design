package com.lab.html_editor.utils.command.editor_command;

import com.lab.html_editor.controller.HtmlEditor;
import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;

public class ConsoleHtmlShowIdCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private HtmlEditor editor;
    private HtmlDocument document;
    private boolean showId;
    private boolean previousShowId=true;

    public ConsoleHtmlShowIdCommand(HtmlEditor editor,boolean showId){
        this.editor=editor;
        this.document=editor.getDocument();
        this.showId=showId;
    }


    @Override
    public boolean execute(){
        try{
            this.previousShowId=editor.isShowId();
            editor.setShowId(showId);
            document.notifyObservers(new StatusEvent("Set show id to "+showId,true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to show id document", false,e));
            return false;
        }
        
    }

    @Override
    public boolean undo(){
        editor.setShowId(previousShowId);  
        document.notifyObservers(new StatusEvent("Set show id to "+showId,true));
        return true;   
    }

    @Override 
    public boolean redo(){
        editor.setShowId(showId);
        document.notifyObservers(new StatusEvent("Set show id to "+showId,true));
        return true;  
    }

    @Override
    public boolean supportsUndo(){
        return true;
    }
}
