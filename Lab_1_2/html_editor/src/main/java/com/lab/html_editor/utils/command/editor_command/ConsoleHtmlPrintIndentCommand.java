package com.lab.html_editor.utils.command.editor_command;

import com.lab.html_editor.controller.events.StatusEvent;
import com.lab.html_editor.model.htmlElement.HtmlDocument;
import com.lab.html_editor.utils.command.ConsoleCommand;
import com.lab.html_editor.utils.command.ConsoleEditorCommand;
import com.lab.html_editor.utils.strategy.HtmlIndentedRepresentation;
import com.lab.html_editor.view.HtmlView;

public class ConsoleHtmlPrintIndentCommand extends ConsoleEditorCommand implements ConsoleUpdateCommand{
    private final HtmlDocument document;
    private final int indent;
    private final HtmlView view;


    public ConsoleHtmlPrintIndentCommand(HtmlDocument document,HtmlView view,int indent){
        this.document=document;
        this.indent=indent;
        this.view=view;
    }

    @Override
    public boolean execute(){
        try{
            document.setRepresentationStrategy(new HtmlIndentedRepresentation());
            document.setIndent(indent);
            document.notifyObservers(new StatusEvent("Successfully converted to indented representation with indent "+indent, true));
            return true;
        }catch(Exception e){
            document.notifyObservers(new StatusEvent("Failed to init document", false,e));
            return false;
        }
        
    }

    @Override
    public boolean undo(){
        throw new RuntimeException("Failed to undo");
        //document.notifyObservers(new StatusEvent("Successfully undo init document", true));      
    }

    @Override 
    public boolean redo(){
        //document.notifyObservers(new StatusEvent("Successfully redo init document", true));
        throw new RuntimeException("Failed to undo");
    }

    @Override
    public boolean supportsUndo(){
        return false;
    }

}
